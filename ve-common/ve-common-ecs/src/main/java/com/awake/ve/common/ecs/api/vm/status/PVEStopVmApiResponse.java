package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api  关闭虚拟机响应
 *
 * @author wangjiaxing
 * @date 2025/2/23 14:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PVEStopVmApiResponse implements BaseApiResponse {

    private String data;

}
