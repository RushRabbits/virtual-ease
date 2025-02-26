package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVENodePutNetworkConfigApiRequest extends PVEBaseApiRequest {

    /**
     * 节点
     */
    private String node;

    /**
     * 网络名
     */
    private String iface;

    /**
     * 网络类型
     */
    private String type;

    // 以下字段选填


    // 基本网络配置
    /**
     * IPv4地址
     */
    private String address;

    /**
     * IPv6地址
     */
    private String address6;

    /**
     * 系统启动时自动启动接口
     */
    private Boolean autoStart;

    /**
     * IPv4 CIDR格式（如"192.168.1.100/24"）
     */
    private String cidr;

    /**
     * IPv6 CIDR格式
     */
    private String cidr6;

    /**
     * 网络接口注释
     */
    private String comments;

    /**
     * 网络接口注释
     */
    private String comments6;

    /**
     * 默认IPv4网关地址
     */
    private String gateway;

    /**
     * 默认IPv6网关地址
     */
    private String gateway6;

    /**
     * IPv4网络掩码（如"255.255.255.0"或"24"）
     */
    private String netmask;

    /**
     * IPv6网络掩码
     */
    private String netmask6;

    /**
     * 最大传输单元（1280-65520）
     * web端给的默认值1500,所以我们建议使用1500
     */
    private Integer mtu;


    // 桥接网络专有配置
    /**
     * 要添加到网桥的接口列表（空格分隔
     */
    private String bridgePorts;

    /**
     * 允许的VLAN ID列表（如"2 4 100-200"）
     */
    private String bridgeVids;

    /**
     * 启用VLAN感知网桥
     */
    private Boolean bridgeVlanAware;


    // vlan 专有配置
    /**
     * VLAN ID（1-4094）
     */
    private Integer vlanId;

    /**
     * VLAN所在的原始接口
     */
    private String vlanRawDevice;


    // ovs专有配置
    /**
     * OVS绑定使用的接口
     */
    private String ovsBonds;

    /**
     * OVS端口关联的OVS网桥
     */
    private String ovsBridge;

    /**
     * OVS接口选项
     */
    private String ovsOptions;

    /**
     * 要添加到OVS网桥的接口
     */
    private String ovsPorts;

    /**
     * VLAN标签（1-4094）
     */
    private String ovsTags;


    // 绑定接口特有配置
    /**
     * 主接口（用于active-backup模式）
     */
    private String bondPrimary;

    /**
     * 绑定模式
     * {@link com.awake.ve.common.ecs.enums.network.NetworkBondMode}
     */
    private String bondMode;

    /**
     * 传输散列策略
     */
    private String bondXmitHashPolicy;

    /**
     * 绑定的物理接口（空格分隔）
     */
    private String slaves;
}
