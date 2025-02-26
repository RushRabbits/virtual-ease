package com.awake.ve.common.ecs.handler.impl.vm.network;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.handler.ApiHandler;
import lombok.extern.slf4j.Slf4j;

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
        return null;
    }
}
