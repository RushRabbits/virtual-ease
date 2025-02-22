package com.awake.ve.common.ecs.utils;

import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.api.ticket.PVETicketApiResponse;
import com.awake.ve.common.ecs.enums.PVEApi;
import com.awake.ve.common.translation.utils.RedisUtils;

public class EcsUtils {

    /**
     * 校验ticket并返回可用的ticket
     *
     * @author wangjiaxing
     * @date 2025/2/22 15:57
     */
    public static PVETicketApiResponse checkTicket() {
        PVETicketApiResponse response = RedisUtils.getCacheObject(CacheConstants.PVE_API_TICKET + CacheConstants.PVE_API_TICKET);
        if (response != null) {
            return response;
        }

        BaseApiResponse baseApiResponse = PVEApi.TICKET_CREATE.handle();
        if (baseApiResponse instanceof PVETicketApiResponse ticketApiResponse) {
            return ticketApiResponse;
        }
        return null;
    }
}
