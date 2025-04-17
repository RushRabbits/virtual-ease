package com.awake.ve.common.ecs.api.template.response;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PVETemplateCreateVmApiResponse implements BaseApiResponse {
    private String data;
}
