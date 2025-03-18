package com.awake.ve.virtual.domain;

import com.awake.ve.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 虚拟机信息对象 ve_vm_info
 *
 * @author 突突兔
 * @date 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ve_vm_info")
public class VeVmInfo extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 虚拟机id
     */
    @TableId(value = "vm_id")
    private Long vmId;

    /**
     * 虚拟机名
     */
    private String name;

    /**
     * 虚拟机内存
     */
    private Long memory;

    /**
     * 虚拟机启动顺序
     */
    private String boot;

    /**
     * cloud-init用户信息
     */
    private String ciUser;

    /**
     * cloud-init用户密码
     */
    private String ciPassword;

    /**
     * 首次启动后是否自动升级包
     */
    private Boolean ciUpgrade;

    /**
     * cpu架构
     */
    private String cpu;

    /**
     * cpu插槽数
     */
    private Long sockets;

    /**
     * cpu每个插槽的核心数
     */
    private Long cores;

    /**
     * vga
     */
    private String vga;

    /**
     * 是否开启qemu代理
     */
    private String agent;

    /**
     * 操作系统
     */
    private String osType;

    /**
     * SCSI控制器硬件类型
     */
    private String scsiHw;

    /**
     * ip配置
     */
    private String ipConfig;

    /**
     * 网络配置
     */
    private String net;

    /**
     * SCSI硬盘
     */
    private String scsi;

    /**
     * IDE硬盘
     */
    private String ide;

    /**
     * bios
     */
    private String bios;

    /**
     * 备注
     */
    private String remark;


}
