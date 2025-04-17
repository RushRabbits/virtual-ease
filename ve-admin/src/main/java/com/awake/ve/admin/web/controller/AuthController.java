package com.awake.ve.admin.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.codec.Base64;
import com.awake.ve.admin.web.domain.vo.LoginTenantVo;
import com.awake.ve.admin.web.domain.vo.LoginVo;
import com.awake.ve.admin.web.domain.vo.TenantListVo;
import com.awake.ve.admin.web.service.SysLoginService;
import com.awake.ve.admin.web.service.SysRegisterService;
import com.awake.ve.admin.web.service.auth.IAuthStrategy;
import com.awake.ve.common.core.constant.SystemConstants;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.core.domain.model.LoginBody;
import com.awake.ve.common.core.domain.model.RegisterBody;
import com.awake.ve.common.core.domain.model.SocialLoginBody;
import com.awake.ve.common.core.utils.*;
import com.awake.ve.common.encrypt.annotation.ApiEncrypt;
import com.awake.ve.common.json.utils.JsonUtils;
import com.awake.ve.common.satoken.utils.LoginHelper;
import com.awake.ve.common.social.config.properties.SocialLoginConfigProperties;
import com.awake.ve.common.social.config.properties.SocialProperties;
import com.awake.ve.common.social.utils.SocialUtils;
import com.awake.ve.common.tenant.helper.TenantHelper;
import com.awake.ve.system.domain.bo.SysTenantBo;
import com.awake.ve.system.domain.vo.SysClientVo;
import com.awake.ve.system.domain.vo.SysTenantVo;
import com.awake.ve.system.service.ISysClientService;
import com.awake.ve.system.service.ISysConfigService;
import com.awake.ve.system.service.ISysSocialService;
import com.awake.ve.system.service.ISysTenantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@SaIgnore
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SocialProperties socialProperties;
    private final SysLoginService loginService;
    private final SysRegisterService registerService;
    private final ISysConfigService configService;
    private final ISysTenantService tenantService;
    private final ISysSocialService socialService;
    private final ISysClientService clientService;
    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * 登录
     *
     * @param body 登录请求体
     * @author wangjiaxing
     * @date 2025/2/12 15:14
     */
    @ApiEncrypt
    @PostMapping("/login")
    public R<LoginVo> login(@RequestBody String body) {
        LoginBody loginBody = JsonUtils.parseObject(body, LoginBody.class);
        ValidatorUtils.validate(loginBody);
        // 授权类型和客户端id
        String clientId = loginBody.getClientId();
        String grantType = loginBody.getGrantType();
        SysClientVo client = clientService.queryByClientId(clientId);

        // 查询不到client或者client内不包含grantType
        if (client == null || !StringUtils.contains(client.getGrantType(), grantType)) {
            log.info("[AuthController][login] 客户端id:{} ,认证类型:{} 异常", clientId, grantType);
            return R.fail(MessageUtils.message("auth.grant.type.error"));
        } else if (!SystemConstants.NORMAL.equals(client.getStatus())) {
            return R.fail(MessageUtils.message("auth.grant.type.blocked"));
        }

        // 校验租户
        loginService.checkTenant(loginBody.getTenantId());

        // 登录
        LoginVo loginVo = IAuthStrategy.login(body, client, grantType);

        // Long userId = LoginHelper.getUserId();
        // scheduledExecutorService.schedule(() -> {
        //     SseMessageDto dto = new SseMessageDto();
        //     dto.setMessage("欢迎登录");
        //     dto.setUserIds(List.of(userId));
        //     SseMessageUtils.publishMessage(dto);
        // }, 5, TimeUnit.SECONDS);
        return R.ok(loginVo);
    }

    /**
     * 第三方登录请求
     *
     * @param source   第三方平台
     * @param tenantId 租户id
     * @param domain   域名
     */
    @GetMapping("/binding/{source}")
    public R<String> authBinding(@PathVariable("source") String source,
                                 @RequestParam String tenantId,
                                 @RequestParam String domain) {
        SocialLoginConfigProperties socialLoginConfigProperties = socialProperties.getType().get(source);
        if (socialLoginConfigProperties == null) {
            return R.fail(source + "平台账号暂不支持");
        }

        AuthRequest authRequest = SocialUtils.getAuthRequest(source, socialProperties);
        Map<String, String> map = new HashMap<>();
        map.put("tenantId", tenantId);
        map.put("domain", domain);
        map.put("state", AuthStateUtils.createState());
        String authorizeUrl = authRequest.authorize(Base64.encode(JsonUtils.toJsonString(map), StandardCharsets.UTF_8));
        return R.ok(authorizeUrl);
    }

    /**
     * 第三方登录回调 业务处理 绑定授权
     *
     * @param loginBody {@link SocialLoginBody}
     * @author wangjiaxing
     * @date 2025/2/12 15:29
     */
    @PostMapping("/social/callback")
    public R<Void> socialCallback(@RequestBody SocialLoginBody loginBody) {
        // 获取第三方登录信息
        AuthResponse<AuthUser> response = SocialUtils.loginAuth(
                loginBody.getSource(),
                loginBody.getSocialCode(),
                loginBody.getSocialState(),
                socialProperties
        );

        AuthUser authUserData = response.getData();

        // 判断授权响应是否成功
        if (!response.ok()) {
            return R.fail(response.getMsg());
        }

        loginService.socialRegister(authUserData);

        return R.ok();
    }

    /**
     * 取消授权
     *
     * @param socialId 第三方id
     * @author wangjiaxing
     * @date 2025/2/12 15:34
     */
    @DeleteMapping("/unlock/{socialId}")
    public R<Void> unlockSocial(@PathVariable Long socialId) {
        Boolean rows = socialService.deleteWithValidById(socialId);
        return rows ? R.ok() : R.fail("取消授权失败");
    }

    /**
     * 退出登录
     *
     * @author wangjiaxing
     * @date 2025/2/12 15:35
     */
    @PostMapping("/logout")
    public R<Void> logout() {
        loginService.logout();
        return R.ok("退出成功");
    }

    /**
     * 用户注册
     *
     * @param user {@link RegisterBody}
     * @author wangjiaxing
     * @date 2025/2/12 15:37
     */
    @ApiEncrypt
    @PostMapping("/register")
    public R<Void> register(@Validated @RequestBody RegisterBody user) {
        if (!configService.selectRegisterEnabled(user.getTenantId())) {
            return R.fail("当前系统未开启注册功能");
        }
        registerService.register(user);
        return R.ok("注册成功");
    }

    /**
     * 登陆页面获取租户下拉框
     *
     * @param request {@link HttpServletRequest}
     * @return {@link R}<{@link LoginTenantVo}></>
     * @author wangjiaxing
     * @date 2025/2/12 15:39
     */
    @GetMapping("/tenant/list")
    public R<LoginTenantVo> tenantList(HttpServletRequest request) throws Exception {
        LoginTenantVo result = new LoginTenantVo();

        // 是否开启多租户
        boolean enable = TenantHelper.isEnable();
        if (!enable) {
            return R.ok(result);
        }

        List<SysTenantVo> tenantList = tenantService.queryList(new SysTenantBo());
        List<TenantListVo> voList = MapstructUtils.convert(tenantList, TenantListVo.class);

        try {
            if (LoginHelper.isSuperAdmin()) {
                result.setVoList(voList);
                return R.ok(result);
            }
        } catch (NotLoginException ignored) {
        }

        // 获取域名
        String host;
        String referer = request.getHeader("referer");
        if (StringUtils.isNotBlank(referer)) {
            // 这里从referer取值是为了本地使用hosts添加虚拟域名,方便本地环境调试
            host = referer.split("//")[1].split("/")[0];
        } else {
            host = new URL(request.getRequestURL().toString()).getHost();
        }

        // 根据域名进行筛选
        List<TenantListVo> list = StreamUtils.filter(voList, vo -> StringUtils.equalsIgnoreCase(vo.getDomain(), host));
        result.setVoList(CollectionUtils.isEmpty(list) ? voList : list);
        return R.ok(result);
    }
}
