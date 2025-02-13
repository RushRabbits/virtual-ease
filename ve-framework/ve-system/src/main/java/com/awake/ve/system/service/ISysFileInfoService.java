package com.awake.ve.system.service;

import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.system.domain.bo.SysFileInfoBo;
import com.awake.ve.system.domain.vo.SysFileInfoVo;

import java.util.Collection;
import java.util.List;

/**
 * 本地文件管理Service接口
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
public interface ISysFileInfoService {

    /**
     * 查询本地文件管理
     *
     * @param fileId 主键
     * @return 本地文件管理
     */
    SysFileInfoVo queryById(Long fileId);

    /**
     * 分页查询本地文件管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 本地文件管理分页列表
     */
    TableDataInfo<SysFileInfoVo> queryPageList(SysFileInfoBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的本地文件管理列表
     *
     * @param bo 查询条件
     * @return 本地文件管理列表
     */
    List<SysFileInfoVo> queryList(SysFileInfoBo bo);

    /**
     * 新增本地文件管理
     *
     * @param bo 本地文件管理
     * @return 是否新增成功
     */
    Boolean insertByBo(SysFileInfoBo bo);

    /**
     * 修改本地文件管理
     *
     * @param bo 本地文件管理
     * @return 是否修改成功
     */
    Boolean updateByBo(SysFileInfoBo bo);

    /**
     * 校验并批量删除本地文件管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
