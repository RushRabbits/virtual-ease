package com.awake.ve.common.ecs.enums.vm;

import lombok.Getter;

/**
 * BIOS枚举
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:13
 */
@Getter
public enum BiosType {
    SEA_BIOS("seabios", "seabios"),
    OVMF("ovmf", "ovmf"),
    ;

    private final String type;
    private final String description;

    BiosType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
