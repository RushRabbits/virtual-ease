package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * pve api 启动虚拟机的响应
 *
 * @author wangjiaxing
 * @date 2025/2/23 9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PVEStartVmApiResponse implements BaseApiResponse {
    
    private String data;

}
