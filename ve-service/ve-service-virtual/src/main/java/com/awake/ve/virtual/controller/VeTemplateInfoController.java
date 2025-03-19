package com.awake.ve.virtual.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.virtual.domain.vo.VeTemplateListVo;
import com.awake.ve.virtual.service.IVeTemplateInfoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/ve/template")
public class VeTemplateInfoController {

    private final IVeTemplateInfoService veTemplateInfoService;

    /**
     * 获取模板列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 9:28
     */
    @SaCheckPermission("ve:template:list")
    @GetMapping("/list")
    public R<List<VeTemplateListVo>> templates() {
        return R.ok(veTemplateInfoService.templates());
    }

    /**
     * 根据虚拟机创建模板
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 14:38
     */
    @SaCheckPermission("ve:template:createTemplate")
    @GetMapping("/createTemplate/{vmId}")
    public R<Boolean> createTemplate(@NotNull(message = "虚拟机不能为空") @PathVariable Long vmId) {
        return R.ok(veTemplateInfoService.createTemplate(vmId));
    }
}
