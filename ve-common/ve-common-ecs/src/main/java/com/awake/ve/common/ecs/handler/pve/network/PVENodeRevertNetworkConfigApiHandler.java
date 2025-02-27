package com.awake.ve.common.ecs.handler.pve.network;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.network.PVENodeRevertNetworkConfigApiRequest;
import com.awake.ve.common.ecs.api.network.PVENodeRevertNetworkConfigApiResponse;
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
 * pve api 节点下网络配置退回到上一版本处理器
 *
 * @author wangjiaxing
 * @date 2025/2/26 18:31
 */
@Slf4j
public class PVENodeRevertNetworkConfigApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVENodeRevertNetworkConfigApiHandler() {

    }

    public static PVENodeRevertNetworkConfigApiHandler newInstance() {
        return new PVENodeRevertNetworkConfigApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVENodeRevertNetworkConfigApiRequest request)) {
            log.info("[PVENodeRevertNetworkConfigApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVENodeRevertNetworkConfigApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new ServiceException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();
        String api = PVEApi.NODE_REVERT_NETWORK_CONFIG.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        String url = StrFormatter.format(api, params, true);

        HttpRequest httpRequest = HttpRequest.delete(url)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true);
        try (HttpResponse response = httpRequest.execute()) {
            String string = response.body();
            log.info("[PVENodeRevertNetworkConfigApiHandler][handle] 请求url:{} , 响应:{}", url, string);
            JSON json = JSONUtil.parse(string);
            return new PVENodeRevertNetworkConfigApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
        } catch (Exception e) {
            log.error("[PVENodeRevertNetworkConfigApiHandler][handle] 重新加载网络配置请求异常", e);
            throw new RuntimeException(e);
        }
    }
}
