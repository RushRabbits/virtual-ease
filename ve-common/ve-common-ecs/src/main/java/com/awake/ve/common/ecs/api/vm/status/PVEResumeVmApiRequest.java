package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PVE api resume api 请求参数
 *
 * @author wangjiaxing
 * @date 2025/2/24 9:39
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class PVEResumeVmApiRequest extends PVEBaseApiRequest {
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
     * 是否跳过状态检查
     * 如果设置为true，不检查VM当前状态就直接尝试恢复
     * 使用时需谨慎，因为可能导致不可预期的结果
     */
    private Boolean noCheck;
    /**
     * 选填
     * 忽略锁定机制
     * 仅root用户可用此选项
     * 用于强制恢复被锁定的VM
     */
    private Boolean skipLock;
}
