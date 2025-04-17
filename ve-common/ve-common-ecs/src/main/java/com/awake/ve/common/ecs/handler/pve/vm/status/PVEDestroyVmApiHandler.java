package com.awake.ve.common.ecs.handler.pve.vm.status;

import cn.hutool.core.text.StrFormatter;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVEDestroyVmApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVEDestroyVmApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.awake.ve.common.ecs.constants.ApiParamConstants.*;
import static com.awake.ve.common.ecs.constants.PVEJsonPathConstants.PVE_BASE_RESP;

@Slf4j
public class PVEDestroyVmApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEDestroyVmApiHandler() {
    }

    public static PVEDestroyVmApiHandler newInstance() {
        return new PVEDestroyVmApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVEDestroyVmApiRequest request)) {
            log.info("[PVEDestroyVmApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEDestroyVmApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }

        PVETicketApiResponse ticket = EcsUtils.checkTicket();

        String api = PVEApi.DESTROY_VM.getApi();
        Map<String, Object> params = new HashMap<>();
        params.put(HOST, ECS_PROPERTIES.getHost());
        params.put(PORT, ECS_PROPERTIES.getPort());
        params.put(NODE, request.getNode());
        params.put(VM_ID, request.getVmId());
        String url = StrFormatter.format(api, params, true);

        // 构建查询参数
        StringBuilder sb = new StringBuilder(url);

        // 可选参数，只有非空时才添加
        int flag = 0;
        if (request.getDestroyUnreferencedDisks() != null && request.getDestroyUnreferencedDisks()) {
            sb.append("destroy-unreferenced-disks=").append(request.getDestroyUnreferencedDisks() ? 1 : 0);
            flag++;
        }
        if (request.getPurge() != null && request.getPurge()) {
            if (flag != 0) {
                sb.append("&");
            }
            sb.append("purge=").append(request.getPurge() ? 1 : 0);
            flag++;
        }
        if (request.getSkipLock() != null && request.getSkipLock()) {
            if (flag != 0) {
                sb.append("&");
            }
            sb.append("skiplock=").append(request.getSkipLock() ? 1 : 0);
        }

        url = sb.toString();

        HttpRequest httpRequest = HttpRequest.delete(url)
                .header(CSRF_PREVENTION_TOKEN, ticket.getCSRFPreventionToken(), false)
                .header(COOKIE, PVE_AUTH_COOKIE + ticket.getTicket(), false)
                .setFollowRedirects(true);
        try (HttpResponse response = httpRequest.execute()) {
            String string = response.body();
            log.info("[PVEDestroyVmApiHandler][handle] 请求url:{} , 响应:{}", url, string);
            JSON json = JSONUtil.parse(string);
            return new PVEDestroyVmApiResponse(json.getByPath(PVE_BASE_RESP, String.class));
        } catch (Exception e) {
            log.error("[PVEDestroyVmApiHandler][handle] 删除虚拟机请求异常", e);
            throw new RuntimeException(e);
        }
    }
}
