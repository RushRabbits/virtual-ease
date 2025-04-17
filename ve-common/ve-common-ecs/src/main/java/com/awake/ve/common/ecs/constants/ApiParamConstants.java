package com.awake.ve.common.ecs.constants;


/**
 * @author wangjiaxing
 * @date 2025/2/22 13:06
 */
public interface ApiParamConstants {
    /**
     * ？
     */
    String QUESTION_MARK = "?";
    /**
     * 等号
     */
    String EQUAL_MARK = "=";
    /**
     * and
     */
    String AND = "&";
    /**
     * 节点
     */
    String NODE = "node";
    /**
     * 服务器主机
     */
    String HOST = "host";
    /**
     * 服务端口
     */
    String PORT = "port";
    /**
     * api用户名
     */
    String API_USERNAME = "username";
    /**
     * api用户密码
     */
    String API_PASSWORD = "password";
    /**
     * 虚拟机id
     */
    String VM_ID = "vmid";
    /**
     * pve token请求头
     */
    String CSRF_PREVENTION_TOKEN = "CSRFPreventionToken";
    /**
     * Cookie
     */
    String COOKIE = "Cookie";
    /**
     * pve cookie
     */
    String PVE_AUTH_COOKIE = "PVEAuthCookie=";
    /**
     * 新的id
     */
    String NEW_ID = "newid";
    /**
     * 名称
     */
    String NAME = "name";
    /**
     * content-type
     */
    String CONTENT_TYPE = "Content-Type";
    /**
     * json请求类型
     */
    String APPLICATION_JSON = "application/json";
    /**
     * IO带宽限制
     */
    String BW_LIMIT = "bwlimit";
    /**
     * 描述
     */
    String DESCRIPTION = "description";
    /**
     * 格式
     */
    String FORMAT = "format";
    /**
     * 是否完整克隆
     */
    String FULL = "full";
    /**
     * 存储池
     */
    String POOL = "pool";
    /**
     * 快照名称
     */
    String SNAPNAME = "snapname";
    /**
     * 存储
     */
    String STORAGE = "storage";
    /**
     * 目标
     */
    String TARGET = "target";
    /**
     * 强制覆盖cpu
     */
    String FORCE_CPU = "force_cpu";
    /**
     * 虚拟机类型
     */
    String MACHINE = "machine";
    /**
     * 原节点
     */
    String MIGRATED_FROM = "migratedfrom";
    /**
     * 迁移使用的网络的CIDR
     */
    String MIGRATION_NETWORK = "migration_network";
    /**
     * 迁移类型
     */
    String MIGRATION_TYPE = "migration_type";
    /**
     * 忽略锁定
     */
    String SKIP_LOCK = "skiplock";
    /**
     * 状态uri
     */
    String STATE_URI = "stateuri";
    /**
     * 目标存储
     */
    String TARGET_STORAGE = "targetstorage";
    /**
     * 超时
     */
    String TIMEOUT = "timeout";
    /**
     * 强制停止
     */
    String FORCE_STOP = "forceStop";
    /**
     * 保持连接
     */
    String KEEP_ALIVE = "keepAlive";
    /**
     * 覆盖之前的未完成的shutdown
     */
    String OVERRULE_SHUTDOWN = "overruleShutdown";
    /**
     * 销毁未引用的磁盘
     */
    String DESTROY_UNREFERENCED_DISKS = "destroyUnreferencedDisks";
    /**
     * 完全清理与该VM相关的所有配置
     */
    String PURGE = "purge";
    /**
     * 虚拟机的状态存储的位置
     */
    String STATE_STORAGE = "statestorage";
    /**
     * 是否将虚拟机保存到磁盘
     */
    String TO_DISK = "todisk";
    /**
     * 跳过状态检查
     */
    String NO_CHECK = "nocheck";

    /**
     * acpi 启用/禁用ACPI电源管理
     */
    String ACPI = "acpi";

    /**
     * affinity CPU核心亲和性设置
     */
    String AFFINITY = "affinity";

    /**
     * agent 启用/禁用QEMU Guest Agent
     */
    String AGENT = "agent";

    /**
     * AMD CPU的安全加密虚拟化功能
     */
    String AMD_SEV = "amd-sev";

    /**
     * 处理器架构
     */
    String ARCH = "arch";

    /**
     * 备份存档路径
     */
    String ARCHIVE = "archive";

    /**
     * 传递给KVM的自定义参数
     */
    String ARGS = "args";

    /**
     * 音频设备配置
     */
    String AUDIO0 = "audio0";

    /**
     * 自动启动
     */
    String AUTO_START = "autostart";

    /**
     * VM的目标内存大小(MiB)
     */
    String BALLOON = "balloon";

    /**
     * BIOS Basic Input/Output System 类型
     */
    String BIOS = "bios";

    /**
     * 指定启动顺序
     */
    String BOOT = "boot";

    /**
     * 引导磁盘
     */
    String BOOK_DISK = "bootdisk";

    /**
     * 引导设备
     */
    String CD_ROM = "cdrom";

    /**
     * 自定义CLOUD-INIT文件
     */
    String CI_CUSTOM = "cicustom";

    /**
     * cloud-init用户密码
     */
    String CI_PASSWORD = "cipassword";

    /**
     * cloud-init类型
     */
    String CI_TYPE = "citype";

    /**
     * 首次启动后是否自动升级包
     */
    String CI_UPGRADE = "ciupgrade";

    /**
     * 首次启动后是否自动升级包
     */
    String CI_USER = "ciuser";

    /**
     * 每个CPU插槽的核心数
     */
    String CORES = "cores";

    /**
     * 模拟的CPU类型
     */
    String CPU = "cpu";

    /**
     * 限制CPU使用率
     */
    String CPU_LIMIT = "cpulimit";

    /**
     * VM的CPU权重
     */
    String CPU_UNITS = "cpuunits";

    /**
     * 配置存储EFI变量的磁盘
     */
    String EFI_DISK_0 = "efidisk0";

    /**
     * 允许覆盖已存在的VM
     */
    String FORCE = "force";

    /**
     * 在启动时冻结CPU
     */
    String FREEZE = "freeze";

    /**
     * 钩子脚本
     */
    String HOOK_SCRIPT = "hookscript";

    /**
     * 主机的PCI设备直接映射到虚拟机，实现直通功能
     */
    String HOSTPCI = "hostpci";

    /**
     * 热插拔
     */
    String HOT_PLUG = "hotplug";

    /**
     * 启用/禁用大页内存
     */
    String HUGE_PAGES = "hugepages";

    /**
     * 配置IDE硬盘或CD-ROM设备，支持0-3个设备(n从0到3)
     */
    String IDE = "ide";

    /**
     * 导入过程中用作中间提取存储的基于文件的存储
     */
    String IMPORT_WORKING_STORAGE = "import-working-storage";

    /**
     * IP配置
     */
    String IP_CONFIG = "ipconfig";

    /**
     * VM间共享内存配置
     */
    String IVSH_MEM = "ivshmem";

    /**
     * 与hugepages配合使用，启用后VM关闭时不会删除大页内存，可用于后续启动
     */
    String KEEP_HUGE_PAGES = "keephugepages";

    /**
     * VNC服务器的键盘布局
     */
    String KEY_BOARD = "keyboard";

    /**
     * 启用/禁用KVM硬件虚拟化
     */
    String KVM = "kvm";

    /**
     * 在后台导入或恢复时立即启动VM
     */
    String LIVE_RESTORE = "live-restore";

    /**
     * 将RTC设置为本地时间
     */
    String LOCAL_TIME = "localtime";

    /**
     * VM的锁定/解锁状态
     */
    String LOCK = "lock";

    /**
     * 内存属性设置
     */
    String MEMORY = "memory";

    /**
     * 迁移最大允许停机时间
     */
    String MIGRATE_DOWNTIME = "migrate_downtime";

    /**
     * 设置迁移最大速度
     */
    String MIGRATE_SPEED = "migrate_speed";

    /**
     * cloud-init的DNS服务器设置
     */
    String NAME_SERVER = "nameserver";

    /**
     * 网络设备配置
     */
    String NET = "net";

    /**
     * NUMA拓扑配置
     */
    String NUMA = "numa";

    /**
     * 系统启动时是否自动启动VM
     */
    String ON_BOOT = "onboot";

    /**
     * 操作系统类型
     */
    String OS_TYPE = "ostype";

    /**
     * 映射主机并口设备
     */
    String PARALLEL = "parallel";

    /**
     * 设置VM的保护标志
     */
    String PROTECTION = "protection";

    /**
     * 重启
     */
    String REBOOT = "reboot";

    /**
     * 配置基于VirtIO的随机数生成器
     */
    String RNG0 = "rng0";

    /**
     * 配置虚拟机的SATA硬盘或CD-ROM设备
     */
    String SATA = "sata";

    /**
     * 用于配置虚拟机的SCSI硬盘或CD-ROM设备
     */
    String SCSI = "scsi";

    /**
     * scsihw参数设置SCSI控制器硬件类型，这会影响所有SCSI设备的性能和兼容性
     */
    String SCSI_HW = "scsihw";

    /**
     * 为容器设置DNS搜索域
     */
    String SEARCH_DOMAIN = "searchdomain";

    /**
     * 配置虚拟机的串行端口
     */
    String SERIAL = "serial";

    /**
     * 控制虚拟机的内存自动膨胀（auto-ballooning）优先级
     */
    String SHARES = "shares";

    /**
     * 自定义虚拟机SMBIOS信息，影响VM内看到的"硬件"信息
     */
    String SM_BIOS1 = "smbios1";

    /**
     * 已弃用的总CPU数设置
     */
    String SMP = "smp";

    /**
     * 推荐使用的CPU插槽数设置
     */
    String SOCKETS = "sockets";

    /**
     * 增强SPICE远程桌面协议的用户体验
     */
    String SPICE_ENHANCEMENTS = "spice_enhancements";

    /**
     * 用于cloud-init，在首次启动时设置VM的SSH公钥
     */
    String SSH_KEYS = "sshkeys";

    /**
     * 控制VM创建后是否自动启动
     */
    String START = "start";

    /**
     * VM启动时间
     */
    String START_DATE = "startdate";

    /**
     * 定义PVE集群启动/关闭时VM的行为
     */
    String START_UP = "startup";

    /**
     * 控制USB平板设备的启用状态
     */
    String TABLET = "tablet";

    /**
     * VM添加元数据标签，便于分类和搜索
     */
    String TAGS = "tags";

    /**
     * 启用/禁用时间漂移修复功能
     */
    String TDF = "tdf";

    /**
     * 模板
     */
    String TEMPLATE = "template";

    /**
     * 配置存储TPM (可信平台模块) 状态的磁盘
     */
    String TPM_STATE0 = "tpmstate0";

    /**
     * 分配唯一的随机以太网MAC地址
     */
    String UNIQUE = "unique";

    /**
     * 引用未使用的卷，主要用于内部使用
     */
    String UNUSED = "unused";

    /**
     * 配置USB设备，支持多达15个设备
     */
    String USB = "usb";

    /**
     * 设置热插拔的vCPU数量
     */
    String V_CPUS = "vcpus";

    /**
     * 配置虚拟机VGA设备
     */
    String VGA = "vga";

    /**
     * 配置VirtIO硬盘，支持0-15个设备
     */
    String VIRTIO = "virtio";

    /**
     * VM世代ID设备，向客户操作系统公开128位整数标识符
     */
    String VM_GEN_ID = "vmgenid";

    /**
     * 指定VM状态卷/文件的默认存储位置
     */
    String VM_STATE_STORAGE = "vmstatestorage";

    /**
     * 创建虚拟硬件看门狗设备
     */
    String WATCH_DOG = "watchdog";

    /**
     * dhcp
     */
    String DHCP = "dhcp";

    /**
     * IP
     */
    String IP = "ip";

    /**
     * ip6
     */
    String IP6 = "ip6";

    /**
     * 镜像路径
     */
    String IMAGE_PATH = "imagePath";

    /**
     * 桥接网络
     */
    String BRIDGE = "bridge";

    /**
     * 防火墙
     */
    String FIREWALL = "firewall";

    /**
     * 模型
     */
    String MODEL = "model";

    /**
     * 本地
     */
    String LOCAL = "local";

    /**
     * enabled
     */
    String ENABLED = "enabled";

    /**
     * 类型
     */
    String TYPE = "type";

    /**
     * meta
     */
    String META = "meta";

    /**
     * digest
     */
    String DIGEST = "digest";

    /**
     * 网络名
     */
    String IFACE = "iface";

    /**
     * 地址
     */
    String ADDRESS = "address";

    /**
     * ip6地址
     */
    String ADDRESS6 = "address6";

    /**
     * cidr
     */
    String CIDR = "cidr";

    /**
     * cidr6
     */
    String CIDR6 = "cidr6";

    /**
     * 注释
     */
    String COMMENTS = "comments";

    /**
     * ip6网络接口注释
     */
    String COMMENTS6 = "comments6";

    /**
     * ipv4网络掩码
     */
    String NETMASK = "netmask";

    /**
     * ipv6网络掩码
     */
    String NETMASK6 = "netmask6";

    /**
     * 最大存储单元
     */
    String MTU = "mtu";

    /**
     * 桥接端口
     */
    String BRIDGE_PORTS = "bridge_ports";

    /**
     * 允许的VLAN ID列表（如"2 4 100-200"）
     */
    String BRIDGE_VIDS = "bridge_vids";

    /**
     * 启用VLAN感知网桥
     */
    String BRIDGE_VLAN_AWARE = "bridge_vlan_aware";

    /**
     * VLAN ID
     */
    String VLAN_ID = "vlan-id";

    /**
     * VLAN所在的原始接口
     */
    String VLAN_RAW_DEVICE = "vlan-raw-device";

    /**
     * OVS绑定使用的接口
     */
    String OVS_BONDS = "ovs_bonds";

    /**
     * OVS端口关联的OVS网桥
     */
    String OVS_BRIDGE = "ovs_bridge";

    /**
     * OVS接口选项
     */
    String OVS_OPTIONS = "ovs_options";

    /**
     * OVS端口关联的OVS网桥
     */
    String OVS_PORTS = "ovs_ports";

    /**
     * VLAN标签
     */
    String OVS_TAGS = "ovs_tags";

    /**
     * 主接口（用于active-backup模式）
     */
    String BOND_PRIMARY = "bond_primary";

    /**
     * 绑定模式
     */
    String BOND_MODE = "bond_mode";

    /**
     * 传输散列策略
     */
    String BOND_XMIT_HASH_POLICY = "bond_xmit_hash_policy";

    /**
     * 从属接口
     */
    String SLAVES = "slaves";

    /**
     * 网关
     */
    String GATEWAY = "gateway";

    /**
     * ipv6网关
     */
    String GATEWAY6 = "gateway6";

    /**
     * 是否自动生成密码
     */
    String GENERATE_PASSWORD = "generate-password";

    /**
     * websocket
     */
    String WEBSOCKET = "websocket";

    /**
     * vncticket
     */
    String VNC_TICKET = "vncticket";

    /**
     * proxy
     */
    String PROXY = "proxy";

    /**
     * vlan tag
     */
    String VLAN_TAG = "tag";

}
