package com.awake.ve.virtual.service.impl;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.network.PVENodeNetWorkListApiRequest;
import com.awake.ve.common.ecs.api.network.PVENodeNetworkListApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.core.EcsClient;
import com.awake.ve.common.ecs.domain.network.Network;
import com.awake.ve.virtual.service.IVeNetworkInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class VeNetworkInfoServiceImpl implements IVeNetworkInfoService {

    private final EcsProperties ecsProperties = SpringUtils.getBean(EcsProperties.class);

    private final EcsClient ecsClient;

    /**
     * 查询节点下的网络列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 16:32
     */
    @Override
    public List<Network> networks() {
        // api参数
        PVENodeNetWorkListApiRequest request = PVENodeNetWorkListApiRequest.builder().node(ecsProperties.getNode()).build();

        // api响应
        PVENodeNetworkListApiResponse response = (PVENodeNetworkListApiResponse) ecsClient.networkList(request);
        return response.getNetworks();
    }
}
