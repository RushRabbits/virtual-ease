package com.awake.ve.common.ecs.enums.network;

import lombok.Getter;

/**
 * 网络绑定传输哈希策略
 *
 * @author wangjiaxing
 * @date 2025/2/26 16:58
 */
@Getter
public enum NetworkBondXmitHashPolicy {
    LAYER2("layer2", "基于MAC地址的哈希，仅使用二层信息决定传输接口"),

    LAYER2_3("layer2+3", "基于IP地址和MAC地址的哈希，结合二层和三层信息决定传输接口"),

    LAYER3_4("layer3+4", "基于IP地址和端口的哈希，结合三层和四层信息决定传输接口，提供最佳的负载分布");

    private final String value;
    private final String description;

    NetworkBondXmitHashPolicy(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 从字符串值获取对应的枚举
     *
     * @param value 哈希策略的字符串值
     * @return 对应的BondXmitHashPolicy枚举值，如果不匹配则返回null
     */
    public static NetworkBondXmitHashPolicy fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (NetworkBondXmitHashPolicy policy : values()) {
            if (policy.getValue().equalsIgnoreCase(value)) {
                return policy;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.value;
    }
}