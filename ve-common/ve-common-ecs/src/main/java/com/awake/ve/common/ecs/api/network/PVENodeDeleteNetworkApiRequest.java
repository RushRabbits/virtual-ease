package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

/**
 * pve api 删除网络接口请求
 *
 * @author wangjiaxing
 * @date 2025/2/26 20:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVENodeDeleteNetworkApiRequest extends PVEBaseApiRequest {

    /**
     * 节点名称
     */
    private String node;

    /**
     * 网络接口名称
     */
    private String iface;
}
