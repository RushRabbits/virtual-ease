package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 删除网络接口响应
 *
 * @author wangjiaxing
 * @date 2025/2/26 20:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVENodeDeleteNetworkApiResponse implements BaseApiResponse {
    private String data;
}
