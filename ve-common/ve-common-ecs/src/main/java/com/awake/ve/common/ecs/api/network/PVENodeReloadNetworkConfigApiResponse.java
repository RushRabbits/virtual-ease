package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 重新加载节点下的网络配置响应
 *
 * @author wangjiaxing
 * @date 2025/2/26 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVENodeReloadNetworkConfigApiResponse implements BaseApiResponse {
    private String data;
}
