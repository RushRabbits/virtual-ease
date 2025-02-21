package com.awake.ve.common.ecs.api.response;

import lombok.Data;

@Data
public class PVETicketApiResponse implements BaseApiResponse {

    private String ticket;
    private String CSRFPreventionToken;

    public PVETicketApiResponse(String ticket, String CSRFPreventionToken) {
        this.ticket = ticket;
        this.CSRFPreventionToken = CSRFPreventionToken;
    }
}
