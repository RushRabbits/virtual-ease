package com.awake.ve.virtual.service;

import com.awake.ve.virtual.domain.bo.VeCloneVmByTemplateBo;
import com.awake.ve.virtual.domain.vo.VeTemplateListVo;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface IVeTemplateInfoService {

    /**
     * 获取模板列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 9:28
     */
    List<VeTemplateListVo> templates();

    /**
     * 根据虚拟机创建模板
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 14:38
     */
    Boolean createTemplate(@NotNull(message = "虚拟机不能为空") Long vmId);

    /**
     * 根据模板clone虚拟机
     *
     * @param bo {@link VeCloneVmByTemplateBo}
     * @author wangjiaxing
     * @date 2025/3/19 15:25
     */
    Boolean cloneVmByTemplate(VeCloneVmByTemplateBo bo);
}
