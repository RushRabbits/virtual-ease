package com.awake.ve.common.ecs.enums.network;

import lombok.Getter;

@Getter
public enum NetworkType {
    BRIDGE("bridge", "Linux网桥，用于虚拟机和容器连接"),
    BOND("bond", "链路聚合/绑定接口，提供冗余和增加带宽"),
    ETH("eth", "物理以太网接口"),
    ALIAS("alias", "网络接口别名，用于在同一物理接口上配置多个IP"),
    VLAN("vlan", "虚拟局域网接口，用于网络隔离"),
    OVS_BRIDGE("OVSBridge", "Open vSwitch网桥，提供高级网络功能"),
    OVS_BOND("OVSBond", "Open vSwitch绑定接口"),
    OVS_PORT("OVSPort", "Open vSwitch端口"),
    OVS_INT_PORT("OVSIntPort", "Open vSwitch内部端口"),
    ANY_BRIDGE("any_bridge", "任何类型的网桥接口"),
    ANY_LOCAL_BRIDGE("any_local_bridge", "任何本地网桥接口");

    private final String type;
    private final String description;

    NetworkType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * 通过类型字符串查找对应的枚举值
     *
     * @param type 网络类型字符串
     * @return 对应的NetworkType枚举，如果不存在则返回null
     */
    public static NetworkType fromType(String type) {
        for (NetworkType networkType : values()) {
            if (networkType.getType().equals(type)) {
                return networkType;
            }
        }
        return null;
    }
}
