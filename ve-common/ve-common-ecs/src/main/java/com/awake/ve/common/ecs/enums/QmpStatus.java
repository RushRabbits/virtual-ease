package com.awake.ve.common.ecs.enums;

import lombok.Getter;

@Getter
public enum QmpStatus {
    RUNNING("running", "运行中"),
    PAUSED("paused", "暂停"),
    STOPPED("stopped", "停止"),
    ;

    private final String status;
    private final String description;

    QmpStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
