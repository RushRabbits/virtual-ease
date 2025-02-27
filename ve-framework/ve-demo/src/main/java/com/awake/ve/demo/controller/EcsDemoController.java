package com.awake.ve.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.text.StrFormatter;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.api.network.*;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.template.request.PVECreateTemplateApiRequest;
import com.awake.ve.common.ecs.api.template.request.PVETemplateCreateVmApiRequest;
import com.awake.ve.common.ecs.api.vm.config.PVEGetVmConfigApiRequest;
import com.awake.ve.common.ecs.api.vm.config.PVEPostVmConfigApiRequest;
import com.awake.ve.common.ecs.api.vm.config.PVEPutVmConfigApiRequest;
import com.awake.ve.common.ecs.api.vm.status.*;
import com.awake.ve.common.ecs.api.vnc.PVEVncProxyApiRequest;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.enums.api.PVEApiParam;
import com.awake.ve.common.ecs.enums.cpu.ArchType;
import com.awake.ve.common.ecs.enums.network.NetworkCardModel;
import com.awake.ve.common.ecs.enums.vm.BiosType;
import com.awake.ve.common.ecs.enums.vm.OSType;
import com.awake.ve.common.ecs.enums.vm.ScsiHwType;
import com.awake.ve.common.ecs.enums.vm.VgaType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;


@Slf4j
@RestController
@SaIgnore
@RequestMapping("/demo/ecs")
public class EcsDemoController {
    @GetMapping("/createTicket")
    public R<BaseApiResponse> createTicket() {
        BaseApiResponse response = PVEApi.TICKET_CREATE.handle();
        return R.ok(response);
    }

    @GetMapping("/createTemplateByVmId")
    public R<BaseApiResponse> createTemplateByVmId() {
        PVECreateTemplateApiRequest request = PVECreateTemplateApiRequest.builder()
                .node("pve")
                .vmId(115L)
                .build();
        BaseApiResponse response = PVEApi.CREATE_TEMPLATE.handle(request);
        return R.ok(response);
    }

    @GetMapping("/templateCreateVm")
    public R<BaseApiResponse> templateCreateVm() {
        PVETemplateCreateVmApiRequest request = PVETemplateCreateVmApiRequest.builder()
                .node("pve")
                .vmId(115L)
                .newId(116L)
                .build();
        BaseApiResponse response = PVEApi.TEMPLATE_CLONE_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/startVm")
    public R<BaseApiResponse> startVm() {
        PVEStartVmApiRequest request = PVEStartVmApiRequest.builder()
                .node("pve")
                .vmId(116L)
                .build();
        BaseApiResponse response = PVEApi.START_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/shutdownVm")
    public R<BaseApiResponse> shutdownVm() {
        PVEShutdownVmApiRequest request = PVEShutdownVmApiRequest.builder()
                .node("pve")
                .vmId(116L)
                .skipLock(true)
                .timeout(10)
                .forceStop(true) // 如果不设置强制关闭,那么假如超过timeout还没关闭成功,就会关闭失败
                .build();
        BaseApiResponse response = PVEApi.SHUTDOWN_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/stopVm")
    public R<BaseApiResponse> stopVm() {
        PVEStopVmApiRequest request = PVEStopVmApiRequest.builder()
                .node("pve")
                .vmId(116L)
                .skipLock(true)
                .timeout(10)
                .build();
        BaseApiResponse response = PVEApi.STOP_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/rebootVm")
    public R<BaseApiResponse> rebootVm() {
        PVERebootVmApiRequest request = PVERebootVmApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .timeout(10)
                .build();
        BaseApiResponse response = PVEApi.REBOOT_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/destroyVm")
    public R<BaseApiResponse> destroyVm() {
        PVEDestroyVmApiRequest request = PVEDestroyVmApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .destroyUnreferencedDisks(true)
                .purge(true)
                .skipLock(true)
                .build();
        BaseApiResponse response = PVEApi.DESTROY_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/resetVm")
    public R<BaseApiResponse> resetVm() {
        PVEResetVmApiRequest request = PVEResetVmApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .skipLock(true)
                .build();
        BaseApiResponse response = PVEApi.RESET_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/suspendVm")
    public R<BaseApiResponse> suspendVm() {
        PVESuspendVmApiRequest request = PVESuspendVmApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .skipLock(true)
                .build();
        BaseApiResponse response = PVEApi.SUSPEND_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/resumeVm")
    public R<BaseApiResponse> resumeVm() {
        PVEResumeVmApiRequest request = PVEResumeVmApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .skipLock(true)
                .build();
        BaseApiResponse response = PVEApi.RESUME_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/vmStatus")
    public R<BaseApiResponse> vmStatus() {
        PVEVmStatusApiRequest request = PVEVmStatusApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .build();
        BaseApiResponse response = PVEApi.VM_STATUS.handle(request);
        return R.ok(response);
    }

    @GetMapping("/vmList")
    public R<BaseApiResponse> vmList() {
        PVENodeVmListApiRequest request = PVENodeVmListApiRequest.builder()
                .node("pve")
                .full(0)
                .build();
        BaseApiResponse response = PVEApi.NODE_VM_LIST.handle(request);
        return R.ok(response);
    }

    @GetMapping("/createVm")
    public R<BaseApiResponse> createVm() {
        String ipConfigParam = PVEApiParam.IP_CONFIG.getParam();
        Map<String, Object> ipParam = new HashMap<>();
        ipParam.put(IP, DHCP);
        ipParam.put(IP6, DHCP);
        List<String> ipconfigList = List.of(StrFormatter.format(ipConfigParam, ipParam, true));

        String scsiParam = PVEApiParam.SCSI.getParam();
        Map<String, Object> scsiParamMap = new HashMap<>();
        scsiParamMap.put(IMAGE_PATH, "/opt/images/ubuntu-22.04.qcow2");
        List<String> scsiList = List.of(StrFormatter.format(scsiParam, scsiParamMap, true));

        String netParam = PVEApiParam.NET.getParam();
        Map<String, Object> netParamMap = new HashMap<>();
        netParamMap.put(BRIDGE, "vmbr0");
        netParamMap.put(FIREWALL, 1);
        netParamMap.put(MODEL, NetworkCardModel.VIRTIO.getType());
        List<String> netList = List.of(StrFormatter.format(netParam, netParamMap, true));


        // List<String> scsiList = List.of("local:0,import-from=/opt/images/jammy-server-cloudimg-amd64.img,format=qcow2");
        // List<String> scsiList = List.of("local:0,import-from=/opt/images/ubuntu-22.04.qcow2,format=qcow2");
        String ideParam = PVEApiParam.IDE.getParam();
        Map<String, Object> ideParamMap = new HashMap<>();
        ideParamMap.put(LOCAL, "cloudinit");
        List<String> ideList = List.of(StrFormatter.format(ideParam, ideParamMap, true));

        String bootParam = PVEApiParam.BOOT_ORDER.getParam();
        Map<String, Object> bootParamMap = new HashMap<>();
        bootParamMap.put(BOOT, "scsi0");

        String agentParam = PVEApiParam.AGENT.getParam();
        Map<String, Object> agentParamMap = new HashMap<>();
        agentParamMap.put(ENABLED, 1);
        agentParamMap.put(TYPE, "virtio");

        PVECreateOrRestoreVmApiRequest request = PVECreateOrRestoreVmApiRequest.builder()
                .node("pve")
                .vmId(126L)
                .ipConfig(ipconfigList)
                .memory(2048D)
                .boot(StrFormatter.format(bootParam, bootParamMap, true))
                .ciUser("root")
                .ciPassword("123456789")
                .ciUpgrade(true)
                .sockets(2)
                .cores(4)
                .vga(VgaType.QXL.getType())
                .agent(StrFormatter.format(agentParam, agentParamMap, true))
                .cpu(ArchType.X86_64_V2_AES.getType())
                .osType(OSType.L26.getType())
                .scsiHw(ScsiHwType.VIRTIO_SCSI_PCI.getType())
                .net(netList)
                .scsi(scsiList)
                .ide(ideList)
                .bios(BiosType.SEA_BIOS.getType())
                // .lock("migrate")
                .build();
        BaseApiResponse response = PVEApi.CREATE_OR_RESTORE_VM.handle(request);
        return R.ok(response);
    }

    @GetMapping("/getVmConfig")
    public R<BaseApiResponse> getVmConfig() {
        PVEGetVmConfigApiRequest request = PVEGetVmConfigApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .current(true)
                .build();
        BaseApiResponse response = PVEApi.GET_VM_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/postVmConfig")
    public R<BaseApiResponse> postVmConfig() {

        PVEPostVmConfigApiRequest request = PVEPostVmConfigApiRequest.builder()
                .node("pve")
                .vmId(125L)
                .memory(1024D)
                .ciUser("root")
                .ciPassword("123456")
                .ciUpgrade(true)
                .build();
        BaseApiResponse response = PVEApi.POST_VM_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/putVmConfig")
    public R<BaseApiResponse> putVmConfig() {

        PVEPutVmConfigApiRequest request = PVEPutVmConfigApiRequest.builder()
                .node("pve")
                .vmId(125L)
                .memory(2048D)
                .ciUser("root")
                .ciPassword("123456789")
                .ciUpgrade(true)
                .build();
        BaseApiResponse response = PVEApi.PUT_VM_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/nodeNetworkList")
    public R<BaseApiResponse> nodeNetworkList() {

        PVENodeNetWorkListApiRequest request = PVENodeNetWorkListApiRequest.builder()
                .node("pve")
                .type("")
                .build();
        BaseApiResponse response = PVEApi.NODE_NETWORK_LIST.handle(request);
        return R.ok(response);
    }

    @GetMapping("/nodeCreateNetwork")
    public R<BaseApiResponse> nodeCreateNetwork() {

        PVENodeCreateNetworkApiRequest request = PVENodeCreateNetworkApiRequest.builder()
                .node("pve")
                .type("vlan")
                .vlanId(1)
                .vlanRawDevice("ens33")
                .iface("vlan05")
                .autoStart(true)
                .comments("test-create-vlan-01")
                .mtu(1500)
                .build();
        BaseApiResponse response = PVEApi.NODE_CREATE_NETWORK.handle(request);
        return R.ok(response);
    }

    @GetMapping("/nodeReloadNetwork")
    public R<BaseApiResponse> nodeReloadNetwork() {
        PVENodeReloadNetworkConfigApiRequest request = PVENodeReloadNetworkConfigApiRequest.builder()
                .node("pve")
                .build();
        BaseApiResponse response = PVEApi.NODE_RELOAD_NETWORK_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/nodeRevertNetwork")
    public R<BaseApiResponse> nodeRevertNetwork() {
        PVENodeRevertNetworkConfigApiRequest request = PVENodeRevertNetworkConfigApiRequest.builder()
                .node("pve")
                .build();
        BaseApiResponse response = PVEApi.NODE_REVERT_NETWORK_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/nodeNetworkConfig")
    public R<BaseApiResponse> nodeNetworkConfig() {
        PVENodeGetNetworkConfigApiRequest request = PVENodeGetNetworkConfigApiRequest.builder()
                .node("pve")
                .iface("vmbr0")
                .build();
        BaseApiResponse response = PVEApi.GET_NETWORK_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/putNetworkConfig")
    public R<BaseApiResponse> putNetworkConfig() {
        PVENodePutNetworkConfigApiRequest request = PVENodePutNetworkConfigApiRequest.builder()
                .node("pve")
                .iface("vlan2")
                .type("vlan")
                .cidr("192.168.1.139/25")
                .mtu(1280)
                .build();
        BaseApiResponse response = PVEApi.PUT_NETWORK_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/deleteNetworkConfig")
    public R<BaseApiResponse> deleteNetworkConfig() {
        PVENodeDeleteNetworkApiRequest request = PVENodeDeleteNetworkApiRequest.builder()
                .node("pve")
                .iface("vlan3")
                .build();
        BaseApiResponse response = PVEApi.DELETE_NETWORK_CONFIG.handle(request);
        return R.ok(response);
    }

    @GetMapping("/vmVncProxy")
    public R<BaseApiResponse> vmVncProxy() {
        PVEVncProxyApiRequest request = PVEVncProxyApiRequest.builder()
                .node("pve")
                .vmId(117L)
                .generatePassword(true)
                .websocket(true)
                .build();
        BaseApiResponse response = PVEApi.GET_VNC_PROXY.handle(request);
        return R.ok(response);
    }


}
