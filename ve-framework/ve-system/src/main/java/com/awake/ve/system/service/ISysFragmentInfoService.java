package com.awake.ve.system.service;

import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.system.domain.SysFragmentInfo;
import com.awake.ve.system.domain.bo.SysFragmentInfoBo;
import com.awake.ve.system.domain.bo.UploadRequestBo;
import com.awake.ve.system.domain.vo.SysFragmentInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;

/**
 * 分片Service接口
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
public interface ISysFragmentInfoService extends IService<SysFragmentInfo> {

    /**
     * 查询分片
     *
     * @param fragmentId 主键
     * @return 分片
     */
    SysFragmentInfoVo queryById(Long fragmentId);

    /**
     * 分页查询分片列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 分片分页列表
     */
    TableDataInfo<SysFragmentInfoVo> queryPageList(SysFragmentInfoBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的分片列表
     *
     * @param bo 查询条件
     * @return 分片列表
     */
    List<SysFragmentInfoVo> queryList(SysFragmentInfoBo bo);

    /**
     * 新增分片
     *
     * @param bo 分片
     * @return 是否新增成功
     */
    Boolean insertByBo(SysFragmentInfoBo bo);

    /**
     * 修改分片
     *
     * @param bo 分片
     * @return 是否修改成功
     */
    Boolean updateByBo(SysFragmentInfoBo bo);

    /**
     * 校验并批量删除分片信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 获取分片列表
     *
     * @param bo {@link UploadRequestBo}
     * @return {@link List}<{@link SysFragmentInfo}></>
     * @author wangjiaxing
     * @date 2025/2/14 11:05
     */
    List<SysFragmentInfo> fragmentList(UploadRequestBo bo);

    /**
     * 查询所有的文件分片
     *
     * @author wangjiaxing
     * @date 2025/2/14 11:26
     */
    List<SysFragmentInfo> allFragments(UploadRequestBo bo);

    /**
     * 根据md5和type删除分片
     *
     * @param bo {@link UploadRequestBo}
     * @author wangjiaxing
     * @date 2025/2/14 11:40
     */
    Boolean deleteByHashAndType(UploadRequestBo bo);

    /**
     * 根据md5和分片序号删除分片
     *
     * @param uploadRequestBo {@link UploadRequestBo}
     * @return {@link List}<{@link SysFragmentInfo}></>
     * @author wangjiaxing
     * @date 2025/2/14 14:03
     */
    List<SysFragmentInfo> deletedByHashAndNum(UploadRequestBo uploadRequestBo);
}
