package com.awake.ve.demo.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.ecs.domain.apiResult.base.ApiResult;
import com.awake.ve.common.ecs.enums.PVEApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@SaIgnore
@RequestMapping("/demo/ecs")
public class EcsDemoController {
    @GetMapping("/createTicket")
    public R<ApiResult> createTicket() {
        String username = "API@pve";
        String password = "123qwe...";

        PVEApi ticketApi = PVEApi.TICKET_CREATE;
        String api = ticketApi.getApi();

        Map<String, Object> map = new HashMap<>();
        map.put("host", "192.168.1.139");
        map.put("port", "8006");
        map.put("username", username);
        map.put("password", password);
        String url = StrFormatter.format(api, map, true);

        HttpResponse response = HttpRequest.post(url).setFollowRedirects(true).execute();

        String body = response.body();

        return R.ok(body);
    }
}
