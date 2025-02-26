package com.awake.ve.common.ecs.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * IP地址分配方法枚举
 * // TODO 官方文档未给出具体的方法字符串,以下是ai推理
 *
 * @author wangjiaxing
 * @date 2025/2/26 15:06
 */
@Getter
public enum NetworkConfigMethod {
    // 共享方法
    STATIC("static", "静态IP配置，手动设置固定的IP地址"),
    DHCP("dhcp", "从DHCP服务器自动获取IP地址配置"),
    MANUAL("manual", "接口启动但不自动配置IP地址"),
    NONE("none", "不配置网络层"),

    // IPv4特有
    LOOPBACK("loopback", "环回接口配置，通常用于lo接口", false, true),

    // IPv6特有
    AUTO("auto", "IPv6自动配置(SLAAC)", true, false),
    IGNORE("ignore", "完全忽略IPv6配置", true, false);

    private final String method;
    private final String description;
    private final boolean supportedInIPv6;
    private final boolean supportedInIPv4;

    NetworkConfigMethod(String method, String description) {
        this(method, description, true, true); // 默认两种都支持
    }

    NetworkConfigMethod(String method, String description, boolean supportedInIPv6, boolean supportedInIPv4) {
        this.method = method;
        this.description = description;
        this.supportedInIPv6 = supportedInIPv6;
        this.supportedInIPv4 = supportedInIPv4;
    }

    /**
     * 获取所有IPv4支持的方法
     */
    public static NetworkConfigMethod[] getIPv4Methods() {
        return Arrays.stream(values())
                .filter(NetworkConfigMethod::isSupportedInIPv4)
                .toArray(NetworkConfigMethod[]::new);
    }

    /**
     * 获取所有IPv6支持的方法
     */
    public static NetworkConfigMethod[] getIPv6Methods() {
        return Arrays.stream(values())
                .filter(NetworkConfigMethod::isSupportedInIPv6)
                .toArray(NetworkConfigMethod[]::new);
    }

    /**
     * 通过方法字符串查找对应的枚举值
     */
    public static NetworkConfigMethod fromMethod(String method) {
        if (method == null) {
            return null;
        }

        for (NetworkConfigMethod configMethod : values()) {
            if (configMethod.getMethod().equalsIgnoreCase(method)) {
                return configMethod;
            }
        }
        return null;
    }
}