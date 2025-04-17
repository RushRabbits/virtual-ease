package com.awake.ve.admin.web.service;

import cn.dev33.satoken.secure.BCrypt;
import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.domain.model.RegisterBody;
import com.awake.ve.common.core.enums.UserType;
import com.awake.ve.common.core.exception.user.CaptchaException;
import com.awake.ve.common.core.exception.user.CaptchaExpireException;
import com.awake.ve.common.core.exception.user.UserException;
import com.awake.ve.common.core.utils.MessageUtils;
import com.awake.ve.common.core.utils.ServletUtils;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.log.event.LoginInfoEvent;
import com.awake.ve.common.tenant.helper.TenantHelper;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.common.web.config.properties.CaptchaProperties;
import com.awake.ve.system.domain.SysUser;
import com.awake.ve.system.domain.bo.SysUserBo;
import com.awake.ve.system.mapper.SysUserMapper;
import com.awake.ve.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 注册校验实现类
 *
 * @author wangjiaxing
 * @date 2025/2/12 11:44
 */
@RequiredArgsConstructor
@Service
public class SysRegisterService {

    private final ISysUserService userService;
    private final SysUserMapper sysUserMapper;
    private final CaptchaProperties captchaProperties;

    /**
     * 注册
     *
     * @param registerBody {@link RegisterBody}
     * @author wangjiaxing
     * @date 2025/2/12 11:45
     */
    public void register(RegisterBody registerBody) {
        String tenantId = registerBody.getTenantId();
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();

        // 校验用户类型是否存在
        String userType = UserType.getUserType(registerBody.getUserType()).getUserType();

        // 验证码
        Boolean captchaEnabled = captchaProperties.getEnable();
        if (captchaEnabled) {
            validateCaptcha(tenantId, username, registerBody.getCode(), registerBody.getUuid());
        }

        SysUserBo user = new SysUserBo();
        user.setUserName(username);
        user.setNickName(username);
        user.setPassword(BCrypt.hashpw(password));
        user.setUserType(userType);

        Boolean exist = TenantHelper.dynamic(
                tenantId, () -> sysUserMapper.exists(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, username)
                )
        );

        if (exist) {
            throw new UserException("user.register.save.error", username);
        }
        boolean registerFlag = userService.registerUser(user, tenantId);
        if (!registerFlag) {
            throw new UserException("user.register.error", username);
        }
        recordLoggingInfo(tenantId, username, Constants.REGISTER, MessageUtils.message("user.register.success"));
    }

    /**
     * 校验验证码
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @param uuid     唯一标识
     * @author wangjiaxing
     * @date 2025/2/12 11:48
     */
    private void validateCaptcha(String tenantId, String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.blankToDefault(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);

        if (captcha == null) {
            recordLoggingInfo(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }

        if (!code.equalsIgnoreCase(captcha)) {
            recordLoggingInfo(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @param status   状态
     * @param message  错误信息
     * @author wangjiaxing
     * @date 2025/2/12 11:51
     */
    private void recordLoggingInfo(String tenantId, String username, String status, String message) {
        LoginInfoEvent loginInfoEvent = new LoginInfoEvent();
        loginInfoEvent.setTenantId(tenantId);
        loginInfoEvent.setUsername(username);
        loginInfoEvent.setStatus(status);
        loginInfoEvent.setMessage(message);
        loginInfoEvent.setRequest(ServletUtils.getRequest());

        // 推送事件到容器 对应的监听器(UserActionListener)会处理该事件
        SpringUtils.context().publishEvent(loginInfoEvent);
    }
}
