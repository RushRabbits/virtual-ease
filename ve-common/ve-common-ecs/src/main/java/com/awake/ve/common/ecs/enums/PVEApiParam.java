package com.awake.ve.common.ecs.enums;

import lombok.Getter;

/**
 * pve api 参数项
 *
 * @author wangjiaxing
 * @date 2025/2/25 18:15
 */
@Getter
public enum PVEApiParam {
    IP_CONFIG(
            "ip={ip},ip6={ip6}",
            "pve 创建虚拟机api ipconfig[n] 参数项"),
    SCSI(
            "local:0,import-from={imagePath},format=qcow2",
            "pve 创建虚拟机api scsi[n] 参数项," +
                    "如果为自己上传到宿主机的镜像,则写绝对路径," +
                    "如果是在pve存在的镜像,则参考 import-from=local:template/ubuntu-server.qcow2"
    ),
    NET(
            "model={model},bridge={bridge},firewall={firewall}",
            "pve 创建虚拟机api net[n] 参数项"
    ),
    IDE(
            "local:{local}",
            "pve 创建虚拟机api ide[n] 参数项"
    ),
    BOOT_ORDER(
            "order={boot}",
            "pve 创建虚拟机api boot 参数项 , 指定启动顺序"
    ),
    AGENT(
            "enabled={enabled},type={type}",
            "pve 创建虚拟机api boot 参数项 , 设置是否开启QEMU代理"
    );

    private final String param;
    private final String description;

    PVEApiParam(String param, String description) {
        this.param = param;
        this.description = description;
    }
}
