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
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.awake.ve.virtual.domain.vo.VeImageInfoVo;
import com.awake.ve.virtual.domain.bo.VeImageInfoBo;
import com.awake.ve.virtual.service.IVeImageInfoService;

/**
 * 镜像管理
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ve/imageInfo")
public class VeImageInfoController extends BaseController {

    private final IVeImageInfoService veImageInfoService;

    /**
     * 查询镜像管理列表
     */
    @SaCheckPermission("ve:imageInfo:list")
    @GetMapping("/list")
    public TableDataInfo<VeImageInfoVo> list(VeImageInfoBo bo, PageQuery pageQuery) {
        return veImageInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出镜像管理列表
     */
    @SaCheckPermission("ve:imageInfo:export")
    @Log(title = "镜像管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(VeImageInfoBo bo, HttpServletResponse response) {
        List<VeImageInfoVo> list = veImageInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "镜像管理", VeImageInfoVo.class, response);
    }

    /**
     * 获取镜像管理详细信息
     *
     * @param imageId 主键
     */
    @SaCheckPermission("ve:imageInfo:query")
    @GetMapping("/{imageId}")
    public R<VeImageInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                    @PathVariable Long imageId) {
        return R.ok(veImageInfoService.queryById(imageId));
    }

    /**
     * 新增镜像管理
     */
    @SaCheckPermission("ve:imageInfo:add")
    @Log(title = "镜像管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody VeImageInfoBo bo) {
        return toAjax(veImageInfoService.insertByBo(bo));
    }

    /**
     * 修改镜像管理
     */
    @SaCheckPermission("ve:imageInfo:edit")
    @Log(title = "镜像管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody VeImageInfoBo bo) {
        return toAjax(veImageInfoService.updateByBo(bo));
    }

    /**
     * 删除镜像管理
     *
     * @param imageIds 主键串
     */
    @SaCheckPermission("ve:imageInfo:remove")
    @Log(title = "镜像管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{imageIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] imageIds) {
        return toAjax(veImageInfoService.deleteWithValidByIds(List.of(imageIds), true));
    }
}
