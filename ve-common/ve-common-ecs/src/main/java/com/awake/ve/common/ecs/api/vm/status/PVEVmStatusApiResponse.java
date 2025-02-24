package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.response.BaseApiResponse;
import com.awake.ve.common.ecs.domain.PveHaObject;
import com.awake.ve.common.ecs.enums.VmStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVEVmStatusApiResponse implements BaseApiResponse {
    // TODO 由于考虑时间,还有一些返回值比较复杂,暂时没有对接

    /**
     * 一定会返回
     * 高可用HA信息
     */
    private PveHaObject ha;

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
     * qemu代理是否开启
     */
    private Boolean agent;

    /**
     * lock 当前配置锁（如果有）
     */
    private String lock;

    /**
     * 剪切板设置
     * Enable a specific clipboard. If not set, depending on the display type the SPICE one will be added.
     */
    private String clipboard;

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
     * 是否支持SPICE VGA配置
     */
    private Boolean spice;

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
