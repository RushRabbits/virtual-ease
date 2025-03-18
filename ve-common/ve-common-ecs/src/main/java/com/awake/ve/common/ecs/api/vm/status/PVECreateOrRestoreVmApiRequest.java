package com.awake.ve.common.ecs.api.vm.status;

import com.awake.ve.common.ecs.api.request.PVEBaseApiRequest;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PVECreateOrRestoreVmApiRequest extends PVEBaseApiRequest {

    /**
     * 必填
     * 节点
     */
    private String node;

    /**
     * 必填
     * 虚拟机id
     */
    private Long vmId;

    // 以下字段为选填
    /**
     * 默认值：true
     * 启用/禁用ACPI电源管理
     */
    private Boolean acpi;

    /**
     * CPU核心亲和性设置
     * 格式如：0,5,8-11
     * 限制VM使用特定的CPU核心
     */
    private String affinity;

    /**
     * QEMU Guest Agent设置
     * 控制与客户机操作系统的通信
     * 可设置备份时冻结文件系统等功能
     */
    private String agent;

    /**
     * AMD CPU的安全加密虚拟化功能
     * 设置内存加密等安全特性
     */
    private String amdSev;

    /**
     * 处理器架构
     * 可选：x86_64 | aarch64
     * 默认使用主机架构
     */
    private String arch;

    /**
     * 备份存档路径
     * 可以是.tar或.vma文件
     * 用于从备份恢复VM
     */
    private String archive;

    /**
     * 传递给KVM的自定义参数
     * 仅供专家使用
     */
    private String args;

    /**
     * 音频设备配置
     * 常用于QXL/Spice远程桌面
     * 不同于显卡设置的spice,是两回事
     */
    private String audio0;

    /**
     * 默认值：false
     * 系统崩溃后是否自动重启
     */
    private Boolean autoStart;

    /**
     * VM的目标内存大小(MiB)
     * 设置为0禁用内存气球驱动
     */
    private Integer balloon;

    /**
     * Basic Input/Output System
     * 格式：seabios | ovmf
     * 默认值：seabios
     * 作用：选择BIOS实现方式
     * seabios：传统BIOS
     * ovmf：UEFI固件
     */
    private String bios;

    /**
     * 格式：[legacy=]<[acdn][,1-4]>] [,order=<device[,device...]>]
     * 作用：指定启动顺序
     * 例如：order=scsi0;ide2 或 legacy=dc（已废弃格式）
     * a=软盘，c=硬盘，d=CD-ROM，n=网络
     */
    private String boot;

    /**
     * 格式：pve-qm-bootdisk
     * 已废弃
     * 建议使用 boot order 参数代替
     */
    private String bootDisk;

    /**
     * 格式：<integer> (0 - N)
     * 作用：限制I/O带宽（KB/s）
     * 0表示不限制
     */
    private Integer bwLimit;

    /**
     * 格式：<volume>
     * 是 ide2 选项的别名
     * 用于指定CD-ROM设备
     */
    private String cdrom;

    /**
     * <p>
     * ## cicustom 参数
     * <p>
     * ### 基本格式与用途
     * `cicustom` 参数用于cloud-init初始化过程中指定自定义文件，替换自动生成的配置。
     * <p>
     * ### 格式选项
     * - `[meta=<volume>]` - 自定义元数据文件
     * - `[network=<volume>]` - 自定义网络配置
     * - `[user=<volume>]` - 自定义用户数据
     * - `[vendor=<volume>]` - 自定义供应商数据
     * <p>
     * ### 技术细节
     * - 与cloud-init工具配合使用
     * - 允许高度自定义虚拟机的首次启动配置
     * - 指定的卷需包含有效的cloud-init格式配置文件
     */
    private String ciCustom;

    /**
     * 格式：<string>
     * cloud-init用户密码
     * 不推荐使用，建议使用SSH密钥
     */
    private String ciPassword;

    /**
     * 格式：configdrive2 | nocloud | opennebula
     * cloud-init配置格式
     * 根据操作系统类型自动选择：
     * <p>
     * Windows用configdrive2
     * Linux用nocloud
     */
    private String ciType;

    /**
     * 格式：<boolean>
     * 默认值：true
     * 首次启动后是否自动升级包
     */
    private Boolean ciUpgrade;

    /**
     * 格式：<string>
     * cloud-init用户名
     * 用于替换默认用户配置
     * cloud-init: User name to change ssh keys and password for instead of the image's configured default user.
     */
    private String ciUser;

    /**
     * cores: integer
     * <p>
     * <p>
     * 格式：<integer> (1 - N)
     * 默认值：1
     * 描述：每个CPU插槽的核心数
     * 用途：设置每个CPU插槽包含多少个核心
     */
    private Integer cores;

    /**
     * 格式：[cputype=]<string>] [,flags=<+FLAG|-FLAG...>] [,hidden=<1|0>] [,hv-vendor-id=<vendor-id>] [,phys-bits=<8-64|host>] [,reported-model=<enum>]
     * 描述：模拟的CPU类型
     * 主要参数：
     * cputype：CPU型号
     * flags：CPU特性标志（开启用+，关闭用-）
     * hidden：是否隐藏CPU信息
     * hv-vendor-id：虚拟化供应商ID
     * phys-bits：物理地址位数
     * reported-model：报告的CPU型号
     */
    private String cpu;

    /**
     * 格式：<number> (0 - 128)
     * 默认值：0
     * 描述：限制CPU使用率
     * 注意：如果主机有2个CPU，总CPU时间为2；0表示不限制
     */
    private Integer cpuLimits;

    /**
     * cpuunits: integer
     * 格式：<integer> (1 - 262144)
     * 默认值：cgroup v1: 1024
     * 描述：VM的CPU权重
     * 说明：数值越大获得的CPU时间越多，是相对于其他VM的权重值
     */
    private Integer cpuUnits;

    /**
     * 格式：<string>
     * 描述：VM的描述信息
     * 说明：显示在web界面的VM摘要中，保存为配置文件中的注释
     */
    private String description;

    /**
     * efidisk0: string
     * <p>
     * <p>
     * 格式：[file=]<volume> [,efitype=<2m|4m>] [,format=<enum>] [,import-from=<volume>]
     * 描述：配置存储EFI变量的磁盘
     * 参数说明：
     * <p>
     * file：存储卷
     * efitype：EFI类型（2m或4m）
     * format：格式类型
     * import-from：导入源
     * <p>
     * 与qm importdisk不具备相同的作用
     * efidisk0：
     * <p>
     * 是UEFI固件启动所需的磁盘
     * 当使用 bios: ovmf 时需要
     * 用来存储 UEFI 的启动配置、变量等
     * 格式如：efidisk0: local:100/vm-100-efidisk0.qcow2
     * 是一个很小的磁盘，通常几MB
     * <p>
     * qm importdisk：
     * <p>
     * 是一个命令，用于导入磁盘镜像到PVE存储
     * 用于导入外部磁盘镜像（比如ISO转换成的磁盘镜像）
     * 可以导入任意大小的磁盘
     * 例如：qm importdisk 100 debian.img local
     * <p>
     * 区别：
     * <p>
     * efidisk0 是系统引导需要的特殊磁盘
     * importdisk 是导入普通系统磁盘的工具
     * efidisk0 通常自动创建和管理
     * importdisk 需要手动操作和指定
     */
    private String efiDisk0;

    /**
     * 格式：<boolean>
     * 描述：允许覆盖已存在的VM
     * 用于强制创建/覆盖操作
     */
    private Boolean force;

    /**
     * 格式：<boolean>
     * 描述：在启动时冻结CPU
     * 需要使用'c'监视命令来开始执行
     */
    private Boolean freeze;

    /**
     * 格式：<string>
     * 描述：在VM生命周期的不同阶段执行的脚本
     * 用于自动化操作
     */
    private String hookScript;

    /**
     * hostpci[n] 参数
     * 基本格式与用途
     * hostpci[n] 参数用于将宿主机的PCI设备直接映射到虚拟机，实现直通功能。
     * 格式选项
     * <p>
     * [host=]<HOSTPCIID[;HOSTPCIID2...]> - 宿主机PCI设备ID
     * [device-id=<hex id>] - 设备ID
     * [legacy-id=<1|0>] - 是否使用传统ID
     * [mapping=<mapping-id>] - 映射标识符
     * [pcie=<1|0>] - 是否使用PCIe总线
     * [rombar=<1|0>] - 是否启用ROM条
     * [romfile=<string>] - ROM文件路径
     * [sub-device-id=<hex id>] - 子设备ID
     * [sub-vendor-id=<hex id>] - 子供应商ID
     * [vendor-id=<hex id>] - 供应商ID
     * [x-vga=<1|0>] - 是否为VGA设备
     * <p>
     * 技术细节
     * <p>
     * 允许虚拟机直接访问宿主机硬件
     * 设备一旦分配给VM，宿主机将无法使用该设备
     * 使用此功能的VM无法迁移（因为硬件绑定）
     * 标记为实验性功能，可能存在问题
     * <p>
     * 实际应用示例
     * Copyhostpci0=0000:01:00.0,pcie=1
     * 将宿主机上PCI设备0000:01:00.0直通给虚拟机
     * Copyhostpci0=0000:02:00.0,x-vga=1
     * 将显卡直通给虚拟机
     * 使用场景
     * <p>
     * 显卡直通（GPU直通）用于游戏或图形处理
     * 网卡直通用于高性能网络应用
     * 专用存储控制器直通
     * 其他需要直接硬件访问的设备（如USB控制器）
     * <p>
     * 注意事项
     * <p>
     * 需要在BIOS/UEFI中启用IOMMU支持
     * 需要在宿主机内核参数中添加相应支持
     * 部分设备可能需要驱动隔离（vfio-pci）
     */
    private List<String> hostPci;

    /**
     * 格式：<string>
     * 默认值：network,disk,usb
     * 描述：选择性启用热插拔功能
     * 支持特性：network, disk, cpu, memory, usb, cloudinit
     * 使用'0'完全禁用热插拔
     * 注意：USB热插拔需要客户机版本>=7.1且ostype为l26或windows>7
     */
    private String hotPlug;

    /**
     * 格式：any | 2 | 1024
     * 描述：启用/禁用大页内存
     * 可选值：
     * any：自动选择大小
     * 2：使用2MB大页
     * 1024：使用1GB大页
     */
    private String hugePages;

    /**
     * ide[n] 参数
     * 基本格式与用途
     * ide[n] 参数用于配置IDE硬盘或CD-ROM设备，支持0-3个设备(n从0到3)。
     * 格式选项
     * 与SATA/SCSI设备类似，但使用IDE接口：
     * <p>
     * [file=]<volume> - 指定存储卷
     * [aio=<native|threads|io_uring>] - 异步IO模式
     * [backup=<1|0>] - 是否包含在备份中
     * [bps=<bps>] - 带宽限制
     * [cache=<enum>] - 缓存模式
     * [cyls=<integer>] - 柱面数
     * [detect_zeroes=<1|0>] - 是否检测零块
     * [discard=<ignore|on>] - 丢弃/TRIM支持
     * [format=<enum>] - 磁盘格式
     * [heads=<integer>] - 磁头数
     * [media=<cdrom|disk>] - 介质类型
     * 以及多种性能和IO相关参数
     * <p>
     * 技术特性
     * <p>
     * 兼容性最广泛的磁盘接口
     * 性能低于SATA/SCSI/VirtIO
     * 最适合用作CD-ROM或引导设备
     * <p>
     * 实际应用示例
     * Copyide0=local:iso/windows.iso,media=cdrom
     * 挂载ISO文件作为CD-ROM
     * Copyide0=local-lvm:vm-100-disk-1,cache=writeback
     * 配置IDE硬盘
     * 最佳实践
     * <p>
     * 现代系统建议仅将IDE用于CD-ROM设备（ide2=cdrom）
     * 对于主要磁盘，推荐使用性能更好的VirtIO/SCSI/SATA
     * 对于旧操作系统或特殊引导需求，IDE仍然很有用
     * <p>
     * 兼容性说明
     * <p>
     * 几乎所有操作系统都原生支持IDE设备
     * 不需要额外驱动
     * 对于不支持VirtIO的旧系统很有用
     * <p>
     * IDE接口是最传统的虚拟磁盘接口，虽然性能不如现代接口，但兼容性极佳，在特定场景下仍然有其价值。而hostpci直通功能则是满足高性能需求的关键特性，尤其适用于需要直接访问物理硬件的特殊应用场景。
     */
    private List<String> ide;

    /**
     * import-working-storage: string
     * 格式：<storage ID>
     * 描述：导入过程中用作中间提取存储的基于文件的存储
     * 需要启用'images'内容类型
     * 默认使用源存储
     * 用途：在导入VM时作为临时工作存储区
     */
    private String importWorkingStorage;

    /**
     * 格式：[gw=<GatewayIPv4>] [,gw6=<GatewayIPv6>] [,ip=<IPv4Format/CIDR>] [,ip6=<IPv6Format/CIDR>]
     * 描述：cloud-init用于指定网络接口的IP地址和网关
     * 特殊值：
     * dhcp：使用DHCP获取IP（无需指定网关）
     * auto：IPv6无状态自动配置（需要cloud-init 19.4+）
     * 默认行为：如果启用cloud-init且未指定IP，默认使用IPv4 DHCP
     * 注意：网关是可选的，但如果指定必须与IP类型匹配
     */
    private List<String> ipConfig;

    /**
     * 格式：size=<integer> [,name=<string>]
     * 描述：VM间共享内存配置
     * 用途：
     * VM之间直接通信
     * VM与主机之间直接通信
     * 提高VM间通信性能
     * 参数：
     * size：共享内存大小
     * name：共享内存标识符
     */
    private String ivshmem;

    /**
     * 格式：<boolean>
     * 默认值：false
     * 描述：与hugepages配合使用，启用后VM关闭时不会删除大页内存，可用于后续启动
     * 用途：提高VM重启性能
     */
    private Boolean keepHugePages;

    /**
     * keyboard: enum
     * 格式：多种键盘布局选项（de, de-ch, da, en-gb, en-us等）
     * 描述：VNC服务器的键盘布局
     * 注意：通常不需要设置，最好在客户机OS中处理
     */
    private String keyBoard;

    /**
     * 格式：<boolean>
     * 默认值：true
     * 描述：启用/禁用KVM硬件虚拟化
     */
    private Boolean kvm;

    /**
     * live-restore: boolean
     * 格式：<boolean>
     * 描述：在后台导入或恢复时立即启动VM
     */
    private Boolean liveRestore;

    /**
     * localtime: boolean
     * 格式：<boolean>
     * 描述：将RTC设置为本地时间
     * 注意：Windows OS类型默认启用
     */
    private Boolean localTime;

    /**
     * lock: enum
     * 格式：backup | clone | create | migrate | rollback | snapshot | snapshot-delete | suspending | suspended
     * 描述：VM的锁定/解锁状态
     * 用于不同操作的状态控制
     */
    private String lock;

    /**
     * machine: string
     * 格式：[type=]<machine type>] [,vionmmu=<intel|virtio>]
     * 描述：指定QEMU机器类型
     */
    private String machine;

    /**
     * memory: string
     * 格式：[current=]<integer>
     * 描述：内存属性设置
     */
    private Double memory;

    /**
     * migrate_downtime: number
     * <p>
     * <p>
     * 格式：<number> (0 - N)
     * 默认值：0.1
     * 描述：迁移最大允许停机时间（秒）
     * 会根据需要自动调整
     */
    private Double migrateDowntime;

    /**
     * migrate_speed: integer
     * <p>
     * 格式：<integer> (0 - N)
     * 默认值：0
     * 描述：设置迁移最大速度（MB/s）
     * 0表示无限制
     */
    private Integer migrateSpeed;

    /**
     * name: string
     * <p>
     * 格式：<string>
     * 描述：设置VM名称
     * 仅用于Web界面配置
     */
    private String name;

    /**
     * nameserver: string
     * 格式：<string>
     * 描述：cloud-init的DNS服务器设置
     * 如果未设置，自动使用主机设置
     */
    private String nameserver;

    /**
     * 描述：网络设备配置
     * <p>
     * [model=]<enum> [,bridge=<bridge>]
     * [,firewall=<1|0>] [,link_down=<1|0>]
     * [,macaddr=XX:XX:XX:XX:XX:XX]
     * [,mtu=<integer>] [,queues=<integer>]
     * [,rate=<number>] [,tag=<integer>]
     * [,trunks=<vlanid[,vlanid...]>]
     * <p>
     * 主要参数：
     * <p>
     * model：网卡类型
     * bridge：网桥名称
     * firewall：启用防火墙
     * macaddr：MAC地址
     * mtu：最大传输单元
     * rate：速率限制
     * tag：VLAN标签
     */
    private List<String> net;

    // /**
    //  * numa: boolean
    //  * 格式：<boolean>
    //  * 默认值：false
    //  * 描述：启用/禁用NUMA（非统一内存访问）
    //  */
    // private Boolean numa;

    /**
     * numa[n]: string
     * 格式：cpus=<id[-id],..> [,hostnodes=<id[-id],..>] [,memory=<number>] [,policy=<preferred|bind|interleave>]
     * 描述：NUMA拓扑配置
     * 用于高级内存管理
     */
    private List<String> numa;

    /**
     * onboot: boolean
     * 格式：<boolean>
     * 默认值：false
     * 描述：系统启动时是否自动启动VM
     */
    private Boolean onBoot;

    /**
     * ostype: enum
     * <p>
     * <p>
     * 格式：多种操作系统类型选项
     * 可选值包括：
     * <p>
     * Windows系列：wxp, w2k, w2k3, w2k8, wvista, win7, win8, win10, win11
     * Linux系列：l24, l26
     * 其他：other, solaris
     * <p>
     * 描述：指定客户机操作系统类型
     * 用途：为特定操作系统启用优化特性
     */
    private String osType;

    /**
     * parallel[n]: string
     * <p>
     * <p>
     * 格式：/dev/parport\d+|/dev/usb/lp\d+
     * 范围：n是0到2
     * 描述：映射主机并口设备
     * 警告：
     * <p>
     * 允许直接访问主机硬件
     * 使用此选项的VM无法迁移
     * 实验性功能，有用户报告问题
     */
    private List<String> parallel;

    /**
     * pool: string
     * 格式：<string>
     * 描述：将VM添加到指定的资源池
     * 用于资源管理和组织
     */
    private String pool;

    /**
     * protection: boolean
     * <p>
     * 格式：<boolean>
     * 默认值：false
     * 描述：设置VM的保护标志
     * 功能：
     * 启用后禁止删除VM
     * 禁止删除磁盘操作
     * 用于防止意外删除
     */
    private Boolean protection;

    /**
     * reboot: boolean
     * <p>
     * <p>
     * 格式：<boolean>
     * 默认值：true
     * 描述：允许重启
     * 如果设为'0'，VM在重启时会退出
     */
    private Boolean reboot;

    /**
     * rng0: string
     * <p>
     * 格式：
     * [source=]/dev/urandom|/dev/random|/dev/hwrng
     * [,max_bytes=<integer>]
     * [,period=<integer>]
     * <p>
     * 描述：配置基于VirtIO的随机数生成器
     * 参数：
     * source：随机数源
     * max_bytes：最大字节数
     * period：周期
     */
    private String rng0;

    /**
     * sata[n] 参数用于配置虚拟机的SATA硬盘或CD-ROM设备，其中n是设备号（从0到5）。
     * 最多支持6个设备
     * sata[n]: [file=]<volume> [,aio=<native|threads|io_uring>] [,backup=<1|0>] [,bps=<bps>] [,bps_max_length=<seconds>]...
     */
    private List<String> sata;

    /**
     * scsi[n]参数
     * 用于配置虚拟机的SCSI硬盘或CD-ROM设备，其中n是设备号（从0到30）。与SATA设备类似，但支持更多设备（最多31个设备，而SATA最多支持6个）。
     */
    private List<String> scsi;

    /**
     * scsihw参数设置SCSI控制器硬件类型，这会影响所有SCSI设备的性能和兼容性。
     * 可选值
     * lsi - LSI 53C895A（传统型号）
     * lsi53c810 - LSI 53C810
     * virtio-scsi-pci - VirtIO SCSI PCI（推荐，性能最佳）
     * virtio-scsi-single - 单队列VirtIO SCSI PCI
     * megasas - MegaRAID SAS 8708EM2
     * pvscsi - VMware的并行SCSI
     */
    private String scsiHw;

    /**
     * searchdomain 参数
     * 功能描述
     * searchdomain参数用于为容器设置DNS搜索域。这主要应用于LXC容器而非KVM虚拟机。
     * <p>
     * 用于cloud-init配置
     * 如果未设置searchdomain和nameserver，则自动使用宿主机的设置
     * 多个域名可以用空格分隔
     */
    private String searchDomain;

    /**
     * serial[n] 参数
     * 基本格式与用途
     * serial[n] 参数用于在虚拟机内创建串行设备，支持0-3个设备（n从0到3）。
     * 格式选项
     * <p>
     * /dev/... - 透传宿主机上的物理串行设备
     * socket - 在宿主机侧创建Unix套接字
     * <p>
     * 实际用法示例
     * Copyserial0=/dev/ttyS0
     * 或
     * Copyserial0=socket
     * 注意事项
     * <p>
     * 迁移限制：使用主机串行设备的VM无法迁移
     * 实验性功能：标记为实验性，可能存在问题
     * 应用场景：适用于需要串行控制台的特殊系统，如某些网络设备模拟
     */
    private List<String> serial;

    /**
     * shares 参数
     * 功能说明
     * 控制虚拟机的内存自动膨胀（auto-ballooning）优先级。
     * 参数范围
     * <p>
     * 范围：0 - 50000
     * 默认值：1000
     * <p>
     * 工作原理
     * <p>
     * 值越大，VM在内存压力下获得的内存比例越高
     * 设置为0完全禁用auto-ballooning
     * 相对权重 - 值为2000的VM比值为1000的获得双倍优先级
     * <p>
     * 实际应用示例
     * Copyshares=2000
     * 高优先级虚拟机（如数据库服务器）设置更高值
     * Copyshares=500
     * 低优先级虚拟机（如开发/测试环境）设置更低值
     */
    private Integer shares;

    /**
     * smbios1 参数
     * 功能说明
     * 自定义虚拟机SMBIOS信息，影响VM内看到的"硬件"信息。
     * 主要字段
     * <p>
     * manufacturer - 制造商名称
     * product - 产品名称
     * serial - 序列号
     * uuid - 唯一标识符
     * version - 固件版本
     * <p>
     * 特殊编码要求
     * 所有字段值需使用Base64编码
     * 实际用例
     * Copysmbios1=uuid=a0b1c2d3-e4f5-6789-abcd-ef0123456789
     * 设置特定UUID，对许可证绑定很有用
     * Copysmbios1=manufacturer=Q3VzdG9tIE1hbnVmYWN0dXJlcg==,product=Q3VzdG9tIFByb2R1Y3Q=
     * 自定义制造商和产品信息（Base64编码的"Custom Manufacturer"和"Custom Product"）
     */
    private String smBios1;

    /**
     * smp 和 sockets 参数
     * 功能区别
     * <p>
     * smp - 已弃用的总CPU数设置
     * sockets - 推荐使用的CPU插槽数设置
     * <p>
     * 范围与默认值
     * <p>
     * 默认值：1
     * 范围：1-N（取决于宿主机CPU核心数）
     * <p>
     * 最佳实践
     * 总CPU数 = sockets × cores（在cores参数中设置）
     * 推荐使用sockets和cores组合而非smp
     * 示例配置
     * Copysockets=2,cores=4
     * 创建具有2个插槽、每插槽4核，总共8个vCPU的虚拟机
     */
    private String smp;

    /**
     * smp 和 sockets 参数
     * 功能区别
     * <p>
     * smp - 已弃用的总CPU数设置
     * sockets - 推荐使用的CPU插槽数设置
     * <p>
     * 范围与默认值
     * <p>
     * 默认值：1
     * 范围：1-N（取决于宿主机CPU核心数）
     * <p>
     * 最佳实践
     * 总CPU数 = sockets × cores（在cores参数中设置）
     * 推荐使用sockets和cores组合而非smp
     * 示例配置
     * Copysockets=2,cores=4
     * 创建具有2个插槽、每插槽4核，总共8个vCPU的虚拟机
     */
    private Integer sockets;

    /**
     * spice_enhancements 参数
     * 功能说明
     * 增强SPICE远程桌面协议的用户体验。
     * 主要选项
     * <p>
     * foldersharing=<1|0> - 启用/禁用文件夹共享
     * videostreaming=<off|all|filter> - 视频流优化选项
     * <p>
     * 应用场景
     * <p>
     * 需要宿主机与VM之间文件传输时启用foldersharing
     * 需要流畅视频播放体验时调整videostreaming
     * <p>
     * 示例配置
     * Copyspice_enhancements=foldersharing=1,videostreaming=all
     */
    private String spiceEnhancements;

    /**
     * sshkeys 参数
     * 功能说明
     * 用于cloud-init，在首次启动时设置VM的SSH公钥。
     * 格式要求
     * <p>
     * 每行一个密钥
     * 使用标准OpenSSH格式
     * <p>
     * 实际应用
     * Copysshkeys=ssh-rsa AAAAB3NzaC1yc2EAAA...user@host
     * 允许无密码SSH登录，自动化部署的重要组成部分
     */
    private String sshKeys;

    /**
     * start 参数
     * 功能说明
     * 控制VM创建后是否自动启动。
     * 可选值
     * false - 默认值，不自动启动
     * true - 创建后立即启动VM
     */
    private Boolean start;

    /**
     * startup 参数
     * 功能说明
     * 定义PVE集群启动/关闭时VM的行为。
     * 关键选项
     * <p>
     * order=<n> - 启动顺序（非负整数）
     * up=<n> - 启动延迟（秒）
     * down=<n> - 关闭延迟（秒）
     * <p>
     * 特性细节
     * <p>
     * 关机顺序与启动顺序相反（先启动的后关闭）
     * 数值越小越先启动
     * <p>
     * 实际配置示例
     * Copystartup=order=3,up=120,down=60
     * 设置VM在集群启动序列中排第3位，启动前等待120秒，关机前等待60秒
     */
    private String startup;

    /**
     * 您说得对，我漏掉了startdate参数。让我补充这个重要参数的详细信息：
     * <p>
     * ## startdate 参数
     * <p>
     * ### 功能说明
     * 设置虚拟机内部实时时钟(RTC)的初始日期和时间。
     * <p>
     * ### 参数格式
     * - `now` - 默认值，使用当前系统时间
     * - `YYYY-MM-DD` - 仅指定日期
     * - `YYYY-MM-DDTHH:MM:SS` - 指定精确的日期和时间
     * <p>
     * ### 有效范围示例
     * - `now`
     * - `2006-06-17`
     * - `2006-06-17T16:01:21`
     * <p>
     * 这个参数在特定场景下很有用，比如时间敏感的测试环境、日期许可证验证测试或特定时间点的系统行为模拟。
     */
    private String startDate;

    /**
     * storage 参数
     * 功能说明
     * 设置VM的默认存储位置。
     * 设置要求
     * 必须指定有效的存储ID（在PVE中已配置）
     * 最佳实践
     * 选择适合VM工作负载的存储类型：
     * <p>
     * 高性能需求：local-lvm或ZFS
     * 高可用需求：共享存储如Ceph
     */
    private String storage;

    /**
     * tablet 参数
     * 功能说明
     * 控制USB平板设备的启用状态。
     * 可选值
     * <p>
     * 1 - 默认值，启用
     * 0 - 禁用
     * <p>
     * 技术细节
     * <p>
     * 主要用于改善VNC中鼠标定位
     * 在有大量纯控制台VM的情况下，禁用可提高性能
     * <p>
     * 应用场景
     * 使用命令qm set <vmid> --tablet 0禁用图形性能不佳的VM上的tablet设备
     */
    private Boolean tablet;

    /**
     * tags 参数
     * 功能说明
     * 为VM添加元数据标签，便于分类和搜索。
     * 使用方法
     * 多个标签以逗号分隔
     * 实际应用示例
     * Copytags=production,database,high-priority
     * 通过qm list --filter tags=production可快速筛选所有生产环境VM
     */
    private String tags;

    /**
     * tdf 参数
     * 功能说明
     * 启用/禁用时间漂移修复功能。
     * 应用场景
     * <p>
     * 长时间运行的VM可能出现时钟偏移
     * 对时间敏感的应用（如数据库）建议启用
     * <p>
     * 示例配置
     * tdf=1
     */
    private Boolean tdf;

    /**
     * template 参数
     * 功能说明
     * 将VM标记为模板，用于创建链接克隆。
     * 特性
     * ● 模板VM不能直接启动
     * ● 可大幅节省存储空间（链接克隆只存储差异）
     * 实际用法
     * template=1
     * 或使用命令：qm template <vmid>
     * 通过这种API配置，可以实现从基础镜像快速部署多个相似VM的自动化工作流。
     */
    private Boolean template;

    /**
     * tpmstate0 参数
     * 基本格式与用途
     * tpmstate0 参数用于配置存储TPM (可信平台模块) 状态的磁盘。
     * 格式选项
     * <p>
     * [file=]<volume> - 指定存储卷
     * [import-from=<source volume>] - 从现有卷导入
     * [size=<DiskSize>] - 磁盘大小
     * [version=<v1.2|v2.0>] - TPM版本
     * <p>
     * 技术细节
     * <p>
     * 格式固定为'raw'
     * 大小固定为4 MiB（忽略SIZE_IN_GiB参数）
     * 支持TPM 1.2和2.0版本
     * <p>
     * 实际应用示例
     * Copytpmstate0=local-lvm:vm-100-disk-1,version=v2.0
     * 或
     * Copytpmstate0=local-lvm:0,import-from=local:backup/vm-100-tpm.raw
     * 使用场景
     * <p>
     * 需要BitLocker加密的Windows系统
     * 需要安全启动的虚拟机
     * 企业级安全合规要求的环境
     */
    private String tpmState0;

    /**
     * unique 参数
     * 功能说明
     * 分配唯一的随机以太网MAC地址。
     * 参数类型
     * 布尔值 (boolean)
     * 实际应用
     * Copyunique=1
     * 使用场景
     * <p>
     * 避免网络中的MAC地址冲突
     * 克隆虚拟机时确保网络配置唯一性
     */
    private Boolean unique;

    /**
     * unused[n] 参数
     * 功能说明
     * 引用未使用的卷，主要用于内部使用。
     * 格式
     * [file=]<volume>
     * 注意事项
     * <p>
     * 内部使用，不应手动修改
     * 通常由PVE系统自动管理
     */
    private List<String> unused;

    /**
     * usb[n] 参数
     * 功能说明
     * 配置USB设备，支持多达15个设备(n从0到14)。
     * 格式选项
     * <p>
     * [host=]<HOST/USB/DEVICE|spice> - 指定主机USB设备或SPICE重定向
     * [mapping=<mapping-id>] - 设备映射ID
     * [usb3=<1|0>] - 是否使用USB 3.0
     * <p>
     * 参数限制
     * <p>
     * 对于机器版本>=7.1，ostype为l26或windows>7时，n最多可达14
     * 其他情况下n最多可达4
     * <p>
     * 示例配置
     * Copyusb0=host=1c1a:00ab
     * 或
     * Copyusb0=spice,usb3=1
     * 应用场景
     * <p>
     * 向VM传递主机USB设备（如U盘、打印机）
     * 通过SPICE协议重定向客户端计算机的USB设备
     */
    private List<String> usb;

    /**
     * vcpus 参数
     * 功能说明
     * 设置热插拔的vCPU数量。
     * 参数类型
     * 整数 (integer)
     * 默认值
     * 0
     * 范围
     * 1-N
     * 配置示例
     * vcpus=4
     * 技术说明
     * ● 允许在VM运行时调整可用CPU数量
     * ● 需要客户机操作系统支持热插拔
     */
    private Integer vCpus;

    /**
     * vga 参数
     * 功能说明
     * 配置虚拟机的VGA硬件。
     * 格式选项
     * <p>
     * [type=]<enum> - 显示类型（std、qxl、vmware等）
     * [clipboard=<vnc>] - 剪贴板支持
     * [memory=<integer>] - 显存大小(MB)
     * <p>
     * 特性说明
     * <p>
     * QEMU 2.9默认VGA类型为'std'
     * 高分辨率模式(>=1280x1024x16)可能需要增加显存
     * 'cirrus'类型为SPICE启用显示服务器
     * Windows可选择独立显示数量，Linux可自行添加显示
     * <p>
     * 实际配置示例
     * Copyvga=qxl,memory=32
     * 或
     * Copyvga=std,clipboard=vnc
     * 使用场景
     * <p>
     * 需要高分辨率或多显示器的VM
     * 需要图形加速的应用
     */
    private String vga;

    /**
     * virtio[n] 参数
     * 基本格式与用途
     * virtio[n] 参数用于配置VirtIO硬盘，支持0-15个设备。
     * 格式选项
     * 与SCSI和SATA设备类似，但使用VirtIO驱动：
     * <p>
     * [file=]<volume> - 指定存储卷
     * [aio=<native|threads|io_uring>] - 异步IO模式
     * [backup=<1|0>] - 是否包含在备份中
     * [bps=<bps>] - 带宽限制
     * 以及各种IO性能参数
     * <p>
     * 技术特性
     * <p>
     * 性能最好的虚拟磁盘类型
     * 要求客户机操作系统安装VirtIO驱动
     * 支持各种IO调优参数
     * <p>
     * 实际应用示例
     * Copyvirtio0=local-lvm:vm-100-disk-1,cache=writeback,aio=io_uring
     * 最佳实践
     * <p>
     * 现代Linux默认包含VirtIO驱动
     * Windows需要额外安装驱动（可通过ISO提供）
     * 对于性能关键型应用是首选
     */
    private List<String> virtio;

    /**
     * vmgenid 参数
     * 功能说明
     * VM世代ID设备，向客户操作系统公开128位整数标识符。
     * 默认值
     * 1 (自动生成)
     * 格式
     * (?:[a-fA-F0-9]{8}-){3}[a-fA-F0-9]{8}（UUID格式）
     * 技术细节
     * <p>
     * 允许在虚拟机配置变更时（如快照执行或从模板创建）通知客户操作系统
     * 客户OS可以标记分布式数据库为脏，重新初始化随机数生成器等
     * 仅通过API/CLI创建或更新时自动创建，手动编辑配置文件无效
     * <p>
     * 适用场景
     * <p>
     * 运行数据库或集群应用的VM
     * 需要对VM配置变更作出反应的应用
     */
    private String vmGenId;

    /**
     * vmstatestorage 参数
     * 功能说明
     * 指定VM状态卷/文件的默认存储位置。
     * 格式
     * <storage ID>
     * 实际配置
     * Copyvmstatestorage=local
     * 用途
     * <p>
     * 存储VM暂停状态
     * 存储快照相关文件
     */
    private String vmStateStorage;

    /**
     * watchdog 参数
     * 功能说明
     * 创建虚拟硬件看门狗设备。
     * 格式选项
     * <p>
     * [model=]<i6300esb|ib700> - 看门狗设备型号
     * [action=<enum>] - 触发动作
     * <p>
     * 工作原理
     * <p>
     * 启用后必须由客户机内的代理定期轮询
     * 否则看门狗将重置客户机或执行指定动作
     * <p>
     * 应用场景
     * <p>
     * 提高服务可用性
     * 自动重启卡死的虚拟机
     * 监控客户机健康状态
     * <p>
     * 配置示例
     * Copywatchdog=model=i6300esb,action=reset
     */
    private String watchDog;
}
