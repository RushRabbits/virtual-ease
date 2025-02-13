package com.awake.ve.system.controller.system;

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
import com.awake.ve.system.domain.bo.SysFileInfoBo;
import com.awake.ve.system.domain.vo.SysFileInfoVo;
import com.awake.ve.system.service.ISysFileInfoService;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

/**
 * 本地文件管理
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/file/fileInfo")
public class SysFileInfoController extends BaseController {

    private final ISysFileInfoService sysFileInfoService;

    /**
     * 查询本地文件管理列表
     */
    @SaCheckPermission("file:fileInfo:list")
    @GetMapping("/list")
    public TableDataInfo<SysFileInfoVo> list(SysFileInfoBo bo, PageQuery pageQuery) {
        return sysFileInfoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出本地文件管理列表
     */
    @SaCheckPermission("file:fileInfo:export")
    @Log(title = "本地文件管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysFileInfoBo bo, HttpServletResponse response) {
        List<SysFileInfoVo> list = sysFileInfoService.queryList(bo);
        ExcelUtil.exportExcel(list, "本地文件管理", SysFileInfoVo.class, response);
    }

    /**
     * 获取本地文件管理详细信息
     *
     * @param fileId 主键
     */
    @SaCheckPermission("file:fileInfo:query")
    @GetMapping("/{fileId}")
    public R<SysFileInfoVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long fileId) {
        return R.ok(sysFileInfoService.queryById(fileId));
    }

    /**
     * 新增本地文件管理
     */
    @SaCheckPermission("file:fileInfo:add")
    @Log(title = "本地文件管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysFileInfoBo bo) {
        return toAjax(sysFileInfoService.insertByBo(bo));
    }

    /**
     * 修改本地文件管理
     */
    @SaCheckPermission("file:fileInfo:edit")
    @Log(title = "本地文件管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysFileInfoBo bo) {
        return toAjax(sysFileInfoService.updateByBo(bo));
    }

    /**
     * 删除本地文件管理
     *
     * @param fileIds 主键串
     */
    @SaCheckPermission("file:fileInfo:remove")
    @Log(title = "本地文件管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] fileIds) {
        return toAjax(sysFileInfoService.deleteWithValidByIds(List.of(fileIds), true));
    }
}
