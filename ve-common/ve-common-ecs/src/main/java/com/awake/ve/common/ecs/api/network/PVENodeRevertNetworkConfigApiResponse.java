package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 节点下网络配置退回到上一版本 响应
 *
 * @author wangjiaxing
 * @date 2025/2/26 18:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVENodeRevertNetworkConfigApiResponse implements BaseApiResponse {
    private String data;
}
