package com.awake.ve.virtual.domain;

import com.awake.ve.common.ecs.domain.vm.PveVmInfo;
import com.awake.ve.common.ecs.enums.vm.VmStatus;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 查询虚拟机列表的vo
 *
 * @author wangjiaxing
 * @date 2025/3/18 11:14
 */
@AutoMapper(target = PveVmInfo.class)
@Data
public class VeVmListVo {
    /**
     * 一定会返回
     * 虚拟机状态(只有stopped和running)
     * 枚举{@link VmStatus}
     */
    private String status;

    /**
     * 一定会返回
     * 虚拟机id
     */
    private Long vmId;

    // 以下字段未必会返回
    /**
     * 虚拟机名
     */
    private String name;

    /**
     * lock 当前配置锁（如果有）
     */
    private String lock;

    /**
     * 最大可用cpu数量
     */
    private Integer cpus;

    /**
     * 根磁盘大小（字节）
     */
    private Double maxDisk;

    /**
     * 最大可用内存（字节）
     */
    private Double maxMem;

    /**
     * IO统计
     * 磁盘读取字节数
     */
    private Double diskRead;

    /**
     * IO统计
     * 磁盘写入字节数
     */
    private Double diskWrite;

    /**
     * IO统计
     * 网络输入流量
     */
    private Double netIn;

    /**
     * IO统计
     * 网络输出流量
     */
    private Double netOut;

    /**
     * QEMU进程ID（虚拟机运行时）
     */
    private Long pid;

    /**
     * QMP监控命令状态
     */
    private String qmpStatus;

    /**
     * 当前运行的机器类型
     */
    private String runningMachine;

    /**
     * 当前运行的QEMU版本
     */
    private String runningQemu;

    /**
     * 已配置的标签
     */
    private String tags;

    /**
     * 是否为模板
     */
    private Boolean template;

    /**
     * 虚拟机运行时间 up time
     * 注意并不是更新时间 updateTime
     */
    private Integer uptime;
}
