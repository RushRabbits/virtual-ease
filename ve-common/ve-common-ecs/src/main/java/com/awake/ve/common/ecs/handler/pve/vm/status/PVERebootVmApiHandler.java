package com.awake.ve.common.ecs.handler.pve.vm.status;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.api.vm.status.*;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.PVEJsonPathConstants.PVE_BASE_RESP;

@Slf4j
public class PVERebootVmApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    public PVERebootVmApiHandler() {
    }

    public static PVERebootVmApiHandler newInstance() {
        return new PVERebootVmApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVERebootVmApiRequest request)) {
            log.info("[PVERebootVmApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVERebootVmApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.REBOOT_VM.getApi();

        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(VM_ID, request.getVmId());
        String url = StrFormatter.format(api, params, true);

        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(NODE, request.getNode());
        jsonObject.set(VM_ID, request.getVmId());
        jsonObject.set(TIMEOUT, request.getTimeout());
        String body = jsonObject.toString();

        HttpResponse response = HttpRequest.post(url)
                .body(body)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();

        String string = response.body();
        JSONObject json = JSONUtil.parseObj(string);
        return new PVERebootApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
