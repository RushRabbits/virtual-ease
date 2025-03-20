package com.awake.ve.virtual.controller;

import java.util.List;

import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.web.core.BaseController;
import com.awake.ve.virtual.domain.bo.VeCreateVmBo;
import com.awake.ve.virtual.domain.vo.VeVmListVo;
import com.awake.ve.virtual.domain.vo.VeVmConfigVo;
import com.awake.ve.virtual.domain.vo.VeVmStatusVo;
import com.awake.ve.virtual.service.IVeVmInfoService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

/**
 * 虚拟机信息
 *
 * @author 突突兔
 * @date 2025-02-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ve/vm")
public class VeVmInfoController extends BaseController {

    private final IVeVmInfoService veVmInfoService;

    /**
     * 查询指定节点下的虚拟机列表
     *
     * @author wangjiaxing
     * @date 2025/3/17 16:58
     */
    @SaCheckPermission("ve:vmInfo:list")
    @GetMapping("/vmList")
    public R<List<VeVmListVo>> vmList() {
        return R.ok(veVmInfoService.vmList());
    }

    /**
     * 获取虚拟机配置信息
     *
     * @param vmId 虚拟机id
     * @return {@link R}<{@link VeVmConfigVo}></>
     * @author wangjiaxing
     * @date 2025/3/18 14:08
     */
    @SaCheckPermission("ve:vmInfo:getConfig")
    @GetMapping("/config/{vmId}")
    public R<VeVmConfigVo> getVmConfig(@NotNull(message = "虚拟机id不能为空") @PathVariable Long vmId) {
        return R.ok(veVmInfoService.getVmConfig(vmId));
    }

    /**
     * 销毁指定虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 17:52
     */
    @SaCheckPermission("ve:vmInfo:destroyVm")
    @DeleteMapping("/destroyVm/{vmId}")
    public R<Boolean> destroyVm(@PathVariable Long vmId) {
        return R.ok(veVmInfoService.destroyVm(vmId));
    }

    /**
     * 启动虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:06
     */
    @PostMapping("/startVm/{vmId}")
    public R<Boolean> startVm(@PathVariable Long vmId) {
        return R.ok(veVmInfoService.startVm(vmId));
    }

    /**
     * 获取虚拟机状态
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:28
     */
    @SaCheckPermission("ve:vmInfo:status")
    @GetMapping("/status/{vmId}")
    public R<VeVmStatusVo> vmStatus(@PathVariable Long vmId) {
        return R.ok(veVmInfoService.vmStatus(vmId));
    }

    /**
     * 挂起虚拟机
     *
     * @param vmId 挂起虚拟机
     * @author wangjiaxing
     * @date 2025/3/19 18:48
     */
    @SaCheckPermission("ve:vmInfo:suspend")
    @PostMapping("/suspend/{vmId}")
    public R<Boolean> suspendVm(@PathVariable Long vmId) {
        return R.ok(veVmInfoService.suspendVm(vmId));
    }

    /**
     * 恢复虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:56
     */
    @SaCheckPermission("ve:vmInfo:resume")
    @PostMapping("/resume/{vmId}")
    public R<Boolean> resumeVm(@PathVariable Long vmId) {
        return R.ok(veVmInfoService.resumeVm(vmId));
    }

    /**
     * 重启虚拟机(正常的重启)
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 19:04
     */
    @SaCheckPermission("ve:vmInfo:reboot")
    @PostMapping("/reboot/{vmId}")
    public R<Boolean> rebootVm(@PathVariable Long vmId) {
        return R.ok(veVmInfoService.rebootVm(vmId));
    }

    /**
     * 重启虚拟机(强制重启)
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 19:04
     */
    @SaCheckPermission("ve:vmInfo:reset")
    @PostMapping("/reset/{vmId}")
    public R<Boolean> resetVm(@PathVariable Long vmId) {
        return R.ok(veVmInfoService.resetVm(vmId));
    }

    /**
     * 创建虚拟机
     *
     * @param bo {@link VeCreateVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 9:46
     */
    @SaCheckPermission("ve:vmInfo:create")
    @PostMapping("/create")
    public R<Boolean> createVm(@RequestBody VeCreateVmBo bo) {
        return R.ok(veVmInfoService.createVm(bo));
    }
}
