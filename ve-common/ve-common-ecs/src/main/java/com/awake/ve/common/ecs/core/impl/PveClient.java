package com.awake.ve.common.ecs.core.impl;

import com.awake.ve.common.core.service.EcsService;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiResponse;
import com.awake.ve.common.ecs.core.EcsClient;
import com.awake.ve.common.ecs.domain.vm.PveVmInfo;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.utils.EcsUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PVE虚拟化服务实现类
 *
 * @author wangjiaxing
 * @date 2024/12/16 10:43
 */
@Component
public class PveClient implements EcsClient, EcsService {

    /**
     * 获取ticket
     *
     * @author wangjiaxing
     * @date 2025/2/27 18:50
     */
    public BaseApiResponse auth() {
        return EcsUtils.checkTicket();
    }

    /**
     * 创建模板
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:53
     */
    @Override
    public BaseApiResponse createTemplate(BaseApiRequest request) {
        return PVEApi.CREATE_TEMPLATE.handle(request);
    }

    /**
     * 根据模板克隆虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:53
     */
    @Override
    public BaseApiResponse templateCloneVm(BaseApiRequest request) {
        return PVEApi.TEMPLATE_CLONE_VM.handle(request);
    }

    /**
     * 创建虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:54
     */
    @Override
    public BaseApiResponse createVm(BaseApiRequest request) {
        return PVEApi.CREATE_OR_RESTORE_VM.handle(request);
    }

    /**
     * restoreVm
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:55
     */
    @Override
    public BaseApiResponse restoreVm(BaseApiRequest request) {
        return PVEApi.CREATE_OR_RESTORE_VM.handle(request);
    }

    /**
     * 获取虚拟机配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:56
     */
    @Override
    public BaseApiResponse getVmConfig(BaseApiRequest request) {
        return PVEApi.GET_VM_CONFIG.handle(request);
    }

    /**
     * 异步修改虚拟机配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:57
     */
    @Override
    public BaseApiResponse editVmConfigAsync(BaseApiRequest request) {
        return PVEApi.POST_VM_CONFIG.handle(request);
    }

    /**
     * 同步修改虚拟机配置
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:57
     */
    @Override
    public BaseApiResponse editVmConfigSync(BaseApiRequest request) {
        return PVEApi.PUT_VM_CONFIG.handle(request);
    }

    /**
     * 启动虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:58
     */
    @Override
    public BaseApiResponse startVm(BaseApiRequest request) {
        return PVEApi.START_VM.handle(request);
    }

    /**
     * 关闭虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:59
     */
    @Override
    public BaseApiResponse shutdownVm(BaseApiRequest request) {
        return PVEApi.SHUTDOWN_VM.handle(request);
    }

    /**
     * 强制关闭虚拟机
     *
     * @param request {@link BaseApiRequest}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/27 18:59
     */
    @Override
    public BaseApiResponse stopVm(BaseApiRequest request) {
        return PVEApi.STOP_VM.handle(request);
    }

    @Override
    public BaseApiResponse rebootVm(BaseApiRequest request) {
        return PVEApi.REBOOT_VM.handle(request);
    }

    @Override
    public BaseApiResponse resetVm(BaseApiRequest request) {
        return PVEApi.RESET_VM.handle(request);
    }

    @Override
    public BaseApiResponse destroyVm(BaseApiRequest request) {
        return PVEApi.DESTROY_VM.handle(request);
    }

    @Override
    public BaseApiResponse suspendVm(BaseApiRequest request) {
        return PVEApi.SUSPEND_VM.handle(request);
    }

    @Override
    public BaseApiResponse resumeVm(BaseApiRequest request) {
        return PVEApi.RESUME_VM.handle(request);
    }

    @Override
    public BaseApiResponse vmStatus(BaseApiRequest request) {
        return PVEApi.VM_STATUS.handle(request);
    }

    @Override
    public BaseApiResponse vmList(BaseApiRequest request) {
        return PVEApi.NODE_VM_LIST.handle(request);
    }

    @Override
    public BaseApiResponse templateList(BaseApiRequest request) {
        return PVEApi.NODE_VM_LIST.handle(request);
    }

    @Override
    public BaseApiResponse networkList(BaseApiRequest request) {
        return PVEApi.NODE_NETWORK_LIST.handle(request);
    }

    @Override
    public BaseApiResponse createNetwork(BaseApiRequest request) {
        return PVEApi.NODE_CREATE_NETWORK.handle(request);
    }

    @Override
    public BaseApiResponse reloadNetwork(BaseApiRequest request) {
        return PVEApi.NODE_RELOAD_NETWORK_CONFIG.handle(request);
    }

    @Override
    public BaseApiResponse revertNetwork(BaseApiRequest request) {
        return PVEApi.NODE_REVERT_NETWORK_CONFIG.handle(request);
    }

    @Override
    public BaseApiResponse getNetworkConfig(BaseApiRequest request) {
        return PVEApi.GET_NETWORK_CONFIG.handle(request);
    }

    @Override
    public BaseApiResponse editNetworkConfig(BaseApiRequest request) {
        return PVEApi.PUT_NETWORK_CONFIG.handle(request);
    }

    @Override
    public BaseApiResponse deleteNetworkConfig(BaseApiRequest request) {
        return PVEApi.DELETE_NETWORK_CONFIG.handle(request);
    }

    @Override
    public BaseApiResponse createVmVncProxy(BaseApiRequest request) {
        return PVEApi.CREATE_VM_VNC_PROXY.handle(request);
    }

    @Override
    public BaseApiResponse getVmVncWebsocket(BaseApiRequest request) {
        return PVEApi.CREATE_VM_VNC_PROXY.handle(request);
    }

    @Override
    public BaseApiResponse createVmSpiceProxy(BaseApiRequest request) {
        return PVEApi.CREATE_VM_SPICE_PROXY.handle(request);
    }

    /**
     * 获取虚拟机系统所有现存的虚拟机和模板的id集合
     *
     * @author wangjiaxing
     * @date 14:49
     */
    @Override
    public List<Long> existVmAndTemplateIds() {
        PVENodeVmListApiRequest request = PVENodeVmListApiRequest.builder().node("pve").full(0).build();
        PVENodeVmListApiResponse response = (PVENodeVmListApiResponse) this.vmList(request);
        List<PveVmInfo> vmList = response.getVmList();
        return vmList.stream().map(PveVmInfo::getVmId).toList();
    }
}
