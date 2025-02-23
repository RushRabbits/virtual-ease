package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import com.awake.ve.common.ecs.enums.MigrateType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PVE api 启动虚拟机的请求参数
 *
 * @author wangjiaxing
 * @date 2025/2/23 9:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVEStartVmApiRequest extends PVEBaseApiRequest {
    /**
     * 目标虚拟机所在节点(必填)
     */
    private String node;
    /**
     * 虚拟机ID(必填)
     */
    private Long vmId;
    /**
     * 选填
     * 覆盖QEMU的CPU参数设置
     * 可以强制指定特定的CPU类型
     */
    private String forceCpu;
    /**
     * 选填
     * 指定QEMU机器类型
     * 格式：[type=<machine type>][,vionmmu=<intel|virtio>]
     */
    private String machine;
    /**
     * 选填
     * 集群节点名称
     * 用于迁移场景，表示VM从哪个节点迁移而来
     */
    private String migratedFrom;
    /**
     * 选填
     * 迁移使用的网络的CIDR
     * 指定用于迁移的子网
     */
    private String migrateNetwork;
    /**
     * 选填
     * secure | insecure
     * 默认使用SSH隑道加密迁移流量
     * 在完全私有网络中可以禁用加密以提高性能
     */
    private MigrateType migrateType;
    /**
     * 选填
     * 忽略锁定
     * 仅root用户可以使用此选项
     */
    private Boolean skipLock = false;
    /**
     * 选填
     * 保存/恢复状态的位置
     * 用于指定命令保存或恢复状态的位置
     */
    private String stateUri;
    /**
     * 选填
     * 源存储到目标存储的映射
     * 提供单个存储ID会将所有源存储映射到该存储
     * 使用特殊值"1"将映射每个源存储到其自身
     */
    private String targetStorage;
    /**
     * 选填
     * 最大等待超时时间（秒）
     * 默认值：max(30, vm RAM in GB)
     * 范围：0-N
     */
    private Integer timeout;

}
