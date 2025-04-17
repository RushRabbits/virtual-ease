package com.awake.ve.virtual.service;


import com.awake.ve.virtual.domain.bo.VeCreateOrEditVmBo;
import com.awake.ve.virtual.domain.bo.VeShutdownOrStopVmBo;
import com.awake.ve.virtual.domain.vo.VeVmListVo;
import com.awake.ve.virtual.domain.vo.VeVmConfigVo;
import com.awake.ve.virtual.domain.vo.VeVmStatusVo;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 虚拟机信息Service接口
 *
 * @author 突突兔
 * @date 2025-02-28
 */
public interface IVeVmInfoService {

    /**
     * 查询指定节点下的虚拟机列表
     *
     * @author wangjiaxing
     * @date 2025/3/17 16:57
     */
    List<VeVmListVo> vmList();

    /**
     * 获取虚拟机配置信息
     *
     * @param vmId 虚拟机id
     * @return {@link VeVmConfigVo}
     * @author wangjiaxing
     * @date 2025/3/18 14:08
     */
    VeVmConfigVo getVmConfig(@NotNull(message = "虚拟机id不能为空") Long vmId);

    /**
     * 销毁指定虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 17:52
     */
    Boolean destroyVm(Long vmId);

    /**
     * 启动虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:06
     */
    Boolean startVm(Long vmId);

    /**
     * 获取虚拟机状态
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:28
     */
    VeVmStatusVo vmStatus(Long vmId);

    /**
     * 挂起虚拟机
     *
     * @param vmId 挂起虚拟机
     * @author wangjiaxing
     * @date 2025/3/19 18:48
     */
    Boolean suspendVm(Long vmId);

    /**
     * 恢复虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:56
     */
    Boolean resumeVm(Long vmId);

    /**
     * 重启虚拟机(正常的重启)
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 19:04
     */
    Boolean rebootVm(Long vmId);

    /**
     * 重启虚拟机(强制重启)
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 19:04
     */
    Boolean resetVm(Long vmId);

    /**
     * 创建虚拟机
     *
     * @param bo {@link VeCreateOrEditVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 9:46
     */
    Boolean createVm(VeCreateOrEditVmBo bo);

    /**
     * 异步修改虚拟机配置
     *
     * @param bo {@link VeCreateOrEditVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 11:37
     */
    Boolean editAsync(VeCreateOrEditVmBo bo);

    /**
     * 同步修改虚拟机配置
     *
     * @param bo {@link VeCreateOrEditVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 11:37
     */
    Boolean editSync(VeCreateOrEditVmBo bo);

    /**
     * 关闭虚拟机(正常关闭)
     *
     * @param bo {@link VeShutdownOrStopVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 12:08
     */
    Boolean shutdown(VeShutdownOrStopVmBo bo);

    /**
     * 关闭虚拟机(强制关闭)
     *
     * @param bo {@link VeShutdownOrStopVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 12:08
     */
    Boolean stop(VeShutdownOrStopVmBo bo);
}
