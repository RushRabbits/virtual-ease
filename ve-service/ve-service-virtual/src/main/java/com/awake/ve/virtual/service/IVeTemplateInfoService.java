package com.awake.ve.virtual.service;

import com.awake.ve.virtual.domain.vo.VeTemplateListVo;

import java.util.List;

public interface IVeTemplateInfoService {

    /**
     * 获取模板列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 9:28
     */
    List<VeTemplateListVo> templates();
}
