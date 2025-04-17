package com.awake.ve.common.ecs.enums.cloudinit;

import lombok.Getter;

 /**
  * cloud-init 枚举
   * @author wangjiaxing
   * @date 2025/2/25 12:13
   */
@Getter
public enum CloudInitType {
    CONFIG_DRIVE_2("configdrive2", "configdrive2"),
    NO_CLOUD("nocloud", "nocloud"),
    OPENNEBULA("opennebula", "opennebula");

    private final String type;
    private final String description;

    CloudInitType(String type, String description) {
        this.type = type;
        this.description = description;
    }

}
