package com.awake.ve.common.ecs.wrapper;

import com.awake.ve.common.ecs.handler.impl.vm.PVETicketApiHandler;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.wrapper.base.ApiWrapper;
import lombok.Data;

/**
 * 调用PVE API返回结果包装类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:01
 */
@Data
public class PVEApiResultWrapper implements ApiWrapper {

    private ApiHandler apiHandler;

    public static PVEApiResultWrapper newInstance() {
        return new PVEApiResultWrapper();
    }

    public void setApiHandler(ApiHandler apiHandler) {
        this.apiHandler = apiHandler;
    }

    public ApiHandler getApiHandler() {
        return apiHandler != null ? apiHandler : PVETicketApiHandler.newInstance();
    }

    @Override
    public void setApiResult(ApiHandler apiHandler) {

    }

    @Override
    public ApiHandler getApiResult() {
        return null;
    }
}
