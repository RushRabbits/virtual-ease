package com.awake.ve.common.ecs.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PVETicketApiResponse implements BaseApiResponse {

    private String ticket;

    @JsonProperty("CSRFPreventionToken")
    private String CSRFPreventionToken;

    public PVETicketApiResponse(String ticket, String CSRFPreventionToken) {
        this.ticket = ticket;
        this.CSRFPreventionToken = CSRFPreventionToken;
    }
}
