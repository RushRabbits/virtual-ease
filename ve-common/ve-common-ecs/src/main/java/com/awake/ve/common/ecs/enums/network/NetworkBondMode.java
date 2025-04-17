package com.awake.ve.common.ecs.enums.network;

import lombok.Getter;

/**
 * 网络绑定模式选项
 *
 * @author wangjiaxing
 * @date 2025/2/26 16:59
 */
@Getter
public enum NetworkBondMode {
    BALANCE_RR("balance-rr", "轮询模式，按顺序传输数据包，提供负载均衡和容错能力"),
    ACTIVE_BACKUP("active-backup", "主备模式，只有一个接口处于活动状态，当活动接口故障时备用接口接管"),
    BALANCE_XOR("balance-xor", "基于XOR散列的负载均衡，根据源和目标MAC地址选择传输接口"),
    BROADCAST("broadcast", "广播模式，在所有接口上同时传输所有数据包，提供最大容错性"),
    IEEE_8023AD("802.3ad", "IEEE 802.3ad动态链路聚合，需要支持802.3ad的交换机"),
    BALANCE_TLB("balance-tlb", "自适应传输负载均衡，不需要特殊交换机支持"),
    BALANCE_ALB("balance-alb", "自适应负载均衡，包含传输和接收负载均衡"),
    BALANCE_SLB("balance-slb", "交换机负载均衡，适用于某些特定的交换机环境"),
    LACP_BALANCE_SLB("lacp-balance-slb", "结合LACP和交换机负载均衡"),
    LACP_BALANCE_TCP("lacp-balance-tcp", "基于TCP连接的负载均衡，通过分析TCP流量优化负载分配");

    private final String value;
    private final String description;

    NetworkBondMode(String value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * 从字符串值获取对应的枚举
     *
     * @param value 绑定模式的字符串值
     * @return 对应的BondMode枚举值，如果不匹配则返回null
     */
    public static NetworkBondMode fromValue(String value) {
        if (value == null) {
            return null;
        }

        for (NetworkBondMode mode : values()) {
            if (mode.getValue().equalsIgnoreCase(value)) {
                return mode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.value;
    }
}