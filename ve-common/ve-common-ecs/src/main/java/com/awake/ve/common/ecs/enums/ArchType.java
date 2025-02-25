package com.awake.ve.common.ecs.enums;

import lombok.Getter;

/**
 * cpu架构枚举
 * <p>
 * // TODO 类型还有很多,不一一列举,注意api文档提供的类型是错误的,需要从web端找.而且此值可以不设置,会读宿主机的cpu架构
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:13
 */
@Getter
public enum ArchType {
    X86_64_V2("x86-64-v2", "x86-64-v2"),
    X86_64_V2_AES("x86-64-v2-AES", "x86-64-v2-AES"),
    X86_64_V3("x86-64-v3", "x86-64-v3"),
    X86_64_V4("x86-64-v4", "x86-64-v4"),
    HOST("host", "host"),
    ;

    private final String type;
    private final String description;

    ArchType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
