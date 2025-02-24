package com.awake.ve.common.ecs.handler.impl.vm;

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
import com.awake.ve.common.ecs.api.vm.status.PVEResumeVmApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVEResumeVmApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.enums.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.JsonPathConstants.PVE_BASE_RESP;

/**
 * pve api 恢复虚拟机 处理器
 *
 * @author wangjiaxing
 * @date 2025/2/24 9:38
 */
@Slf4j
public class PVEResumeVmApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEResumeVmApiHandler() {
    }

    public static PVEResumeVmApiHandler newInstance() {
        return new PVEResumeVmApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVEResumeVmApiRequest request)) {
            log.info("[PVEResumeVmApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEResumeVmApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new ServiceException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.RESUME_VM.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(VM_ID, request.getVmId());
        String url = StrFormatter.format(api, params, true);

        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(NO_CHECK, request.getNoCheck());
        jsonObject.set(SKIP_LOCK, request.getSkipLock());
        String body = jsonObject.toString();

        HttpResponse response = HttpRequest.post(url)
                .body(body, APPLICATION_JSON)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true)
                .execute();

        String string = response.body();
        JSON json = JSONUtil.parse(string);
        return new PVEResumeVmApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
    }
}
