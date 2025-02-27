package com.awake.ve.common.ecs.handler.pve.vnc;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.api.vnc.PVEVncProxyApiRequest;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.converter.EcsConverter;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;

@Slf4j
public class PVEVncProxyApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEVncProxyApiHandler() {
    }

    public static PVEVncProxyApiHandler newInstance() {
        return new PVEVncProxyApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVEVncProxyApiRequest request)) {
            log.info("[PVEVncProxyApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEVncProxyApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new ServiceException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.GET_VM_VNC_PROXY.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(VM_ID, request.getVmId());
        String url = StrFormatter.format(api, params, true);

        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(GENERATE_PASSWORD, request.getGeneratePassword() != null && request.getGeneratePassword() ? 1 : null);
        jsonObject.set(WEBSOCKET, request.getWebsocket() != null && request.getWebsocket() ? 1 : null);
        String body = jsonObject.toString();

        HttpRequest httpRequest = HttpRequest.post(url)
                .body(body)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true);
        try (HttpResponse response = httpRequest.execute()) {
            String string = response.body();
            log.info("[PVEVncProxyApiHandler][handle] 请求url:{} , 响应:{}", url, string);
            JSON json = JSONUtil.parse(string);
            return EcsConverter.buildPVEVncProxyApiResponse(json);
        } catch (Exception e) {
            log.error("[PVEVncProxyApiHandler][handle] 获取vnc代理请求异常", e);
            throw new RuntimeException(e);
        }
    }
}
