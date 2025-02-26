package com.awake.ve.common.ecs.handler.pve.vm.status;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiRequest;
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
public class PVENodeVmListApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVENodeVmListApiHandler() {

    }

    public static PVENodeVmListApiHandler newInstance() {
        return new PVENodeVmListApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVENodeVmListApiRequest request)) {
            log.info("[PVENodeVmListApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVENodeVmListApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new ServiceException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.NODE_VM_LIST.getApi();

        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        String url = StrFormatter.format(api, params, true);

        if (request.getFull() != null && request.getFull() == 1) {
            url = url + QUESTION_MARK + FULL + EQUAL_MARK + request.getFull();
        }

        HttpResponse response = HttpRequest.get(url)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();
        String string = response.body();
        log.info("[PVENodeVmListApiHandler][handle] 请求url:{} , 响应:{}", url, string);
        JSON json = JSONUtil.parse(string);

        return EcsConverter.buildPVENodeVmListApiResponse(json);
    }
}
