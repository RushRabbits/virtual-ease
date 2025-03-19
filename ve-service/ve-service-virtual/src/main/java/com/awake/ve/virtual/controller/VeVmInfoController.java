package com.awake.ve.virtual.controller;

import java.util.List;

import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.core.validate.AddGroup;
import com.awake.ve.common.core.validate.EditGroup;
import com.awake.ve.common.excel.utils.ExcelUtil;
import com.awake.ve.common.idempotent.annotation.RepeatSubmit;
import com.awake.ve.common.log.annotation.Log;
import com.awake.ve.common.log.enums.BusinessType;
import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.common.web.core.BaseController;
import com.awake.ve.virtual.domain.vo.VeVmListVo;
import com.awake.ve.virtual.domain.bo.VeVmInfoBo;
import com.awake.ve.virtual.domain.vo.VeVmConfigVo;
import com.awake.ve.virtual.domain.vo.VeVmInfoVo;
import com.awake.ve.virtual.domain.vo.VeVmStatusVo;
import com.awake.ve.virtual.service.IVeVmInfoService;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
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
     * 查询虚拟机信息列表
     */
    @SaCheckPermission("virtual:vmInfo:list")
    @GetMapping("/list")
    public TableDataInfo<VeVmInfoVo> list(VeVmInfoBo bo, PageQuery pageQuery) {
        return veVmInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出虚拟机信息列表
     */
    @SaCheckPermission("virtual:vmInfo:export")
    @Log(title = "虚拟机信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(VeVmInfoBo bo, HttpServletResponse response) {
        List<VeVmInfoVo> list = veVmInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "虚拟机信息", VeVmInfoVo.class, response);
    }

    /**
     * 获取虚拟机信息详细信息
     *
     * @param vmId 主键
     */
    @SaCheckPermission("virtual:vmInfo:query")
    @GetMapping("/{vmId}")
    public R<VeVmInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long vmId) {
        return R.ok(veVmInfoService.queryById(vmId));
    }

    /**
     * 新增虚拟机信息
     */
    @SaCheckPermission("virtual:vmInfo:add")
    @Log(title = "虚拟机信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody VeVmInfoBo bo) {
        return toAjax(veVmInfoService.insertByBo(bo));
    }

    /**
     * 修改虚拟机信息
     */
    @SaCheckPermission("virtual:vmInfo:edit")
    @Log(title = "虚拟机信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody VeVmInfoBo bo) {
        return toAjax(veVmInfoService.updateByBo(bo));
    }

    /**
     * 删除虚拟机信息
     *
     * @param vmIds 主键串
     */
    @SaCheckPermission("virtual:vmInfo:remove")
    @Log(title = "虚拟机信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{vmIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] vmIds) {
        return toAjax(veVmInfoService.deleteWithValidByIds(List.of(vmIds), true));
    }

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
}
