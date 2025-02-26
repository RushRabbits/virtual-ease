package com.awake.ve.common.ecs.handler.impl.vm.status;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.core.constant.CacheNames;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.enums.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.translation.utils.RedisUtils;
import lombok.Data;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.JsonPathConstants.PVE_CSRF_PREVENTION_TOKEN;
import static com.awake.ve.common.ecs.constants.JsonPathConstants.PVE_TICKET;

/**
 * PVE api GET /api2/json/access/ticket 的返回值类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:05
 */
@Data
public class PVETicketApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    /**
     * {@link PVEApi}
     */
    private PVEApi pveApi;

    private PVETicketApiHandler() {
    }

    public static PVETicketApiHandler newInstance() {
        PVETicketApiHandler handler = new PVETicketApiHandler();
        PVETicketApiRequest request = new PVETicketApiRequest();
        request.setUsername(ECS_PROPERTIES.getApiUsername());
        request.setPassword(ECS_PROPERTIES.getApiPassword());

        return handler;
    }

    @Override
    public BaseApiResponse handle() {
        Object cacheObject = RedisUtils.getCacheObject(CacheConstants.PVE_API_TICKET + CacheNames.PVE_API_TICKET);
        if (cacheObject instanceof PVETicketApiResponse apiResponse) {
            return apiResponse;
        }
        PVEApi ticketApi = PVEApi.TICKET_CREATE;
        String api = ticketApi.getApi();
        Map<String, Object> map = new HashMap<>();
        map.put(HOST, ECS_PROPERTIES.getHost());
        map.put(PORT, ECS_PROPERTIES.getPort());
        map.put(API_USERNAME, ECS_PROPERTIES.getApiUsername());
        map.put(API_PASSWORD, ECS_PROPERTIES.getApiPassword());
        String url = StrFormatter.format(api, map, true);

        HttpResponse response = HttpRequest.post(url).setFollowRedirects(true).execute();

        String body = response.body();

        JSON json = JSONUtil.parse(body);
        String ticket = json.getByPath(PVE_TICKET, String.class);
        String CSRFPreventionToken = json.getByPath(PVE_CSRF_PREVENTION_TOKEN, String.class);
        PVETicketApiResponse ticketApiResponse = new PVETicketApiResponse(ticket, CSRFPreventionToken);
        RedisUtils.setCacheObject(CacheConstants.PVE_API_TICKET + CacheNames.PVE_API_TICKET, ticketApiResponse, Duration.ofMinutes(30));
        return ticketApiResponse;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest request) {
        return this.handle();
    }
}
