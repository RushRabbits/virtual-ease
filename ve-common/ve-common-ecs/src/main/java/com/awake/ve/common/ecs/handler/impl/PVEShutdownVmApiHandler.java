package com.awake.ve.common.ecs.handler.impl;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVEShutdownVmApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVEShutdownVmApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.enums.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.JsonPathConstants.PVE_BASE_RESP;

@Slf4j
public class PVEShutdownVmApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEShutdownVmApiHandler() {
    }

    public static PVEShutdownVmApiHandler newInstance() {
        return new PVEShutdownVmApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if(!(baseApiRequest instanceof PVEShutdownVmApiRequest request)){
            log.info("[PVEShutdownVmApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEShutdownVmApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.SHUTDOWN_VM.getApi();
        Map<String , Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, ECS_PROPERTIES.getNode());
        params.put(VM_ID, request.getVmId());

        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(NODE, request.getNode());
        jsonObject.set(VM_ID, request.getVmId());
        jsonObject.set(FORCE_STOP, request.getForceStop()); // 如果不设置强制关闭,那么假如超过timeout还没关闭成功,就会关闭失败
        jsonObject.set(KEEP_ALIVE, request.getKeepAlive());
        jsonObject.set(SKIP_LOCK , request.getSkipLock());
        jsonObject.set(TIMEOUT, request.getTimeout());
        String body = jsonObject.toString();

        String url = StrFormatter.format(api, params, true);
        HttpResponse response = HttpRequest.post(url)
                .body(body, APPLICATION_JSON)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();
        EcsUtils.rmLockConf(request.getVmId());

        JSON json = JSONUtil.parse(response.body());
        return new PVEShutdownVmApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
