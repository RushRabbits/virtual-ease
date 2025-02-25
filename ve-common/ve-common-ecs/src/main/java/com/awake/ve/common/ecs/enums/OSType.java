package com.awake.ve.common.ecs.enums;

import lombok.Getter;

/**
 * 操作系统类型枚举
 *
 * @author wangjiaxing
 * @date 2025/2/25 12:15
 */
@Getter
public enum OSType {
    /**
     * Specify guest operating system. This is used to enable special
     * optimization/features for specific operating systems:
     * <p>
     * [horizontal]
     * other;; unspecified OS
     * wxp;; Microsoft Windows XP
     * w2k;; Microsoft Windows 2000
     * w2k3;; Microsoft Windows 2003
     * w2k8;; Microsoft Windows 2008
     * wvista;; Microsoft Windows Vista
     * win7;; Microsoft Windows 7
     * win8;; Microsoft Windows 8/2012/2012r2
     * win10;; Microsoft Windows 10/2016/2019
     * win11;; Microsoft Windows 11/2022/2025
     * l24;; Linux 2.4 Kernel
     * l26;; Linux 2.6 - 6.X Kernel
     * solaris;; Solaris/OpenSolaris/OpenIndiania kernel
     */
    OTHER("other", "其他"),
    WXP("wxp", "Windows XP"),
    W2K("w2k", "Windows 2000"),
    W2K3("w2k3", "Windows Server 2003"),
    W2K8("w2k8", "Windows Server 2008"),
    WVISTA("wvista", "Windows Vista"),
    WIN7("win7", "Windows 7"),
    WIN8("win8", "Windows 8"),
    WIN10("win10", "Windows 10"),
    WIN11("win11", "Windows 11"),
    L24("l24", "Linux 2.4"),
    L26("l26", "Linux 2.6+"),
    SOLARIS("solaris", "Solaris");

    private final String type;
    private final String description;

    OSType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}