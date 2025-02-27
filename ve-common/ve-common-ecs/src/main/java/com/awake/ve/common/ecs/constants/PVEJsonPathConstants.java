package com.awake.ve.common.ecs.constants;

public interface PVEJsonPathConstants {
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
    /**
     * 网络名
     */
    String NETWORK_LIST_IFACE = "$.iface";
    /**
     * 网络类型
     */
    String NETWORK_LIST_TYPE = "$.type";
    /**
     * 网络是否存在
     */
    String NETWORK_LIST_EXISTS = "$.exists";
    /**
     * ip分配方法
     */
    String NETWORK_LIST_METHOD = "$.method";
    /**
     * ip6分配方法
     */
    String NETWORK_LIST_METHOD6 = "$.method6";
    /**
     * 网络是否自动启动
     */
    String NETWORK_LIST_AUTOSTART = "$.autostart";
    /**
     * 网络是否处于活跃状态
     */
    String NETWORK_LIST_ACTIVE = "$.active";
    /**
     * 网络优先级
     */
    String NETWORK_LIST_PRIORITY = "$.priority";
    /**
     * 支持的地址族
     */
    String NETWORK_LIST_FAMILIES = "$.families";
    /**
     * 桥接网络网关
     */
    String NETWORK_LIST_BRIDGE_GATEWAY = "$.gateway";
    /**
     * 桥接网络地址
     */
    String NETWORK_LIST_BRIDGE_ADDRESS = "$.address";
    /**
     * 桥接网络子网掩码
     */
    String NETWORK_LIST_BRIDGE_NETMASK = "$.netmask";
    /**
     * 桥接网络CIDR
     */
    String NETWORK_LIST_BRIDGE_CIDR = "$.cidr";
    /**
     * 桥接网络FD
     */
    String NETWORK_LIST_BRIDGE_FD = "$.bridge_fd";
    /**
     * 桥接网络端口
     */
    String NETWORK_LIST_BRIDGE_PORTS = "$.bridge_ports";
    /**
     * 桥接网络STP
     */
    String NETWORK_LIST_BRIDGE_STP = "$.bridge_stp";
    /**
     * VLAN网络所在物理网卡
     */
    String NETWORK_LIST_VLAN_RAW_DEVICE = "$.vlan_raw_device";

    /**
     * 网络名
     */
    String NETWORK_CONFIG_IFACE = "$.iface";
    /**
     * 网络类型
     */
    String NETWORK_CONFIG_TYPE = "$.type";
    /**
     * 网络是否存在
     */
    String NETWORK_CONFIG_EXISTS = "$.exists";
    /**
     * ip分配方法
     */
    String NETWORK_CONFIG_METHOD = "$.method";
    /**
     * ip6分配方法
     */
    String NETWORK_CONFIG_METHOD6 = "$.method6";
    /**
     * 网络是否自动启动
     */
    String NETWORK_CONFIG_AUTOSTART = "$.autostart";
    /**
     * 网络是否处于活跃状态
     */
    String NETWORK_CONFIG_ACTIVE = "$.active";
    /**
     * 网络优先级
     */
    String NETWORK_CONFIG_PRIORITY = "$.priority";
    /**
     * 支持的地址族
     */
    String NETWORK_CONFIG_FAMILIES = "$.families";
    /**
     * 桥接网络网关
     */
    String NETWORK_CONFIG_BRIDGE_GATEWAY = "$.gateway";
    /**
     * 桥接网络地址
     */
    String NETWORK_CONFIG_BRIDGE_ADDRESS = "$.address";
    /**
     * 桥接网络子网掩码
     */
    String NETWORK_CONFIG_BRIDGE_NETMASK = "$.netmask";
    /**
     * 桥接网络CIDR
     */
    String NETWORK_CONFIG_BRIDGE_CIDR = "$.cidr";
    /**
     * 桥接网络FD
     */
    String NETWORK_CONFIG_BRIDGE_FD = "$.bridge_fd";
    /**
     * 桥接网络端口
     */
    String NETWORK_CONFIG_BRIDGE_PORTS = "$.bridge_ports";
    /**
     * 桥接网络STP
     */
    String NETWORK_CONFIG_BRIDGE_STP = "$.bridge_stp";
    /**
     * VLAN网络所在物理网卡
     */
    String NETWORK_CONFIG_VLAN_RAW_DEVICE = "$.vlan_raw_device";

    /**
     * pve vnc proxy port
     */
    String PVE_VNC_PROXY_PORT = "$.port";
    /**
     * pve 任务id
     */
    String PVE_VNC_PROXY_UPID = "$.upid";
    /**
     * 用户名
     */
    String PVE_VNC_PROXY_USER = "$.user";
    /**
     * 密码
     */
    String PVE_VNC_PROXY_PASSWORD = "$.password";
    /**
     * vncticket
     */
    String PVE_VNC_PROXY_TICKET = "$.ticket";
    /**
     * cert
     */
    String PVE_VNC_PROXY_CERT = "$.cert";

    /**
     * pve vnc websocket port
     */
    String PVE_WEBSOCKET_PROXY_PORT = "$.port";

    /**
     * pve spice websocket secure attention
     */
    String PVE_VM_SPICE_SECURE_ATTENTION = "$.secure-attention";

    /**
     * pve spice websocket host
     */
    String PVE_VM_SPICE_HOST = "$.host";

    /**
     * pve spice websocket title
     */
    String PVE_VM_SPICE_TITLE = "$.title";
    /**
     * pve spice websocket host subject
     */
    String PVE_VM_SPICE_HOST_SUBJECT = "$.host-subject";
    /**
     * pve spice websocket toggle full screen
     */
    String PVE_VM_SPICE_TOGGLE_FULL_SCREEN = "$.toggle-fullscreen";
    /**
     * pve spice websocket proxy
     */
    String PVE_VM_SPICE_PROXY = "$.proxy";
    /**
     * pve spice websocket tls port
     */
    String PVE_VM_SPICE_TLS_PORT = "$.tls-port";
    /**
     * pve spice websocket ca
     */
    String PVE_VM_SPICE_CA = "$.ca";
    /**
     * pve spice websocket type
     */
    String PVE_VM_SPICE_TYPE = "$.type";
    /**
     * pve spice websocket delete this file
     */
    String PVE_VM_SPICE_DELETE_THIS_FILE = "$.delete-this-file";
    /**
     * pve spice websocket release cursor
     */
    String PVE_VM_SPICE_RELEASE_CURSOR = "$.release-cursor";
    /**
     * pve spice websocket password
     */
    String PVE_VM_SPICE_PASSWORD = "$.password";

}
