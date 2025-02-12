package com.awake.ve.admin.web.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import com.awake.ve.admin.web.domain.vo.LoginVo;
import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.core.constant.Constants;
import com.awake.ve.common.core.constant.SystemConstants;
import com.awake.ve.common.core.constant.TenantConstants;
import com.awake.ve.common.core.domain.dto.PostDTO;
import com.awake.ve.common.core.domain.dto.RoleDTO;
import com.awake.ve.common.core.domain.model.LoginUser;
import com.awake.ve.common.core.enums.LoginType;
import com.awake.ve.common.core.exception.user.UserException;
import com.awake.ve.common.core.utils.*;
import com.awake.ve.common.log.event.LoginInfoEvent;
import com.awake.ve.common.mybatis.helper.DataPermissionHelper;
import com.awake.ve.common.satoken.utils.LoginHelper;
import com.awake.ve.common.tenant.exception.TenantException;
import com.awake.ve.common.tenant.helper.TenantHelper;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.system.domain.SysUser;
import com.awake.ve.system.domain.bo.SysSocialBo;
import com.awake.ve.system.domain.vo.*;
import com.awake.ve.system.mapper.SysUserMapper;
import com.awake.ve.system.service.*;
import com.baomidou.lock.annotation.Lock4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * 登录服务
 *
 * @author wangjiaxing
 * @date 2025/2/12 13:47
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginService {

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    private final ISysTenantService sysTenantService;
    private final ISysPermissionService sysPermissionService;
    private final ISysSocialService sysSocialService;
    private final ISysRoleService sysRoleService;
    private final ISysDeptService sysDeptService;
    private final ISysPostService sysPostService;
    private final SysUserMapper userMapper;

    /**
     * 绑定第三方用户
     *
     * @param authUser {@link AuthUser}
     * @author wangjiaxing
     * @date 2025/2/12 13:51
     */
    @Lock4j
    public void socialRegister(AuthUser authUser) {
        String authId = authUser.getSource() + authUser.getUuid();

        // 第三方用户信息
        SysSocialBo socialBo = BeanUtil.toBean(authUser, SysSocialBo.class);
        BeanUtil.copyProperties(authUser.getToken(), socialBo);

        Long userId = LoginHelper.getUserId();
        socialBo.setUserId(userId);
        socialBo.setAuthId(authId);
        socialBo.setOpenId(authUser.getUuid());
        socialBo.setUserName(authUser.getUsername());
        socialBo.setNickName(authUser.getNickname());

        // 第三方账号是否已绑定其他账号
        List<SysSocialVo> checkList = sysSocialService.selectByAuthId(authId);
        if (!CollectionUtils.isEmpty(checkList)) {
            throw new RuntimeException("第三方账号已绑定其他账号");
        }

        SysSocialBo params = new SysSocialBo();
        params.setUserId(userId);
        params.setSource(socialBo.getSource());
        List<SysSocialVo> socialList = sysSocialService.queryList(params);
        if (CollectionUtils.isEmpty(socialList)) {
            // 没有绑定用户信息,新增用户信息
            sysSocialService.insertByBo(socialBo);
        } else {
            // 更新用户信息
            socialBo.setId(socialList.get(0).getId());
            sysSocialService.updateByBo(socialBo);
            // 自行判断是否要抛异常
            // throw new RuntimeException("此平台账号已绑定其他账号");
        }
    }

    /**
     * 退出登录
     *
     * @author wangjiaxing
     * @date 2025/2/12 14:07
     */
    public void logout() {
        try {
            LoginUser loginUser = LoginHelper.getLoginUser();
            if (loginUser == null) {
                return;
            }
            // 超级管理员,登出清除动态租户
            if (TenantHelper.isEnable() && LoginHelper.isSuperAdmin()) {
                TenantHelper.clearDynamic();
            }
            recordLogininfor(loginUser.getTenantId(), loginUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
        } catch (NotLoginException ignored) {
        } finally {
            try {
                StpUtil.logout();
            } catch (NotLoginException ignored) {
            }
        }

    }

    /**
     * 记录
     *
     * @param tenantId 租户id
     * @param username 用户名
     * @param logout   登出
     * @param message  错误消息
     * @author wangjiaxing
     * @date 2025/2/12 14:04
     */
    public void recordLogininfor(String tenantId, String username, String logout, String message) {
        LoginInfoEvent event = new LoginInfoEvent();
        event.setTenantId(tenantId);
        event.setUsername(username);
        event.setStatus(logout);
        event.setMessage(message);
        event.setRequest(ServletUtils.getRequest());

        SpringUtils.context().publishEvent(event);
    }

    /**
     * 构建LoginUser
     *
     * @param user {@link SysUserVo}
     * @return {@link LoginUser}
     * @author wangjiaxing
     * @date 2025/2/12 14:08
     */
    public LoginUser buildLoginUser(SysUserVo user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserId(user.getUserId());
        loginUser.setDeptId(user.getDeptId());
        loginUser.setUsername(user.getUserName());
        loginUser.setNickname(user.getNickName());
        loginUser.setUserType(user.getUserType());
        loginUser.setMenuPermission(sysPermissionService.getMenuPermission(user.getUserId()));
        loginUser.setRolePermission(sysPermissionService.getRolePermission(user.getUserId()));
        if (!Objects.isNull(user.getDeptId())) {
            Opt<SysDeptVo> deptOpt = Opt.of(user.getDeptId()).map(sysDeptService::selectDeptById);
            loginUser.setDeptName(deptOpt.map(SysDeptVo::getDeptName).orElse(StringUtils.EMPTY));
        }
        List<SysRoleVo> roles = sysRoleService.selectRolesByUserId(user.getUserId());
        List<SysPostVo> posts = sysPostService.selectPostsByUserId(user.getUserId());
        loginUser.setRoles(BeanUtil.copyToList(roles, RoleDTO.class));
        loginUser.setPosts(BeanUtil.copyToList(posts, PostDTO.class));
        return loginUser;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户id
     * @param ip     ip
     * @author wangjiaxing
     * @date 2025/2/12 14:22
     */
    public void recordLoginInfo(Long userId, String ip) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(ip);
        sysUser.setLoginDate(DateUtils.getNowDate());
        sysUser.setUpdateBy(userId);
        DataPermissionHelper.ignore(() -> userMapper.updateById(sysUser));
    }

    /**
     * 校验是否登录
     *
     * @param loginType 登陆类型
     * @param tenantId  租户id
     * @param username  用户名
     * @param supplier  {@link Supplier}
     * @author wangjiaxing
     * @date 2025/2/12 14:23
     */
    public void checkLogin(LoginType loginType, String tenantId, String username, Supplier<Boolean> supplier) {
        String errorKey = CacheConstants.PWD_ERR_CNT_KEY + username;
        String loginFail = Constants.LOGIN_FAIL;

        // 获取用户登录错误次数,默认为0 (可自定义限制策略 例如: key + username + ip)
        Integer errorNum = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);

        // 锁定时间内登录,则踢出
        if (errorNum >= maxRetryCount) {
            recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
            throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
        }

        // 登陆失败
        if (supplier.get()) {
            // 错误次数递增
            errorNum++;
            RedisUtils.setCacheObject(errorKey, errorNum);

            // 到达错误次数限制 锁定登录
            if (errorNum >= maxRetryCount) {
                recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
                throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
            } else {
                // 未达到错误上限
                recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNum, maxRetryCount));
                throw new UserException(loginType.getRetryLimitCount(), errorNum);
            }
        }

        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey);
    }

    /**
     * 校验租户
     *
     * @param tenantId 租户id
     * @author wangjiaxing
     * @date 2025/2/12 15:02
     */
    public void checkTenant(String tenantId) {
        if (!TenantHelper.isEnable()) {
            return;
        }
        if (StringUtils.isBlank(tenantId)) {
            throw new TenantException("tenant.number.not.blank");
        }
        if (TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
            return;
        }
        SysTenantVo tenant = sysTenantService.queryByTenantId(tenantId);
        if (ObjectUtil.isNull(tenant)) {
            log.info("[SysLoginService][checkTenant] 登录租户:{} 不存在", tenantId);
            throw new TenantException("tenant.not.exists");
        } else if (SystemConstants.DISABLE.equals(tenant.getStatus())) {
            log.info("[SysLoginService][checkTenant] 登录租户:{} 已停用", tenantId);
            throw new TenantException("tenant.blocked");
        } else if (ObjectUtil.isNotNull(tenant.getExpireTime()) && new Date().after(tenant.getExpireTime())) {
            log.info("[SysLoginService][checkTenant] 登录租户:{} 已过期", tenantId);
            throw new TenantException("tenant.expired");
        }
    }
}
