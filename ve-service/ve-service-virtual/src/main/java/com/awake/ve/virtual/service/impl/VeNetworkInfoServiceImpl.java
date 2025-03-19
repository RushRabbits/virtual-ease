package com.awake.ve.virtual.service.impl;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.network.PVENodeNetWorkListApiRequest;
import com.awake.ve.common.ecs.api.network.PVENodeNetworkListApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.domain.network.Network;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.virtual.service.IVeNetworkInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VeNetworkInfoServiceImpl implements IVeNetworkInfoService {

    private final EcsProperties ecsProperties = SpringUtils.getBean(EcsProperties.class);

    /**
     * 查询节点下的网络列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 16:32
     */
    @Override
    public List<Network> networks() {
        ApiHandler apiHandler = PVEApi.NODE_NETWORK_LIST.getApiHandler();

        // api参数
        PVENodeNetWorkListApiRequest request = PVENodeNetWorkListApiRequest.builder().node(ecsProperties.getNode()).build();

        // api响应
        PVENodeNetworkListApiResponse response = (PVENodeNetworkListApiResponse) apiHandler.handle(request);
        return response.getNetworks();
    }
}
