package com.awake.ve.common.ecs.enums.network;

import lombok.Getter;

/**
 * 枚举表示网桥STP(生成树协议)的配置选项
 *
 * @author wangjiaxing
 * @date 2025/2/26 15:12
 */
@Getter
public enum NetworkBridgeStp {
    ON("on", "启用生成树协议，用于检测和防止网络环路"),
    OFF("off", "禁用生成树协议");

    private final String value;
    private final String description;

    NetworkBridgeStp(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 从字符串值获取对应的枚举
     *
     * @param value STP配置的字符串值
     * @return 对应的BridgeStp枚举值，如果不匹配则返回null
     */
    public static NetworkBridgeStp fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (NetworkBridgeStp stp : values()) {
            if (stp.getValue().equalsIgnoreCase(value)) {
                return stp;
            }
        }
        return null;
    }

    /**
     * 将布尔值转换为对应的BridgeStp枚举
     */
    public static NetworkBridgeStp fromBoolean(boolean enabled) {
        return enabled ? ON : OFF;
    }

    /**
     * 转换为布尔值
     */
    public boolean toBooleanValue() {
        return this == ON;
    }

    @Override
    public String toString() {
        return this.value;
    }
}