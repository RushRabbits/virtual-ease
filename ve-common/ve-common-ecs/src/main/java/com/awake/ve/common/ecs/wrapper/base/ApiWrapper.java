package com.awake.ve.common.ecs.wrapper.base;

import com.awake.ve.common.ecs.handler.ApiHandler;

/**
 * api包装类的基类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:03
 */
public interface ApiWrapper {

    void setApiResult(ApiHandler apiHandler);

    ApiHandler getApiResult();
}
