package com.awake.ve.virtual.service.impl;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ecs.api.network.*;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.core.EcsClient;
import com.awake.ve.common.ecs.domain.network.Network;
import com.awake.ve.virtual.domain.bo.VeCreateOrEditNetworkBo;
import com.awake.ve.virtual.domain.bo.VeDeleteNetworkBo;
import com.awake.ve.virtual.domain.bo.VeGetNetworkConfigBo;
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

    /**
     * 创建网络
     *
     * @param bo {@link VeCreateOrEditNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 14:43
     */
    @Override
    public Boolean create(VeCreateOrEditNetworkBo bo) {
        // api参数
        PVENodeCreateNetworkApiRequest request = PVENodeCreateNetworkApiRequest.builder()
                .node(ecsProperties.getNode())
                .type(bo.getType())
                .iface(bo.getIface())
                .build();

        // api响应
        PVENodeCreateNetworkApiResponse response = (PVENodeCreateNetworkApiResponse) ecsClient.createNetwork(request);
        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 获取网络配置
     *
     * @param bo {@link VeCreateOrEditNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 15:00
     */
    @Override
    public Network getConfig(VeGetNetworkConfigBo bo) {
        // api参数
        PVENodeGetNetworkConfigApiRequest request = PVENodeGetNetworkConfigApiRequest.builder()
                .node(ecsProperties.getNode())
                .iface(bo.getIface())
                .build();
        PVENodeGetNetworkConfigApiResponse response = (PVENodeGetNetworkConfigApiResponse) ecsClient.getNetworkConfig(request);

        // api响应
        return response.getNetwork();
    }

    /**
     * 重新加载节点下的网络配置
     *
     * @author wangjiaxing
     * @date 2025/3/20 15:20
     */
    @Override
    public Boolean reloadConfig() {
        // api参数
        PVENodeReloadNetworkConfigApiRequest request = PVENodeReloadNetworkConfigApiRequest.builder()
                .node(ecsProperties.getNode())
                .build();

        // api响应
        PVENodeReloadNetworkConfigApiResponse response = (PVENodeReloadNetworkConfigApiResponse) ecsClient.reloadNetwork(request);
        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 恢复节点下的网络配置至上一个版本
     *
     * @author wangjiaxing
     * @date 2025/3/20 15:27
     */
    @Override
    public Boolean revertConfig() {
        // api参数
        PVENodeRevertNetworkConfigApiRequest request = PVENodeRevertNetworkConfigApiRequest.builder()
                .node(ecsProperties.getNode())
                .build();

        // api响应
        PVENodeRevertNetworkConfigApiResponse response = (PVENodeRevertNetworkConfigApiResponse) ecsClient.revertNetwork(request);
        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 删除网络
     *
     * @param bo {@link VeDeleteNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 15:34
     */
    @Override
    public Boolean delete(VeDeleteNetworkBo bo) {
        // api参数
        PVENodeDeleteNetworkApiRequest request = PVENodeDeleteNetworkApiRequest.builder()
                .node(ecsProperties.getNode())
                .iface(bo.getIface())
                .build();

        // api响应
        PVENodeDeleteNetworkApiResponse response = (PVENodeDeleteNetworkApiResponse) ecsClient.deleteNetworkConfig(request);
        return StringUtils.isNotBlank(response.getData());
    }
}
