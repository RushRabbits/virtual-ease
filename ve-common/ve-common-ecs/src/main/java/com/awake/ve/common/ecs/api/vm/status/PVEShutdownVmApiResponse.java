package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 虚拟机关机响应
 *
 * @author wangjiaxing
 * @date 2025/2/23 10:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVEShutdownVmApiResponse implements BaseApiResponse {
    private String data;
}
