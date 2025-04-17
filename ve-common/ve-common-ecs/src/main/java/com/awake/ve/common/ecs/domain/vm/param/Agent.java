package com.awake.ve.common.ecs.domain.vm.param;

import lombok.Data;

@Data
public class Agent {

    /**
     * 是否开启qemu代理
     */
    private Integer enabled;

    /**
     * 代理的类型
     */
    private String type;
}
