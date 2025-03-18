package com.awake.ve.virtual.domain.vo;

import com.awake.ve.common.ecs.api.vm.config.PVEGetVmConfigApiResponse;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;

@Data
@AutoMapper(target = PVEGetVmConfigApiResponse.class)
public class VeVmConfigVo {

    /**
     * 虚拟机名
     */
    private String name;

    /**
     * 内存大小
     */
    private Double memory;

    /**
     * 是否开启qemu代理
     */
    private Boolean agent;

    /**
     * vmGenId
     */
    private String vmGenId;

    /**
     * osType
     */
    private String osType;

    /**
     * cpu架构
     */
    private String cpu;

    /**
     * scsi处理器类型
     */
    private String scsiHw;

    /**
     * 启动顺序
     */
    private String boot;

    /**
     * 自定义虚拟机SMBIOS信息，影响VM内看到的"硬件"信息
     */
    private String smBios1;

    /**
     * cpu插槽数
     */
    private Integer sockets;

    /**
     * cpu插槽的核数
     */
    private Integer cores;

    /**
     * 虚拟机元数据
     */
    private String meta;

    /**
     * 虚拟机vga配置
     */
    private String vga;

    /**
     * 配置文件的SHA1摘要
     */
    private String digest;

    /**
     * NUMA拓扑配置
     */
    private Integer numa;

    /**
     * cloud-init user信息
     */
    private String ciUser;

    /**
     * cloud-init password信息
     */
    private String ciPassword;

    /**
     * ide磁盘配置
     */
    private List<String> ide;

    /**
     * ipconfig配置集合
     */
    private List<String> ipConfig;

    /**
     * 网络配置集合
     */
    private List<String> net;

    /**
     * scsi磁盘配置集合
     */
    private List<String> scsi;
}
