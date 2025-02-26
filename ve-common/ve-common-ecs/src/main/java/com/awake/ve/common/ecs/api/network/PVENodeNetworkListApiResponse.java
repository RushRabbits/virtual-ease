package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.domain.network.Network;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVENodeNetworkListApiResponse implements BaseApiResponse {
    /**
     * 网络列表
     */
    private List<Network> networks;
}
