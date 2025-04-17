package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVEDestroyVmApiRequest extends PVEBaseApiRequest {

    /**
     * 必填
     * 节点
     */
    private String node;
    /**
     * 必填
     * 虚拟机id
     */
    private Long vmId;

    /**
     * 选填
     * 如果设置为true，会额外删除所有未在配置中引用但VMID匹配的磁盘
     * 用于清理可能存在的孤立磁盘文件
     */
    private Boolean destroyUnreferencedDisks = true;
    /**
     * 选填
     * 是否从所有配置中移除VMID
     * 包括备份任务、复制任务和高可用（HA）配置
     * 完全清理与该VM相关的所有配置
     */
    private Boolean purge = true;
    /**
     * 选填
     * 忽略锁定机制
     * 仅root用户可用此选项
     * 用于强制删除被锁定的VM
     */
    private Boolean skipLock;
}
