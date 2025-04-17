package com.awake.ve.virtual.domain.vo;

import com.awake.ve.common.excel.annotation.ExcelDictFormat;
import com.awake.ve.common.excel.convert.ExcelDictConvert;
import com.awake.ve.virtual.domain.VeVmInfo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 虚拟机信息视图对象 ve_vm_info
 *
 * @author 突突兔
 * @date 2025-02-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = VeVmInfo.class)
public class VeVmInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 虚拟机id
     */
    @ExcelProperty(value = "虚拟机id")
    private Long vmId;

    /**
     * 虚拟机名
     */
    @ExcelProperty(value = "虚拟机名")
    private String name;

    /**
     * 虚拟机内存
     */
    @ExcelProperty(value = "虚拟机内存")
    private Long memory;

    /**
     * 虚拟机启动顺序
     */
    @ExcelProperty(value = "虚拟机启动顺序")
    private String boot;

    /**
     * cloud-init用户信息
     */
    @ExcelProperty(value = "cloud-init用户信息")
    private String ciUser;

    /**
     * cloud-init用户密码
     */
    @ExcelProperty(value = "cloud-init用户密码")
    private String ciPassword;

    /**
     * 首次启动后是否自动升级包
     */
    @ExcelProperty(value = "首次启动后是否自动升级包")
    private Boolean ciUpgrade;

    /**
     * cpu架构
     */
    @ExcelProperty(value = "cpu架构", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ve_cpu_arch_type")
    private String cpu;

    /**
     * cpu插槽数
     */
    @ExcelProperty(value = "cpu插槽数")
    private Long sockets;

    /**
     * cpu每个插槽的核心数
     */
    @ExcelProperty(value = "cpu每个插槽的核心数")
    private Long cores;

    /**
     * vga
     */
    @ExcelProperty(value = "vga", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ve_vga_type")
    private String vga;

    /**
     * 是否开启qemu代理
     */
    @ExcelProperty(value = "是否开启qemu代理")
    private String agent;

    /**
     * 操作系统
     */
    @ExcelProperty(value = "操作系统", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ve_os_type")
    private String osType;

    /**
     * SCSI控制器硬件类型
     */
    @ExcelProperty(value = "SCSI控制器硬件类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ve_scsi_hw_type")
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
    @ExcelProperty(value = "bios", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "ve_bios_type")
    private String bios;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
