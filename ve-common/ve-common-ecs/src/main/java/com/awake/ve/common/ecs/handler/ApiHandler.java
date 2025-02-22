package com.awake.ve.common.ecs.handler;

import com.awake.ve.common.ecs.api.request.BaseApiRequest;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.director.base.BaseApiDirector;

/**
 * api结果的基类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:04
 */
public interface ApiHandler {

    /**
     * 处理不需要请求参数的api
     *
     * @return {@link BaseApiRequest}
     */
    BaseApiResponse handle();

    /**
     * 处理需要请求参数的api
     *
     * @param director {@link BaseApiDirector}
     * @return {@link BaseApiResponse}
     * @author wangjiaxing
     * @date 2025/2/22 12:41
     */
    BaseApiResponse handle(BaseApiDirector director);
}
