package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVENodeRevertNetworkConfigApiRequest extends PVEBaseApiRequest {
    private String node;
}
