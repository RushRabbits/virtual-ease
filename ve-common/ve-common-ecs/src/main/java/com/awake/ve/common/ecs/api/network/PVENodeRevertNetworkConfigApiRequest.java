package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

/**
 * pve api 节点下网络配置退回到上一版本 请求
 *
 * @author wangjiaxing
 * @date 2025/2/26 18:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVENodeRevertNetworkConfigApiRequest extends PVEBaseApiRequest {
    private String node;
}
