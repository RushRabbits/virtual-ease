package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * pve api 关闭虚拟机api
 *
 * @author wangjiaxing
 * @date 2025/2/23 14:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVEStopVmApiRequest extends PVEBaseApiRequest {

    /**
     * 节点(必填)
     */
    private String node;
    /**
     * 虚拟机ID(必填)
     */
    private Long vmId;

    /**
     * 选填
     * 默认值：false
     * 设置是否保持存储卷处于激活状态
     * 不停用存储卷
     */
    private Boolean keepAlive = false;
    /**
     * 选填
     * 集群节点名称
     * 表示VM是从哪个节点迁移过来的
     */
    private String migratedFrom;
    /**
     * 选填
     * 默认值：0
     * 在强制停止前尝试中止正在进行的 'qmshutdown' 任务
     * 可以打断正在进行的正常关机流程
     */
    private Boolean overruleShutdown = true;
    /**
     * 选填
     * 默认值：false
     * 跳过虚拟机锁定
     * 忽略锁定机制
     * 仅 root 用户可用此选项
     */
    private Boolean skipLock = false;
    /**
     * 选填
     * 默认值：10
     * 等待超时时间（秒）
     * 最大等待时间
     */
    private Integer timeout = 10;

}
