package com.awake.ve.virtual.domain.bo;

import com.awake.ve.common.ecs.domain.vm.param.*;
import lombok.Data;

import java.util.List;

/**
 * 创建虚拟机入参
 *
 * @author wangjiaxing
 * @date 2025/3/20 9:39
 */
@Data
public class VeCreateOrEditVmBo {

    /**
     * 节点名
     */
    private String node;

    /**
     * 虚拟机id
     */
    private Long vmId;

    /**
     * 内存大小
     */
    private Double memory;

    /**
     * cloud-init用户名
     */
    private String ciUser;

    /**
     * cloud-init密码
     */
    private String ciPassword;

    /**
     * 首次启动后是否自动升级包
     */
    private Boolean ciUpgrade;

    /**
     * cpu插槽数
     */
    private Integer sockets;

    /**
     * cpu核数
     */
    private Integer cores;

    /**
     * vga配置
     */
    private String vga;

    /**
     * qemu的代理信息
     */
    private Agent agent;

    /**
     * cpu类型
     */
    private String cpu;

    /**
     * 操作系统类型
     */
    private String osType;

    /**
     * scsihw参数设置SCSI控制器硬件类型
     */
    private String scsiHw;

    /**
     * ip配置
     */
    private List<IpConfig> ipConfig;

    /**
     * 启动顺序
     */
    private BootOrder boot;

    /**
     * 网络配置
     */
    private List<Net> net;

    /**
     * 虚拟机的SCSI硬盘或CD-ROM设备
     */
    private List<Scsi> scsi;

    /**
     * ide配置 配置IDE硬盘或CD-ROM设备
     */
    private List<Ide> ide;

    /**
     * bios配置
     */
    private String bios;
}
