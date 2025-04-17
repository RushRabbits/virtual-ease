package com.awake.ve.virtual.service;

import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.virtual.domain.vo.VeImageInfoVo;
import com.awake.ve.virtual.domain.bo.VeImageInfoBo;

import java.util.Collection;
import java.util.List;

/**
 * 镜像管理Service接口
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
public interface IVeImageInfoService {

    /**
     * 查询镜像管理
     *
     * @param imageId 主键
     * @return 镜像管理
     */
    VeImageInfoVo queryById(Long imageId);

    /**
     * 分页查询镜像管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 镜像管理分页列表
     */
    TableDataInfo<VeImageInfoVo> queryPageList(VeImageInfoBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的镜像管理列表
     *
     * @param bo 查询条件
     * @return 镜像管理列表
     */
    List<VeImageInfoVo> queryList(VeImageInfoBo bo);

    /**
     * 新增镜像管理
     *
     * @param bo 镜像管理
     * @return 是否新增成功
     */
    Boolean insertByBo(VeImageInfoBo bo);

    /**
     * 修改镜像管理
     *
     * @param bo 镜像管理
     * @return 是否修改成功
     */
    Boolean updateByBo(VeImageInfoBo bo);

    /**
     * 校验并批量删除镜像管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
