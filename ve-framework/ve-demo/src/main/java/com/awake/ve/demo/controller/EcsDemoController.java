package com.awake.ve.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.handler.ApiHandler;
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
    public R<ApiHandler> createTicket() {
        return R.ok();
    }
}
