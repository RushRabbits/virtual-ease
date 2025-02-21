package com.awake.ve.common.ecs.handler;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;

/**
 * api结果的基类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:04
 */
public interface ApiHandler {

    BaseApiResponse handle();
}
