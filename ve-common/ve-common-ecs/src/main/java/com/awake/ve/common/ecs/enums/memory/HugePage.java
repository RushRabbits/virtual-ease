package com.awake.ve.common.ecs.enums.memory;

import lombok.Getter;

/**
 * pve api 创建虚拟机 hugepage参数枚举
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:14
 */
@Getter
public enum HugePage {
    ANY("any", "自动选择大小"),
    TWO("2", "2MB"),
    ONE_GB("1024", "1GB");

    private final String config;
    private final String description;

    HugePage(String config, String description) {
        this.config = config;
        this.description = description;
    }
}
