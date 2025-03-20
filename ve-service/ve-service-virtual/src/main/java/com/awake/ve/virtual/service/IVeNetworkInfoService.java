package com.awake.ve.virtual.service;

import com.awake.ve.common.ecs.domain.network.Network;
import com.awake.ve.virtual.domain.bo.VeCreateOrEditNetworkBo;
import com.awake.ve.virtual.domain.bo.VeGetNetworkConfigBo;

import java.util.List;

public interface IVeNetworkInfoService {

    /**
     * 查询节点下的网络列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 16:32
     */
    List<Network> networks();

    /**
     * 创建网络
     *
     * @param bo {@link VeCreateOrEditNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 14:43
     */
    Boolean create(VeCreateOrEditNetworkBo bo);

    /**
     * 获取网络配置
     *
     * @param bo {@link VeCreateOrEditNetworkBo}
     * @author wangjiaxing
     * @date 2025/3/20 15:00
     */
    Network getConfig(VeGetNetworkConfigBo bo);

    /**
     * 重新加载节点下的网络配置
     *
     * @author wangjiaxing
     * @date 2025/3/20 15:20
     */
    Boolean reloadConfig();

    /**
     * 恢复节点下的网络配置至上一个版本
     *
     * @author wangjiaxing
     * @date 2025/3/20 15:27
     */
    Boolean revertConfig();
}
