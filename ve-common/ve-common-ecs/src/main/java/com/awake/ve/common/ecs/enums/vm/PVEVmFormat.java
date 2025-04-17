package com.awake.ve.common.ecs.enums.vm;

import lombok.Getter;

@Getter
public enum PVEVmFormat {
    QCOW2("qcow2", "qcow2"),
    RAW("raw", "raw"),
    VMDK("vmdk", "vmdk");

    private final String format;
    private final String description;

    PVEVmFormat(String format, String description) {
        this.format = format;
        this.description = description;
    }
}
