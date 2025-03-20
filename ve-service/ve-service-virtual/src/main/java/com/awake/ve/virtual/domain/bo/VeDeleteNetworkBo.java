package com.awake.ve.virtual.domain.bo;

import lombok.Data;

@Data
public class VeDeleteNetworkBo {

    /**
     * 节点名称
     */
    private String node;

    /**
     * 网络接口名称
     */
    private String iface;
}
