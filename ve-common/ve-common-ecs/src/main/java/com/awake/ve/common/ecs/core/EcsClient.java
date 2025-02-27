package com.awake.ve.common.ecs.core;

import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;


/**
 * ECS服务的核心Client
 *
 * @author wangjiaxing
 * @date 2024/12/16 10:29
 */
public interface EcsClient {

    /**
     * 虚拟化系统提供方认证
     *
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse auth();


    /**
     * 创建模板
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse createTemplate(BaseApiRequest request);

    /**
     * 根据模板克隆虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse templateCloneVm(BaseApiRequest request);


    /**
     * 创建虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse createVm(BaseApiRequest request);

    /**
     * restoreVm
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse restoreVm(BaseApiRequest request);

    /**
     * 获取虚拟机配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse getVmConfig(BaseApiRequest request);

    /**
     * 异步修改虚拟机配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse editVmConfigAsync(BaseApiRequest request);

    /**
     * 同步修改虚拟机配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse editVmConfigSync(BaseApiRequest request);

    /**
     * 开启虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse startVm(BaseApiRequest request);

    /**
     * 关闭虚拟机(正常关闭-按下关机键)
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse shutdownVm(BaseApiRequest request);

    /**
     * 强制关闭虚拟机(拔掉电源)
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse stopVm(BaseApiRequest request);

    /**
     * 重启虚拟机(正常重启)
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse rebootVm(BaseApiRequest request);

    /**
     * 重置虚拟机(暴力重启)
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse resetVm(BaseApiRequest request);

    /**
     * 销毁虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse destroyVm(BaseApiRequest request);

    /**
     * 暂停虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse suspendVm(BaseApiRequest request);

    /**
     * 恢复虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse resumeVm(BaseApiRequest request);

    /**
     * 恢复虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse vmStatus(BaseApiRequest request);

    /**
     * 节点下的虚拟机列表
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse vmList(BaseApiRequest request);

    /**
     * 节点下的模板列表
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse templateList(BaseApiRequest request);

    /**
     * 节点下的网络列表
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse networkList(BaseApiRequest request);

    /**
     * 创建网络
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse createNetwork(BaseApiRequest request);

    /**
     * 重载网络(更改网络配置后的应用)
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse reloadNetwork(BaseApiRequest request);

    /**
     * 回退网络配置(重载网络配置后,回退无效)
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse revertNetwork(BaseApiRequest request);

    /**
     * 获取网络配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse getNetworkConfig(BaseApiRequest request);

    /**
     * 修改网络配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse editNetworkConfig(BaseApiRequest request);

    /**
     * 删除网络配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse deleteNetworkConfig(BaseApiRequest request);

    /**
     * 创建 VNC代理连接
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse createVmVncProxy(BaseApiRequest request);

    /**
     * 获取 VNC websocket
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse getVmVncWebsocket(BaseApiRequest request);

    /**
     * 创建vm的spice代理
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     */
    BaseApiResponse createVmSpiceProxy(BaseApiRequest request);
}
