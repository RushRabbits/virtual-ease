package com.awake.ve.common.ecs.api.vnc;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVEVncProxyApiRequest extends PVEBaseApiRequest {

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

    // 以下内容选填

    /**
     * 是否生成随机密码
     */
    private Boolean generatePassword;

    /**
     * 为websocket升级做准备（仅在使用串行终端时需要，否则升级始终可行）
     */
    private Boolean websocket;
}
