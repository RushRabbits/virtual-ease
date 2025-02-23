package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVEShutdownVmApiRequest extends PVEBaseApiRequest {
    /**
     * 必填
     * 节点名称
     */
    private String node;
    /**
     * 必填
     * VM ID
     */
    private Long vmId;
    /**
     * 选填
     * 强制关闭
     * 默认值：0（false）
     * 确保VM强制停止
     * 类似于物理机的强制关机
     */
    private Boolean forceStop = true;
    /**
     * 选填
     * 默认值：0（false）
     * 不要停用存储卷
     * 保持存储卷处于活动状态
     */
    private Boolean keepAlive = false;
    /**
     * 选填
     * 忽略锁定
     * 仅root用户可以使用此选项
     * 用于特殊情况下绕过锁定机制
     */
    private Boolean skipLock = false;
    /**
     * 选填
     * 超时（秒）
     */
    private Integer timeout;

}
