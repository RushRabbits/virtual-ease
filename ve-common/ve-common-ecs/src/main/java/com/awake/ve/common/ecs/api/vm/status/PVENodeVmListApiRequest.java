package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVENodeVmListApiRequest extends PVEBaseApiRequest {

    /**
     * 目标节点
     */
    private String node;

    /**
     * 是否返回活动虚拟机（running 状态）的完整状态信息
     * 0: 仅返回虚拟机 ID 和状态
     * 1: 返回虚拟机 ID、状态和完整状态信息
     */
    private Integer full;

}
