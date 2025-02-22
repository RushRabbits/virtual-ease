package com.awake.ve.common.ecs.api.template.response;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 根据虚拟机创建模板的返回值
 *
 * @author wangjiaxing
 * @date 2025/2/22 15:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PVECreateTemplateApiResponse implements BaseApiResponse {
    private String data;
}
