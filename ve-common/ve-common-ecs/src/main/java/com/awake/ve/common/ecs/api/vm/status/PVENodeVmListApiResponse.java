package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.domain.PveVmInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVENodeVmListApiResponse implements BaseApiResponse {

    /**
     * 指定节点下的虚拟机列表
     */
    private List<PveVmInfo> vmList;
}
