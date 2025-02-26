package com.awake.ve.common.ecs.handler.pve.network;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.network.PVENodeDeleteNetworkApiRequest;
import com.awake.ve.common.ecs.api.network.PVENodeDeleteNetworkApiResponse;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.PVEJsonPathConstants.PVE_BASE_RESP;

/**
 * pve api 删除网络处理器
 *
 * @author wangjiaxing
 * @date 2025/2/26 20:19
 */
@Slf4j
public class PVENodeDeleteNetworkApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVENodeDeleteNetworkApiHandler() {
    }

    public static PVENodeDeleteNetworkApiHandler newInstance() {
        return new PVENodeDeleteNetworkApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVENodeDeleteNetworkApiRequest request)) {
            log.info("[PVENodeDeleteNetworkApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVENodeDeleteNetworkApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.DELETE_NETWORK_CONFIG.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(IFACE, request.getIface());
        String url = StrFormatter.format(api, params, true);

        HttpResponse response = HttpRequest.delete(url)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();
        String string = response.body();
        log.info("[PVENodeDeleteNetworkApiHandler][handle] 删除网络配置请求结果:{}", string);
        JSON json = JSONUtil.parse(string);
        return new PVENodeDeleteNetworkApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
