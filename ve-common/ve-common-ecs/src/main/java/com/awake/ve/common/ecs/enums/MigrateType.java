package com.awake.ve.common.ecs.enums;

import lombok.Getter;

@Getter
public enum MigrateType {
    SECURE(
            "secure",
            "默认使用SSH隑道加密迁移流量,在完全私有网络中可以禁用加密以提高性能"
            ),
    INSECURE(
            "insecure",
            "默认使用SSH隑道加密迁移流量,在完全私有网络中可以禁用加密以提高性能"
    )
    ;

    private final String type;
    private final String description;

    MigrateType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
