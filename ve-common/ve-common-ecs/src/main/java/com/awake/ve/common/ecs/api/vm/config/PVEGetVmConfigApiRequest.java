package com.awake.ve.common.ecs.api.vm.config;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

/**
 * pve api 获取虚拟机配置 请求参数
 *
 * @author wangjiaxing
 * @date 2025/2/26 9:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PVEGetVmConfigApiRequest extends PVEBaseApiRequest {

    /**
     * 必填
     * 节点
     */
    private String node;

    /**
     * 必填
     * 虚拟机id
     */
    private Long vmId;

    // 以下为选填
    /**
     * 是否获取当前配置
     */
    private Boolean current;
}
