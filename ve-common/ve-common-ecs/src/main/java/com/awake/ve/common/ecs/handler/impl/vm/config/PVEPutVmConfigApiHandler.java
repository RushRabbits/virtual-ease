package com.awake.ve.common.ecs.handler.impl.vm.config;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.vm.config.PVEPutVmConfigApiRequest;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.handler.impl.vm.status.PVECreateOrRestoreVmApiHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * pve api 异步修改虚拟机配置api
 *
 * @author wangjiaxing
 * @date 2025/2/26 10:38
 */
@Slf4j
public class PVEPutVmConfigApiHandler implements ApiHandler {

    private static final EcsProperties ECS_PROPERTIES = SpringUtils.getBean(EcsProperties.class);

    private PVEPutVmConfigApiHandler() {

    }

    public static PVEPutVmConfigApiHandler newInstance() {
        return new PVEPutVmConfigApiHandler();
    }

    @Override
    public BaseApiResponse handle() {
        return null;
    }

    @Override
    public BaseApiResponse handle(BaseApiRequest baseApiRequest) {
        if (!(baseApiRequest instanceof PVEPutVmConfigApiRequest request)) {
            log.info("[PVEPutVmConfigApiHandler][handle] api请求参数异常 期待:{} , 实际:{}", PVEPutVmConfigApiRequest.class.getName(), baseApiRequest.getClass().getName());
            throw new RuntimeException("api请求参数类型异常");
        }
        return SpringUtils.getBean(PVECreateOrRestoreVmApiHandler.class).handle(request);
    }
}
