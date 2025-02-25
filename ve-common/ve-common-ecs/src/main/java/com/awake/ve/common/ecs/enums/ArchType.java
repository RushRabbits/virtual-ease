package com.awake.ve.common.ecs.enums;

import lombok.Getter;

/**
 * cpu架构枚举
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:13
 */
@Getter
public enum ArchType {
    X86_64("x86_64", "x86_64"),
    AARCH_64("aarch64", "aarch64"),
    ;

    private final String type;
    private final String description;

    ArchType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
