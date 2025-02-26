package com.awake.ve.common.ecs.api.network;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PVENodeNetWorkListApiRequest extends PVEBaseApiRequest {

    /**
     * 必填
     */
    private String node;

    // 以下为选填
    /**
     * 网络类型
     */
    private String type;
}
