package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PVEVmStatusApiRequest extends PVEBaseApiRequest {
    /**
     * 必填
     * 节点名称
     */
    private String node;

    /**
     * 必填
     * 虚拟机ID
     */
    private Long vmId;
}
