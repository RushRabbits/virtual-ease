package com.awake.ve.common.core.service;

import java.util.List;

public interface EcsService {

    /**
     * 获取虚拟机系统所有现存的虚拟机和模板的id集合
     *
     * @author wangjiaxing
     * @date 14:49
     */
    List<Long> existVmAndTemplateIds();
}
