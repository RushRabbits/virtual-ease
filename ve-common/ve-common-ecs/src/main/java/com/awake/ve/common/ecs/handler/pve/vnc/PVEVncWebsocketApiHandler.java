package com.awake.ve.common.ecs.handler.pve.vnc;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.api.vnc.PVEVncWebsocketApiRequest;
import com.awake.ve.common.ecs.api.vnc.PVEVncWebsocketApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.PVEJsonPathConstants.PVE_BASE_RESP;
import static com.awake.ve.common.ecs.constants.PVEJsonPathConstants.PVE_WEBSOCKET_PROXY_PORT;

@Slf4j
public class PVEVncWebsocketApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEVncWebsocketApiHandler() {
    }

    public static PVEVncWebsocketApiHandler newInstance() {
        return new PVEVncWebsocketApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVEVncWebsocketApiRequest request)) {
            log.info("[PVEVncWebsocketApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEVncWebsocketApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.GET_VM_VNC_WEBSOCKET.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(VM_ID, request.getVmId());
        String url = StrFormatter.format(api, params, true);

        StringBuilder sb = new StringBuilder(url);
        int flag = 0;
        if (Objects.nonNull(request.getPort())) {
            sb.append(PORT).append(EQUAL_MARK).append(request.getPort());
            flag++;
        }
        if (StringUtils.isNotBlank(request.getVncTicket())) {
            if (flag != 0) {
                sb.append(AND);
            }
            sb.append(VNC_TICKET).append(EQUAL_MARK).append(request.getVncTicket());
        }
        url = sb.toString();

        HttpRequest httpRequest = HttpRequest.get(url)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true);

        try (HttpResponse response = httpRequest.execute()) {
            String string = response.body();
            log.info("[PVEVncWebsocketApiHandler][handle] 请求url:{} , 响应:{}", url, string);
            JSON json = JSONUtil.parse(string);
            String data = json.getByPath(PVE_BASE_RESP, String.class);
            JSONObject jsonObject = JSONUtil.parseObj(data);
            return new PVEVncWebsocketApiResponse(jsonObject.getByPath(PVE_WEBSOCKET_PROXY_PORT, Integer.class));
        } catch (Exception e) {
            log.error("[PVEVncWebsocketApiHandler][handle] 获取虚拟机vnc websocket请求异常", e);
            throw new RuntimeException(e);
        }
    }
}
