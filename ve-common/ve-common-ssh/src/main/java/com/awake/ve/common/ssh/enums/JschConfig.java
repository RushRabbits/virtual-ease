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

    STRICT_HOST_CHECKING("StrictHostKeyChecking", "no"),

    CHARSET_SHELL("charset.shell", "UTF-8"),

    CHARSET_DEFAULT("charset.default" , "UTF-8"),

    CHARSET_KEY("charset.kex" , "UTF-8"),

    CHARSET_USERAUTH("charset.userauth" , "UTF-8"),

    REMOTE_CHARSET("RemoteCharset" , "UTF-8"),

    TERMINAL_CHARSET("terminalEncoding" , "UTF-8");

    private final String key;
    private final String value;

    JschConfig(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
