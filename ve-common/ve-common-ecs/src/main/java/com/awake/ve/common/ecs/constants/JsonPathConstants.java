package com.awake.ve.common.ecs.constants;

public interface JsonPathConstants {
    /**
     * data
     */
    String PVE_BASE_RESP = "$.data";
    /**
     * CSRFPreventionToken
     */
    String PVE_CSRF_PREVENTION_TOKEN = "$.data.CSRFPreventionToken";
    /**
     * ticket
     */
    String PVE_TICKET = "$.data.ticket";
    /**
     * cpu数量
     */
    String VM_STATUS_CPUS = "$.data.cpus";
    /**
     * 磁盘写入
     */
    String VM_STATUS_DISK_WRITE = "$.data.diskwrite";
    /**
     * 网络输入
     */
    String VM_STATUS_NET_IN = "$.data.netin";
    /**
     * 网络输出
     */
    String VM_STATUS_NET_OUT = "$.data.netout";
    /**
     * 虚拟机类型
     */
    String VM_STATUS_RUNNING_MACHINE = "$.data.running-machine";
    /**
     * qemu版本
     */
    String VM_STATUS_RUNNING_QEMU = "$.data.running-qemu";
    /**
     * 运行时间
     */
    String VM_STATUS_UPTIME = "$.data.uptime";
    /**
     * 最大内存
     */
    String VM_STATUS_MAXMEM = "$.data.maxmem";
    /**
     * 最大磁盘
     */
    String VM_STATUS_MAX_DISK = "$.data.maxdisk";
    /**
     * QMP监控命令状态
     */
    String VM_STATUS_QMP_STATUS = "$.data.qmpstatus";
    /**
     * 虚拟机状态
     */
    String VM_STATUS_STATUS = "$.data.status";
    /**
     * 是否使用qemu代理
     */
    String VM_STATUS_QEMU_AGENT = "$.data.agent";
    /**
     * HA
     */
    String VM_STATUS_HA = "$.data.ha";
    /**
     * 是否使用HA
     */
    String VM_STATUS_HA_MANAGED = "$.data.ha.managed";
    /**
     * QEMU进程ID
     */
    String VM_STATUS_QEMU_PID = "$.data.pid";
    /**
     * 磁盘读取字节数
     */
    String VM_STATUS_DISK_READ = "$.data.diskread";
    /**
     * 剪切板设置
     */
    String VM_STATUS_CLIPBOARD = "$.data.clipboard";
    /**
     * 名字
     */
    String VM_STATUS_NAME = "$.data.name";
    /**
     * 是否使用SPICE
     */
    String VM_STATUS_SPICE = "$.data.spice";
    /**
     * 虚拟机ID
     */
    String VM_STATUS_VMID = "$.data.vmid";
    /**
     * 锁定状态
     */
    String VM_STATUS_LOCK = "$.data.lock";
    /**
     * 虚拟机配置信息
     */
    String VM_STATUS_TAGS = "$.data.tags";
    /**
     * 是否为模板
     */
    String VM_STATUS_TEMPLATE = "$.data.template";


    /**
     * cpu数量
     */
    String VM_LIST_CPUS = "$.cpus";
    /**
     * 磁盘写入
     */
    String VM_LIST_DISK_WRITE = "$.diskwrite";
    /**
     * 网络输入
     */
    String VM_LIST_NET_IN = "$.netin";
    /**
     * 网络输出
     */
    String VM_LIST_NET_OUT = "$.netout";
    /**
     * 虚拟机类型
     */
    String VM_LIST_RUNNING_MACHINE = "$.running-machine";
    /**
     * qemu版本
     */
    String VM_LIST_RUNNING_QEMU = "$.running-qemu";
    /**
     * 运行时间
     */
    String VM_LIST_UPTIME = "$.uptime";
    /**
     * 最大内存
     */
    String VM_LIST_MAXMEM = "$.maxmem";
    /**
     * 最大磁盘
     */
    String VM_LIST_MAX_DISK = "$.maxdisk";
    /**
     * QMP监控命令状态
     */
    String VM_LIST_QMP_STATUS = "$.qmpstatus";
    /**
     * 虚拟机状态
     */
    String VM_LIST_STATUS = "$.status";
    /**
     * 是否使用qemu代理
     */
    String VM_LIST_QEMU_AGENT = "$.agent";
    /**
     * HA
     */
    String VM_LIST_HA = "$.ha";
    /**
     * 是否使用HA
     */
    String VM_LIST_HA_MANAGED = "$.ha.managed";
    /**
     * QEMU进程ID
     */
    String VM_LIST_QEMU_PID = "$.pid";
    /**
     * 磁盘读取字节数
     */
    String VM_LIST_DISK_READ = "$.diskread";
    /**
     * 剪切板设置
     */
    String VM_LIST_CLIPBOARD = "$.clipboard";
    /**
     * 名字
     */
    String VM_LIST_NAME = "$.name";
    /**
     * 是否使用SPICE
     */
    String VM_LIST_SPICE = "$.spice";
    /**
     * 虚拟机ID
     */
    String VM_LIST_VMID = "$.vmid";
    /**
     * 锁定状态
     */
    String VM_LIST_LOCK = "$.lock";
    /**
     * 虚拟机配置信息
     */
    String VM_LIST_TAGS = "$.tags";
    /**
     * 是否为模板
     */
    String VM_LIST_TEMPLATE = "$.template";

}
