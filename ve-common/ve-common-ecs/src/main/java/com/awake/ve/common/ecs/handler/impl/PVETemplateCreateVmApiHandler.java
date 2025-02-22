package com.awake.ve.common.ecs.handler.impl;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.constant.HttpStatus;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.template.request.PVETemplateCreateVmApiRequest;
import com.awake.ve.common.ecs.api.template.response.PVETemplateCreateVmApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
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
public class PVETemplateCreateVmApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    public static ApiHandler newInstance() {
        return new PVETemplateCreateVmApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        PVETicketApiResponse ticket = EcsUtils.checkTicket();
        if (!(baseApiRequest instanceof PVETemplateCreateVmApiRequest request)) {
            log.info("[PVETemplateCreateVmApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVETemplateCreateVmApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new ServiceException("api请求参数类型异常", HttpStatus.WARN);
        }

        String api = PVEApi.TEMPLATE_CLONE_VM.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, ECS_PROPERTIES.getNode());
        params.put(VM_ID, request.getVmId());

        JSONObject jsonObject = new JSONObject();
        jsonObject.set(NEW_ID, request.getNewId());
        jsonObject.set(NODE, request.getNode());
        jsonObject.set(VM_ID, request.getVmId());
        jsonObject.set(BW_LIMIT, request.getBwlimit());
        jsonObject.set(DESCRIPTION, request.getDescription());
        jsonObject.set(FORMAT, request.getFormat());
        jsonObject.set(FULL, request.getFull());
        jsonObject.set(NAME, request.getName());
        jsonObject.set(POOL, request.getPool());
        jsonObject.set(SNAPNAME, request.getSnapname());
        jsonObject.set(STORAGE, request.getStorage());
        jsonObject.set(TARGET, request.getTarget());
        String body = jsonObject.toString();

        String url = StrFormatter.format(api, params, true);

        HttpResponse response = HttpRequest.post(url)
                .body(body, APPLICATION_JSON)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();
        JSON json = JSONUtil.parse(response.body());
        return new PVETemplateCreateVmApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
