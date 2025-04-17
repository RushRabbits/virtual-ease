package com.awake.ve.common.sensitive.core;

/**
 * 脱敏服务
 * 系统提供的默认实现 {@link com.awake.ve.system.service.impl.SysSensitiveServiceImpl}
 * 可根据业务自行重写实现
 *
 * @author wangjiaxing
 * @date 2025/2/8 15:02
 */
public interface SensitiveService {

    /**
     * 是否脱敏
     */
    boolean isSensitive(String roleKey , String perms);
}
