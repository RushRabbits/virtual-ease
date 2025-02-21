package com.awake.ve.common.ecs.domain.apiResult;

import com.awake.ve.common.ecs.domain.apiResult.base.ApiResult;
import lombok.Data;

/**
 * PVE api GET /api2/json/access/ticket 的返回值类
 *
 * @author wangjiaxing
 * @date 2025/2/21 19:05
 */
@Data
public class PVETicketApiResult implements ApiResult {

    /**
     * 用作header参数
     */
    private String CSRFPreventionToken;

    /**
     * 用作cookie
     */
    private String ticket;

    public static ApiResult newInstance() {
        return new PVETicketApiResult();
    }
}
