package com.awake.ve.common.ecs.api.request;

import lombok.Data;

@Data
public class PVEBaseApiRequest implements BaseApiRequest {

    /**
     * 用作header参数
     */
    private String CSRFPreventionToken;

    /**
     * 用作cookie
     */
    private String ticket;
}
