package com.awake.ve.admin.web.service.auth.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.awake.ve.admin.web.domain.vo.LoginVo;
import com.awake.ve.admin.web.service.SysLoginService;
import com.awake.ve.admin.web.service.auth.IAuthStrategy;
import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.constant.SystemConstants;
import com.awake.ve.common.core.domain.model.EmailLoginBody;
import com.awake.ve.common.core.domain.model.LoginUser;
import com.awake.ve.common.core.enums.LoginType;
import com.awake.ve.common.core.exception.user.CaptchaExpireException;
import com.awake.ve.common.core.exception.user.UserException;
import com.awake.ve.common.core.utils.MessageUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.core.utils.ValidatorUtils;
import com.awake.ve.common.json.utils.JsonUtils;
import com.awake.ve.common.satoken.utils.LoginHelper;
import com.awake.ve.common.tenant.helper.TenantHelper;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.system.domain.SysUser;
import com.awake.ve.system.domain.vo.SysClientVo;
import com.awake.ve.system.domain.vo.SysUserVo;
import com.awake.ve.system.mapper.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service("email" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class EmailAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;
    private final SysUserMapper userMapper;

    @Override
    public LoginVo login(String body, SysClientVo client) {
        EmailLoginBody loginBody = JsonUtils.parseObject(body, EmailLoginBody.class);
        ValidatorUtils.validate(loginBody);
        String tenantId = loginBody.getTenantId();
        String email = loginBody.getEmail();
        String code = loginBody.getEmailCode();
        LoginUser loginUser = TenantHelper.dynamic(tenantId, () -> {
            SysUserVo user = loadUserByEmail(email);
            loginService.checkLogin(LoginType.EMAIL, tenantId, user.getUserName(), () -> !validateEmailCode(tenantId, email, code));
            // 此处可以根据登录用户的数据的不同,自行创建loginUser 属性不够用的话,继承拓展即可
            return loginService.buildLoginUser(user);
        });
        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
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
     * 验证邮箱验证码
     *
     * @param tenantId 租户id
     * @param email    邮箱
     * @param code     邮箱验证码
     * @author wangjiaxing
     * @date 2025/2/12 10:17
     */
    private boolean validateEmailCode(String tenantId, String email, String code) {
        String cacheCode = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + email);
        if (StringUtils.isBlank(cacheCode)) {
            loginService.recordLogininfor(tenantId, email, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        return code.equals(cacheCode);
    }

    /**
     * 根据邮箱查询用户
     *
     * @param email 用户邮箱
     * @return {@link SysUserVo}
     * @author wangjiaxing
     * @date 2025/2/12 10:10
     */
    private SysUserVo loadUserByEmail(String email) {
        SysUserVo user = userMapper.selectVoOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, email));
        if (Objects.isNull(user)) {
            log.info("[EmailAuthStrategy][loadUserByEmail]登录用户：{} 不存在.", email);
            throw new UserException("user.not.exists", email);
        } else if (SystemConstants.DISABLE.equals(user.getStatus())) {
            log.info("[EmailAuthStrategy][loadUserByEmail]登录用户：{} 已被停用.", email);
            throw new UserException("user.blocked", email);
        }
        return user;
    }
}
