package com.awake.ve.common.ecs.wrapper;

import com.awake.ve.common.ecs.domain.apiResult.PVETicketApiResult;
import com.awake.ve.common.ecs.domain.apiResult.base.ApiResult;
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

    private ApiResult apiResult;

    public static PVEApiResultWrapper newInstance() {
        return new PVEApiResultWrapper();
    }

    @Override
    public void setApiResult(ApiResult apiResult) {
        this.apiResult = apiResult;
    }

    @Override
    public ApiResult getApiResult() {
        return apiResult != null ? apiResult : PVETicketApiResult.newInstance();
    }
}
