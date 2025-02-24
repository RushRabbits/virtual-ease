package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PVESuspendVmApiResponse implements BaseApiResponse {
    private String data;
}
