package com.awake.ve.common.ecs.enums;

import lombok.Getter;

 /**
  * 枚举类表示Proxmox虚拟机支持的VGA/显示控制器类型
  *  // TODO 由于官方文档未提供具体的类型,所以本枚举不能保证所有都正确
   * @author wangjiaxing
   * @date 2025/2/25 18:39
   */
@Getter
public enum VgaType {
    /**
     * 标准VGA兼容性显卡，默认支持所有客户机操作系统
     */
    STD("std", "标准VGA显卡，提供基础显示功能，与所有操作系统兼容"),
    
    /**
     * VMware兼容显卡，提供更好的图形性能
     */
    VMWARE("vmware", "VMware兼容显卡，提供增强的图形功能和分辨率"),
    
    /**
     * QXL半虚拟化显卡，SPICE远程桌面的推荐选择
     */
    QXL("qxl", "QXL显卡，为SPICE远程桌面优化，提供高性能2D/3D加速和视频流"),
    
    /**
     * 旧版Cirrus Logic GD5446显卡，提供广泛兼容性
     */
    CIRRUS("cirrus", "Cirrus Logic GD5446兼容显卡，适用于老旧操作系统"),
    
    /**
     * 无图形控制器，仅用于控制台访问
     */
    NONE("none", "无图形设备，仅适用于纯控制台访问的服务器"),
    
    /**
     * 虚拟GPU，可用于穿透物理显卡
     */
    VIRTIO("virtio", "VirtIO GPU，更低的CPU消耗，需要客户机操作系统支持VirtIO驱动");
    
    private final String type;
    private final String description;
    
    VgaType(String type, String description) {
        this.type = type;
        this.description = description;
    }
    
    /**
     * 获取枚举值对应的API参数值
     * 
     * @return VGA类型的字符串表示
     */
    public String getType() {
        return type;
    }
    
    /**
     * 获取VGA类型的详细描述
     * 
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * 从字符串转换为枚举值
     * 
     * @param type VGA类型的字符串表示
     * @return 对应的枚举值，如果不存在则返回null
     */
    public static VgaType fromValue(String type) {
        for (VgaType val : VgaType.values()) {
            if (val.type.equals(type)) {
                return val;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return type;
    }
}