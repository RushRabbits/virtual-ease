package com.awake.ve.common.ssh.enums;

import lombok.Getter;

/**
 * jsch配置枚举
 *
 * @author wangjiaxing
 * @date 2025/2/19 11:51
 */
@Getter
public enum JschConfig {

    STRICT_HOST_CHECKING("StrictHostChecking", "no");

    private final String key;
    private final String value;

    JschConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
