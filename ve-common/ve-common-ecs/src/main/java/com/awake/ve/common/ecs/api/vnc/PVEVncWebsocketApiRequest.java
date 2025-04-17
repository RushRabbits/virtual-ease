package com.awake.ve.common.ecs.api.vnc;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVEVncWebsocketApiRequest extends PVEBaseApiRequest {

    /**
     * 节点名称
     */
    private String node;

    /**
     * 端口 (5900 ~ 5999)
     */
    private Integer port;

    /**
     * 虚拟机id
     */
    private Long vmId;

    /**
     * vnc ticket
     */
    private String vncTicket;
}
