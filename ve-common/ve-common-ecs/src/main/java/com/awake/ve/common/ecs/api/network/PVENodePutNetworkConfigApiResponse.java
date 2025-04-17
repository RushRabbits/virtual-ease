package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVENodePutNetworkConfigApiResponse implements BaseApiResponse {
    private String data;
}
