package com.awake.ve.common.ecs.domain.network.children;

import com.awake.ve.common.ecs.domain.network.Network;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BridgeNetwork extends Network {

    /**
     * 网关IP地址，网络流量离开本地网络时使用的路由器地址
     */
    private String gateway;

    /**
     * 分配给该网桥的IP地址
     */
    private String address;

    /**
     * 网络掩码
     */
    private String netmask;

    /**
     * 合并了IP地址和网络掩码的CIDR表示法
     */
    private String cidr;

    /**
     * 转发延迟时间，0表示禁用STP协议时的转发延迟
     */
    private String bridgeFd;

    /**
     * 连接到此网桥的物理网卡，这里是ens33网卡
     */
    private String bridgePorts;

    /**
     * 生成树协议(STP)
     */
    private String bridgeStp;


}
