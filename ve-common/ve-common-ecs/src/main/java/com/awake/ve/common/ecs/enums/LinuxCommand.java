package com.awake.ve.common.ecs.enums;

import lombok.Getter;

@Getter
public enum LinuxCommand {
    RM_LOCK_CONF(
            "rm /var/lock/qemu-server/lock-{vmid}.conf",
            "移除虚拟机锁定的配置文件"
    )
    ;

    private final String command;
    private final String description;

    LinuxCommand(String command, String description) {
        this.command = command;
        this.description = description;
    }
}
