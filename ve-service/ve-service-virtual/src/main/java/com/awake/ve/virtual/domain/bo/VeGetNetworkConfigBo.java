package com.awake.ve.virtual.domain.bo;

import lombok.Data;

@Data
public class VeGetNetworkConfigBo {
    /**
     * 节点
     */
    private String node;

    /**
     * 网络名
     */
    private String iface;
}
