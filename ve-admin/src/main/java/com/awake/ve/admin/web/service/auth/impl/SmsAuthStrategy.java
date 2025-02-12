package com.awake.ve.admin.web.service.auth.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.awake.ve.admin.web.domain.vo.LoginVo;
import com.awake.ve.admin.web.service.SysLoginService;
import com.awake.ve.admin.web.service.auth.IAuthStrategy;
import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.domain.model.LoginUser;
import com.awake.ve.common.core.domain.model.SmsLoginBody;
import com.awake.ve.common.core.enums.LoginType;
import com.awake.ve.common.core.enums.UserStatus;
import com.awake.ve.common.core.exception.user.CaptchaExpireException;
import com.awake.ve.common.core.exception.user.UserException;
import com.awake.ve.common.core.utils.MessageUtils;
import com.awake.ve.common.core.utils.StringUtils;
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

/**
 * 短信认证策略实现类
 *
 * @author wangjiaxing
 * @date 2025/2/12 11:12
 */
@Slf4j
@Service("sms" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SmsAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;
    private final SysUserMapper userMapper;

    /**
     * 短信登录
     *
     * @param body   请求体
     * @param client {@link SysClientVo}
     * @return {@link LoginVo}
     * @author wangjiaxing
     * @date 2025/2/12 11:13
     */
    @Override
    public LoginVo login(String body, SysClientVo client) {
        SmsLoginBody loginBody = JsonUtils.parseObject(body, SmsLoginBody.class);
        String tenantId = loginBody.getTenantId();
        String phonenumber = loginBody.getPhonenumber();
        String smsCode = loginBody.getCode();

        LoginUser loginUser = TenantHelper.dynamic(tenantId, () -> {
            SysUserVo userVo = loadUserByPhoneNumber(phonenumber);
            loginService.checkLogin(LoginType.SMS, tenantId, userVo.getUserName(), () -> !validateSmsCode(tenantId, phonenumber, smsCode));
            // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
            return loginService.buildLoginUser(userVo);
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
     * 校验短信验证码
     *
     * @param tenantId    租户id
     * @param phonenumber 手机号
     * @param smsCode     短信验证码
     * @author wangjiaxing
     * @date 2025/2/12 11:20
     */
    private boolean validateSmsCode(String tenantId, String phonenumber, String smsCode) {
        String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + phonenumber);
        if (StringUtils.isBlank(code)) {
            loginService.recordLogininfor(tenantId, phonenumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
            throw new CaptchaExpireException();
        }
        RedisUtils.deleteObject(GlobalConstants.CAPTCHA_CODE_KEY + phonenumber);
        return code.equals(smsCode);
    }


    /**
     * 根据手机号查询用户信息
     *
     * @param phoneNumber 手机号
     * @return {@link SysUserVo}
     * @author wangjiaxing
     * @date 2025/2/12 11:18
     */
    private SysUserVo loadUserByPhoneNumber(String phoneNumber) {
        SysUserVo userVo = userMapper.selectVoOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, phoneNumber));
        if (ObjectUtil.isNull(userVo)) {
            log.info("[SmsAuthStrategy][loadUserByPhoneNumber] 登录用户：{} 不存在.", phoneNumber);
            throw new UserException("user.not.exists", phoneNumber);
        } else if (UserStatus.DISABLE.getCode().equals(userVo.getStatus())) {
            log.info("[SmsAuthStrategy][loadUserByPhoneNumber] 登录用户：{} 已被停用.", phoneNumber);
            throw new UserException("user.blocked", phoneNumber);
        }
        return userVo;
    }
}
