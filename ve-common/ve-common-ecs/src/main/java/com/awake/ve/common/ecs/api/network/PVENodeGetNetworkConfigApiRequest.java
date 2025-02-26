package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

/**
 * pve api 获取网络配置 请求参数
 *
 * @author wangjiaxing
 * @date 2025/2/26 19:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PVENodeGetNetworkConfigApiRequest extends PVEBaseApiRequest {

    /**
     * 节点
     */
    private String node;

    /**
     * 网络名
     */
    private String iface;
}
