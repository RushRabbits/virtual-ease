package com.awake.ve.admin.web.service;

import cn.dev33.satoken.secure.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
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
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.common.tenant.helper.TenantHelper;
import com.awake.ve.common.web.config.properties.CaptchaProperties;
import com.awake.ve.system.domain.SysUser;
import com.awake.ve.system.domain.bo.SysUserBo;
import com.awake.ve.system.mapper.SysUserMapper;
import com.awake.ve.system.service.ISysUserService;
import org.springframework.stereotype.Service;

/**
 * 注册校验方法
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service("srs")
public class SysRegisterService {

    private final ISysUserService userService;
    private final SysUserMapper userMapper;
    private final CaptchaProperties captchaProperties;

    /**
     * 注册
     */
    public void register(RegisterBody registerBody) {
        String tenantId = registerBody.getTenantId();
        String username = registerBody.getUsername();
        String password = registerBody.getPassword();
        // 校验用户类型是否存在
        String userType = UserType.getUserType(registerBody.getUserType()).getUserType();

        boolean captchaEnabled = captchaProperties.getEnable();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(tenantId, username, registerBody.getCode(), registerBody.getUuid());
        }
        SysUserBo sysUser = new SysUserBo();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(BCrypt.hashpw(password));
        sysUser.setUserType(userType);

        boolean exist = TenantHelper.dynamic(tenantId, () -> {
            return userMapper.exists(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, sysUser.getUserName()));
        });
        if (exist) {
            throw new UserException("user.register.save.error", username);
        }
        boolean regFlag = userService.registerUser(sysUser, tenantId);
        if (!regFlag) {
            throw new UserException("user.register.error");
        }
        recordLogininfor(tenantId, username, Constants.REGISTER, MessageUtils.message("user.register.success"));
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    public void validateCaptcha(String tenantId, String username, String code, String uuid) {
        String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.blankToDefault(uuid, "");
        String captcha = RedisUtils.getCacheObject(verifyKey);
        RedisUtils.deleteObject(verifyKey);
        if (captcha == null) {
            recordLogininfor(tenantId, username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            recordLogininfor(tenantId, username, Constants.REGISTER, MessageUtils.message("user.jcaptcha.error"));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param tenantId 租户ID
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    private void recordLogininfor(String tenantId, String username, String status, String message) {
        LoginInfoEvent logininforEvent = new LoginInfoEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(status);
        logininforEvent.setMessage(message);
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
    }

}
