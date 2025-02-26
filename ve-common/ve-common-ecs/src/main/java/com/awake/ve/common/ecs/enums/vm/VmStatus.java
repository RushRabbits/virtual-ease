package com.awake.ve.common.ecs.enums.vm;

import lombok.Getter;

@Getter
public enum VmStatus {
    STOPPED("stopped", "停止"),
    RUNNING("running", "运行中");

    private final String status;
    private final String description;

    VmStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public static VmStatus getVmStatus(String status) {
        for (VmStatus vmStatus : VmStatus.values()) {
            if (vmStatus.getStatus().equals(status)) {
                return vmStatus;
            }
        }
        return null;
    }
}
