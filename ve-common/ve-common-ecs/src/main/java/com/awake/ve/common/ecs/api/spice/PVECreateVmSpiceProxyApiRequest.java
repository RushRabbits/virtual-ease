package com.awake.ve.common.ecs.api.spice;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

/**
 * pve api 创建spice代理连接 请求参数
 *
 * @author wangjiaxing
 * @date 2025/2/27 14:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVECreateVmSpiceProxyApiRequest extends PVEBaseApiRequest {

    /**
     * 节点
     */
    private String node;

    /**
     * 虚拟机id
     */
    private Long vmId;

    // 以下参数选填

    /**
     * SPICE代理服务器。客户端可以指定代理服务器。默认情况下，返回虚拟机当前运行的节点。
     */
    private String proxy;
}
