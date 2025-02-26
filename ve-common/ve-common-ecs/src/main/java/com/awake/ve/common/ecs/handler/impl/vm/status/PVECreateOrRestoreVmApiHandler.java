package com.awake.ve.common.ecs.handler.impl.vm.status;

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
import com.awake.ve.common.ecs.api.vm.status.PVECreateOrRestoreVmApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVECreateOrRestoreVmApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.converter.EcsConverter;
import com.awake.ve.common.ecs.enums.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.JsonPathConstants.PVE_BASE_RESP;

/**
 * pve api 创建/恢复虚拟机的处理器
 *
 * @author wangjiaxing
 * @date 2025/2/24 14:59
 */
@Slf4j
public class PVECreateOrRestoreVmApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVECreateOrRestoreVmApiHandler() {

    }

    public static PVECreateOrRestoreVmApiHandler newInstance() {
        return new PVECreateOrRestoreVmApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVECreateOrRestoreVmApiRequest request)) {
            log.info("[PVECreateOrRestoreVmApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVECreateOrRestoreVmApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();
        String api = PVEApi.CREATE_OR_RESTORE_VM.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
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
        log.info("[PVECreateOrRestoreVmApiHandler][handle] 请求url:{} , 响应:{}", url, string);
        JSON json = JSONUtil.parse(string);
        return new PVECreateOrRestoreVmApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
