package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.domain.network.Network;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 获取网络配置 响应
 *
 * @author wangjiaxing
 * @date 2025/2/26 19:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVENodeGetNetworkConfigApiResponse implements BaseApiResponse {

    private Network network;
}
