package com.awake.ve.common.ecs.handler.impl;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.request.PVETicketApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.response.PVETicketApiResponse;
import com.awake.ve.common.ecs.enums.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * PVE api GET /api2/json/access/ticket 的返回值类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:05
 */
@Data
public class PVETicketApiHandler implements ApiHandler {

    private PVETicketApiRequest request;
    private PVETicketApiResponse response;

    /**
     * {@link PVEApi}
     */
    private PVEApi pveApi;

    private PVETicketApiHandler() {
    }

    public static PVETicketApiHandler newInstance() {
        PVETicketApiHandler handler = new PVETicketApiHandler();
        PVETicketApiRequest request = new PVETicketApiRequest();
        request.setUsername("API@pve");
        request.setPassword("123qwe...");

        handler.setRequest(request);

        return handler;
    }

    @Override
    public BaseApiResponse handle() {

        PVEApi ticketApi = PVEApi.TICKET_CREATE;
        String api = ticketApi.getApi();
        String username = "API@pve";
        String password = "123qwe...";
        Map<String, Object> map = new HashMap<>();
        map.put("host", "192.168.1.139");
        map.put("port", "8006");
        map.put("username", username);
        map.put("password", password);
        String url = StrFormatter.format(api, map, true);

        HttpResponse response = HttpRequest.post(url).setFollowRedirects(true).execute();

        String body = response.body();

        JSON json = JSONUtil.parse(body);
        String ticket = json.getByPath("$.data.ticket", String.class);
        String CSRFPreventionToken = json.getByPath("$.data.CSRFPreventionToken", String.class);
        return new PVETicketApiResponse(ticket , CSRFPreventionToken);
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest request) {
        return this.handle();
    }
}
