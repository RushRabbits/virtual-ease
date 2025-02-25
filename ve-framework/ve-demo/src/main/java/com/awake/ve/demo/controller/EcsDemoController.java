package com.awake.ve.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.template.request.PVECreateTemplateApiRequest;
import com.awake.ve.common.ecs.api.template.request.PVETemplateCreateVmApiRequest;
import com.awake.ve.common.ecs.api.vm.status.*;
import com.awake.ve.common.ecs.enums.OSType;
import com.awake.ve.common.ecs.enums.PVEApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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
        List<String> ipconfigList = List.of("ip=dhcp,ip6=dhcp");
        // List<String> scsiList = List.of("local:0,import-from=/opt/images/jammy-server-cloudimg-amd64.img,format=qcow2");
        // List<String> scsiList = List.of("local:0,import-from=/opt/images/ubuntu-22.04.qcow2,format=qcow2");
        List<String> scsiList = List.of("local:0,import-from=/opt/images/网络安全工具应用-任务2_使用XSSer进行自动化渗透测试-渗透机-disk1-ES10U.qcow2,format=qcow2");
        List<String> netList = List.of("model=virtio,bridge=vmbr0,firewall=1");
        List<String> ideList = List.of("local:cloudinit");

        PVECreateOrRestoreVmApiRequest request = PVECreateOrRestoreVmApiRequest.builder()
                .node("pve")
                .vmId(120L)
                .ipConfig(ipconfigList)
                .memory(2048D)
                .boot("order=scsi0")
                .ciUser("root")
                .ciPassword("123456789")
                .ciUpgrade(true)
                .sockets(2)
                .cores(4)
                .vga("qxl")
                .agent("enabled=1,type=virtio")
                .cpu("x86-64-v2-AES")
                .osType(OSType.L26.getType())
                .scsiHw("virtio-scsi-single")
                .net(netList)
                .scsi(scsiList)
                .ide(ideList)
                // .lock("migrate")
                .build();
        BaseApiResponse response = PVEApi.CREATE_OR_RESTORE_VM.handle(request);
        return R.ok(response);
    }

}
