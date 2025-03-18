package com.awake.ve.virtual.service;


import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.virtual.domain.VeVmListVo;
import com.awake.ve.virtual.domain.bo.VeVmInfoBo;
import com.awake.ve.virtual.domain.vo.VeVmConfigVo;
import com.awake.ve.virtual.domain.vo.VeVmInfoVo;
import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.List;

/**
 * 虚拟机信息Service接口
 *
 * @author 突突兔
 * @date 2025-02-28
 */
public interface IVeVmInfoService {

    /**
     * 查询虚拟机信息
     *
     * @param vmId 主键
     * @return 虚拟机信息
     */
    VeVmInfoVo queryById(Long vmId);

    /**
     * 分页查询虚拟机信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 虚拟机信息分页列表
     */
    TableDataInfo<VeVmInfoVo> queryPageList(VeVmInfoBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的虚拟机信息列表
     *
     * @param bo 查询条件
     * @return 虚拟机信息列表
     */
    List<VeVmInfoVo> queryList(VeVmInfoBo bo);

    /**
     * 新增虚拟机信息
     *
     * @param bo 虚拟机信息
     * @return 是否新增成功
     */
    Boolean insertByBo(VeVmInfoBo bo);

    /**
     * 修改虚拟机信息
     *
     * @param bo 虚拟机信息
     * @return 是否修改成功
     */
    Boolean updateByBo(VeVmInfoBo bo);

    /**
     * 校验并批量删除虚拟机信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

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
}
