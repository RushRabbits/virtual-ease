package com.awake.ve.common.ecs.domain;

import lombok.Data;

@Data
public class PveHaObject {

    /**
     * 虚拟机是否由 PVE 的高可用（HA）管理器管理
     * 0 - 虚拟机不是由 PVE 的高可用（HA）管理器管理
     * 1 - 虚拟机是由 PVE 的高可用（HA）管理器管理
     */
    private Integer managed;
}
