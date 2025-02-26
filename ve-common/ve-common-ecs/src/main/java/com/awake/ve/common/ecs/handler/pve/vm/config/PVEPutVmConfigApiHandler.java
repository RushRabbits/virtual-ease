package com.awake.ve.common.ecs.handler.pve.vm.config;

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
import com.awake.ve.common.ecs.api.vm.config.PVEPutVmConfigApiRequest;
import com.awake.ve.common.ecs.api.vm.config.PVEPutVmConfigApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.converter.EcsConverter;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.PVEJsonPathConstants.PVE_BASE_RESP;

/**
 * pve api 异步修改虚拟机配置api
 *
 * @author wangjiaxing
 * @date 2025/2/26 10:38
 */
@Slf4j
public class PVEPutVmConfigApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEPutVmConfigApiHandler() {

    }

    public static PVEPutVmConfigApiHandler newInstance() {
        return new PVEPutVmConfigApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVEPutVmConfigApiRequest request)) {
            log.info("[PVEPutVmConfigApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEPutVmConfigApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.PUT_VM_CONFIG.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(VM_ID, request.getVmId());
        String url = StrFormatter.format(api, params, true);

        JSONObject jsonObject = EcsConverter.buildJSONObject(request);
        String body = jsonObject.toString();

        HttpResponse response = HttpRequest.post(url)
                .body(body, APPLICATION_JSON)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();

        String string = response.body();
        log.info("[PVEPutVmConfigApiHandler][handle] 请求url:{} , 响应:{}", url, string);
        JSON json = JSONUtil.parse(string);
        return new PVEPutVmConfigApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
