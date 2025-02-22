package com.awake.ve.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.template.PVECreateTemplateApiRequest;
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
                .vmId("115")
                .build();
        BaseApiResponse response = PVEApi.CREATE_TEMPLATE.handle(request);
        return R.ok(response);
    }
}
