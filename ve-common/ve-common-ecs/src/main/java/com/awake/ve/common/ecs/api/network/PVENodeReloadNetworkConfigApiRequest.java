package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

/**
 * pve api 重新加载节点下的网络配置 请求参数
 *
 * @author wangjiaxing
 * @date 2025/2/26 18:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVENodeReloadNetworkConfigApiRequest extends PVEBaseApiRequest {

    /**
     * 节点名称
     */
    private String node;
}
