package com.awake.ve.virtual.service;

import com.awake.ve.common.ecs.domain.network.Network;

import java.util.List;

public interface IVeNetworkInfoService {

    /**
     * 查询节点下的网络列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 16:32
     */
    List<Network> networks();
}
