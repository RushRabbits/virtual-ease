package com.awake.ve.common.ecs.wrapper.base;

import com.awake.ve.common.ecs.domain.apiResult.base.ApiResult;

/**
 * api包装类的基类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:03
 */
public interface ApiWrapper {

    void setApiResult(ApiResult apiResult);

    ApiResult getApiResult();
}
