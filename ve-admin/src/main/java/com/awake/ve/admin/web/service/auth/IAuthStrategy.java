package com.awake.ve.admin.web.service.auth;

import com.awake.ve.admin.web.domain.vo.LoginVo;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.system.domain.vo.SysClientVo;

/**
 * 授权策略接口
 *
 * @author wangjiaxing
 * @date 2025/2/12 9:58
 */
public interface IAuthStrategy {

    String BASE_NAME = "AuthStrategy";

    /**
     * 登录
     *
     * @param body      登陆对象
     * @param client    授权管理视图对象
     * @param grantType 授权类型
     * @return {@link LoginVo}
     * @author wangjiaxing
     * @date 2025/2/12 10:00
     */
    static LoginVo login(String body, SysClientVo client, String grantType) {
        // 授权类型和客户端id
        String beanName = grantType + BASE_NAME;
        if (!SpringUtils.containsBean(beanName)) {
            throw new ServiceException("授权类型不正确");
        }
        IAuthStrategy instance = SpringUtils.getBean(beanName);
        return instance.login(body, client);
    }

    /**
     * 登录
     *
     * @param body   登陆对象
     * @param client 授权管理视图对象
     * @return {@link LoginVo}
     * @author wangjiaxing
     * @date 2025/2/12 10:02
     */
    LoginVo login(String body, SysClientVo client);
}
