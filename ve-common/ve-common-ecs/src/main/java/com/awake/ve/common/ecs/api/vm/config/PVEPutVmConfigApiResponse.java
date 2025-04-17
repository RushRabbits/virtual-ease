package com.awake.ve.common.ecs.api.vm.config;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 同步修改虚拟机配置的响应
 *
 * @author wangjiaxing
 * @date 2025/2/26 10:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVEPutVmConfigApiResponse implements BaseApiResponse {
    private String data;
}
