package com.awake.ve.common.ecs.domain.vm.param;

import lombok.Data;

@Data
public class Net {
    /**
     * 网卡模型
     */
    private String model;

    /**
     * 桥接网络
     */
    private String bridge;

    /**
     * 是否开启防火墙
     */
    private Integer firewall;

    /**
     * vlanTag
     */
    private Integer vlanTag;
}
