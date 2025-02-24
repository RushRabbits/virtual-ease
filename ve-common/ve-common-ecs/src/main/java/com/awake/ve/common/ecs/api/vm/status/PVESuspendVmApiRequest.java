package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class PVESuspendVmApiRequest extends PVEBaseApiRequest {

    /**
     * 必填
     * 节点名称
     */
    private String node;
    /**
     * 必填
     * 虚拟机id
     */
    private Long vmId;
    /**
     * 选填
     * 忽略锁定机制
     * 仅root用户可用此选项
     */
    private Boolean skipLock;
    /**
     * 选填
     * VM状态的存储位置
     * 指定将VM的状态信息保存在哪个存储上
     */
    private String stateStorage;
    /**
     * 选填
     * 默认值：0
     * 如果设置为1，将VM状态保存到磁盘
     * VM下次启动时会从保存的状态恢复
     * 类似于休眠功能
     */
    private Boolean toDisk;
}
