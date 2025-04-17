package com.awake.ve.admin.web.service.auth.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.awake.ve.admin.web.domain.vo.LoginVo;
import com.awake.ve.admin.web.service.SysLoginService;
import com.awake.ve.admin.web.service.auth.IAuthStrategy;
import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.constant.SystemConstants;
import com.awake.ve.common.core.domain.model.LoginUser;
import com.awake.ve.common.core.domain.model.PasswordLoginBody;
import com.awake.ve.common.core.enums.LoginType;
import com.awake.ve.common.core.exception.user.CaptchaException;
import com.awake.ve.common.core.exception.user.CaptchaExpireException;
import com.awake.ve.common.core.exception.user.UserException;
import com.awake.ve.common.core.utils.MessageUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.core.utils.ValidatorUtils;
import com.awake.ve.common.json.utils.JsonUtils;
import com.awake.ve.common.satoken.utils.LoginHelper;
import com.awake.ve.common.tenant.helper.TenantHelper;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.common.web.config.properties.CaptchaProperties;
import com.awake.ve.system.domain.SysUser;
import com.awake.ve.system.domain.vo.SysClientVo;
import com.awake.ve.system.domain.vo.SysUserVo;
import com.awake.ve.system.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 密码登录认证策略
 *
 * @author wangjiaxing
 * @date 2025/2/12 10:46
 */
@Slf4j
@Service("password" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class PasswordAuthStrategy implements IAuthStrategy {

    private final CaptchaProperties captchaProperties;
    private final SysLoginService loginService;
    private final SysUserMapper userMapper;

    @Override
    public LoginVo login(String body, SysClientVo client) {
        PasswordLoginBody loginBody = JsonUtils.parseObject(body, PasswordLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String tenantId = loginBody.getTenantId();
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        String code = loginBody.getCode();
        String uuid = loginBody.getUuid();

        Boolean captchaEnabled = captchaProperties.getEnable();

        if (captchaEnabled) {
            validateCaptcha(tenantId, username, code, uuid);
        }

        LoginUser loginUser = TenantHelper.dynamic(tenantId, () -> {
            SysUserVo user = loadUserByUsername(username);
            loginService.checkLogin(LoginType.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));
            // 此处可根据登录用户的数据不同 自行创建 loginUser
            return loginService.buildLoginUser(user);
        });

        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同token授权时间 不设置默认走全局yml
        // 例如后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());

        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());

        return loginVo;
    }

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return {@link SysUserVo}
     * @author wangjiaxing
     * @date 2025/2/12 10:58
     */
    private SysUserVo loadUserByUsername(String username) {
        SysUserVo user = userMapper.selectVoOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username));
        if (ObjectUtil.isNull(user)) {
            log.info("[PasswordStrategy][loadUserByUsername] 登录用户：{} 不存在.", username);
            throw new UserException("user.not.exists", username);
        } else if (SystemConstants.DISABLE.equals(user.getStatus())) {
            log.info("[PasswordStrategy][loadUserByUsername] 登录用户：{} 已被停用.", username);
            throw new UserException("user.blocked", username);
        }
        return user;
    }

    /**
     * 验证码校验
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @param code     验证码
     * @param uuid     uuid
     * @author wangjiaxing
     * @date 2025/2/12 10:52
     */
    private void validateCaptcha(String tenantId, String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.blankToDefault(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }
}
