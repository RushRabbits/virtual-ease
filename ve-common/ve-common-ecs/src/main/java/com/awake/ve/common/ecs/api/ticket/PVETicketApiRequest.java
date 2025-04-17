package com.awake.ve.common.ecs.api.ticket;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PVE 获取ticket api 请求参数类
 *
 * @author wangjiaxing
 * @date 2025/2/22 14:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PVETicketApiRequest extends PVEBaseApiRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
