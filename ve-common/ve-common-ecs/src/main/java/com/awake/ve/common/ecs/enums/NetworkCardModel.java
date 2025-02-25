package com.awake.ve.common.ecs.enums;

import lombok.Getter;


/**
 * 枚举类表示Proxmox虚拟机支持的网卡模型类型
 *
 * @author wangjiaxing
 * @date 2025/2/25 19:36
 */
@Getter
public enum NetworkCardModel {
    /**
     * 半虚拟化网卡，提供最佳性能，需要客户机操作系统支持VirtIO驱动
     */
    VIRTIO("virtio", "半虚拟化网卡，提供最佳性能，适用于现代Linux和安装了VirtIO驱动的Windows"),

    /**
     * 模拟Intel E1000网卡，兼容性好，性能适中
     */
    E1000("e1000", "模拟Intel E1000网卡，兼容大多数操作系统，性能适中"),

    /**
     * 模拟Intel E1000-E网卡，E1000的增强版本
     */
    E1000E("e1000-82545em", "模拟Intel E1000-E网卡，E1000的增强版本"),

    /**
     * 模拟Realtek RTL8139网卡，广泛兼容老旧系统
     */
    RTL8139("rtl8139", "模拟Realtek RTL8139网卡，适用于老旧操作系统，性能较低"),

    /**
     * VMware VMXNET3网卡，针对VMware平台优化
     */
    VMXNET3("vmxnet3", "VMware优化网卡，需要VMware Tools或特定驱动支持"),

    /**
     * 模拟Ne2000 PCI网卡，用于非常老旧的系统
     */
    NE2K_PCI("ne2k_pci", "模拟Ne2000 PCI网卡，用于兼容非常老旧的系统"),

    /**
     * 模拟Intel I82551网卡
     */
    I82551("i82551", "模拟Intel i82551网卡"),

    /**
     * 模拟Intel I82557B网卡
     */
    I82557B("i82557b", "模拟Intel i82557B网卡"),

    /**
     * 模拟Intel I82559ER网卡
     */
    I82559ER("i82559er", "模拟Intel i82559ER网卡"),

    /**
     * VirtIO非标准单队列变体
     */
    VIRTIO_NET_PCI("virtio-net-pci", "VirtIO网卡的PCI变体");

    private final String type;
    private final String description;

    NetworkCardModel(String type, String description) {
        this.type = type;
        this.description = description;
    }

    /**
     * 获取枚举值对应的API参数值
     *
     * @return 网卡模型的字符串表示
     */
    public String getType() {
        return type;
    }

    /**
     * 获取网卡模型的详细描述
     *
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 从字符串转换为枚举值
     *
     * @param type 网卡模型的字符串表示
     * @return 对应的枚举值，如果不存在则返回null
     */
    public static NetworkCardModel fromValue(String type) {
        for (NetworkCardModel model : NetworkCardModel.values()) {
            if (model.type.equals(type)) {
                return model;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type;
    }
}