package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVERebootVmApiRequest extends PVEBaseApiRequest {

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
     * 超时时间
     */
    private Integer timeout;
}
