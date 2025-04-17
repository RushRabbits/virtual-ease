package com.awake.ve.common.ecs.api.vnc;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVEVncProxyApiResponse implements BaseApiResponse {

    /**
     * 端口
     */
    private Integer port;
    /**
     * pve 任务id
     */
    private String uPid;
    /**
     * 用户
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * ticket
     */
    private String vncTicket;
    /**
     * 证书
     */
    private String cert;
}
