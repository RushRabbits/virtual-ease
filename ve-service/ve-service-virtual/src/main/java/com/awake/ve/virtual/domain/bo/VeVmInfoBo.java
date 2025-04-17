package com.awake.ve.virtual.domain.bo;

import com.awake.ve.common.core.validate.AddGroup;
import com.awake.ve.common.core.validate.EditGroup;
import com.awake.ve.common.mybatis.core.domain.BaseEntity;
import com.awake.ve.virtual.domain.VeVmInfo;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;


/**
 * 虚拟机信息业务对象 ve_vm_info
 *
 * @author 突突兔
 * @date 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMappers(
        {
                // @AutoMapper(target = PVECreateOrRestoreVmApiRequest.class, reverseConvertGenerate = false),
                @AutoMapper(target = VeVmInfo.class, reverseConvertGenerate = false)
        }
)
public class VeVmInfoBo extends BaseEntity {

    /**
     * 虚拟机id
     */
    @NotNull(message = "虚拟机id不能为空", groups = {EditGroup.class})
    @Min(value = 1, message = "虚拟机id不能小于100", groups = {AddGroup.class, EditGroup.class})
    @Max(value = 999999999, message = "虚拟机id不能大于999999999", groups = {AddGroup.class, EditGroup.class})
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
