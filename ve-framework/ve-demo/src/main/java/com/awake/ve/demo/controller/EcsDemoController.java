package com.awake.ve.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.template.request.PVECreateTemplateApiRequest;
import com.awake.ve.common.ecs.api.template.request.PVETemplateCreateVmApiRequest;
import com.awake.ve.common.ecs.api.vm.PVEStartVmApiRequest;
import com.awake.ve.common.ecs.enums.PVEApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
