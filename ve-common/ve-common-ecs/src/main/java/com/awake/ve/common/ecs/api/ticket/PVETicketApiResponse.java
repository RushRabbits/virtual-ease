package com.awake.ve.common.ecs.api.ticket;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * PVE 获取ticket api 请求response
 *
 * @author wangjiaxing
 * @date 2025/2/22 14:59
 */
@Data
@NoArgsConstructor
public class PVETicketApiResponse implements BaseApiResponse {

    private String ticket;

    @JsonProperty("CSRFPreventionToken")
    private String CSRFPreventionToken;

    public PVETicketApiResponse(String ticket, String CSRFPreventionToken) {
        this.ticket = ticket;
        this.CSRFPreventionToken = CSRFPreventionToken;
    }
}
