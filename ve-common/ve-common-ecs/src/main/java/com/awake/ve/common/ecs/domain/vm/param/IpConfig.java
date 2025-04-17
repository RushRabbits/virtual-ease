package com.awake.ve.common.ecs.domain.vm.param;

import lombok.Data;

/**
 * 创建虚拟机时的ipconfig配置
 *
 * @author wangjiaxing
 * @date 2025/3/18 15:23
 */
@Data
public class IpConfig {

    private String ip;

    private String ip6;
}
