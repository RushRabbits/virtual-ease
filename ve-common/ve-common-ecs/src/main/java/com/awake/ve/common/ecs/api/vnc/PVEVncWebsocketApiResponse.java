package com.awake.ve.common.ecs.api.vnc;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVEVncWebsocketApiResponse implements BaseApiResponse {

    private Integer port;
}
