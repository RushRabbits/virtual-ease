package com.awake.ve.common.ecs.handler.pve.network;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.network.PVENodePutNetworkConfigApiRequest;
import com.awake.ve.common.ecs.api.network.PVENodePutNetworkConfigApiResponse;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
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

@Slf4j
public class PVENodePutNetworkConfigApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVENodePutNetworkConfigApiHandler() {
    }

    public static PVENodePutNetworkConfigApiHandler newInstance() {
        return new PVENodePutNetworkConfigApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVENodePutNetworkConfigApiRequest request)) {
            log.info("[PVENodePutNetworkConfigApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVENodePutNetworkConfigApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new ServiceException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.PUT_NETWORK_CONFIG.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(IFACE, request.getIface());
        String url = StrFormatter.format(api, params, true);

        JSONObject jsonObject = EcsConverter.buildJSONObject(request);
        String body = jsonObject.toString();

        HttpRequest httpRequest = HttpRequest.put(url)
                .body(body, APPLICATION_JSON)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true);
        try (HttpResponse response = httpRequest.execute()) {
            String string = response.body();
            log.info("[PVENodePutNetworkConfigApiHandler][handle] 请求url:{} , 响应:{}", url, string);
            JSON json = JSONUtil.parse(string);
            return new PVENodePutNetworkConfigApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
        } catch (Exception e) {
            log.error("[PVENodePutNetworkConfigApiHandler][handle] 删除网络配置请求异常", e);
            throw new RuntimeException(e);
        }
    }
}
