package com.awake.ve.common.ecs.handler.pve.vm.status;

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
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVEStartVmApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVEStartVmApiResponse;
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
 * PVE 启动虚拟机api
 *
 * @author wangjiaxing
 * @date 2025/2/23 9:41
 */
@Slf4j
public class PVEStartVmApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEStartVmApiHandler() {
    }

    public static PVEStartVmApiHandler newInstance() {
        return new PVEStartVmApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVEStartVmApiRequest request)) {
            log.info("[PVEStartVmApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEStartVmApiHandler.class.getName(), baseApiRequest.getClass().getName());
            throw new ServiceException("api请求参数类型异常", HttpStatus.WARN);
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.START_VM.getApi();

        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(VM_ID, request.getVmId());

        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(NODE, request.getNode());
        jsonObject.set(VM_ID, request.getVmId());
        jsonObject.set(FORCE_CPU, request.getForceCpu());
        jsonObject.set(MACHINE, request.getMachine());
        jsonObject.set(MIGRATED_FROM, request.getMigratedFrom());
        jsonObject.set(MIGRATION_NETWORK, request.getMigrateNetwork());
        jsonObject.set(MIGRATION_TYPE, request.getMigrateType());
        // jsonObject.set(SKIP_LOCK, request.getSkipLock()); 不建议开启
        jsonObject.set(STATE_URI, request.getStateUri());
        jsonObject.set(TARGET_STORAGE, request.getTargetStorage());
        jsonObject.set(TIMEOUT, request.getTimeout());
        String body = jsonObject.toString();

        String url = StrFormatter.format(api, params, true);

        HttpResponse response = HttpRequest.post(url)
                .body(body, APPLICATION_JSON)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();

        JSON json = JSONUtil.parse(response.body());

        return new PVEStartVmApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
