package com.awake.ve.system.service.impl;

import com.awake.ve.common.core.utils.MapstructUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.system.domain.SysFileInfo;
import com.awake.ve.system.domain.bo.SysFileInfoBo;
import com.awake.ve.system.domain.vo.SysFileInfoVo;
import com.awake.ve.system.mapper.SysFileInfoMapper;
import com.awake.ve.system.service.ISysFileInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 本地文件管理Service业务层处理
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@RequiredArgsConstructor
@Service
public class SysFileInfoServiceImpl implements ISysFileInfoService {

    private final SysFileInfoMapper baseMapper;

    /**
     * 查询本地文件管理
     *
     * @param fileId 主键
     * @return 本地文件管理
     */
    @Override
    public SysFileInfoVo queryById(Long fileId){
        return baseMapper.selectVoById(fileId);
    }

    /**
     * 分页查询本地文件管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 本地文件管理分页列表
     */
    @Override
    public TableDataInfo<SysFileInfoVo> queryPageList(SysFileInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysFileInfo> lqw = buildQueryWrapper(bo);
        Page<SysFileInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的本地文件管理列表
     *
     * @param bo 查询条件
     * @return 本地文件管理列表
     */
    @Override
    public List<SysFileInfoVo> queryList(SysFileInfoBo bo) {
        LambdaQueryWrapper<SysFileInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysFileInfo> buildQueryWrapper(SysFileInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysFileInfo> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getFileName()), SysFileInfo::getFileName, bo.getFileName());
        lqw.like(StringUtils.isNotBlank(bo.getOriginName()), SysFileInfo::getOriginName, bo.getOriginName());
        lqw.eq(StringUtils.isNotBlank(bo.getFileSuffix()), SysFileInfo::getFileSuffix, bo.getFileSuffix());
        lqw.eq(StringUtils.isNotBlank(bo.getFilePath()), SysFileInfo::getFilePath, bo.getFilePath());
        return lqw;
    }

    /**
     * 新增本地文件管理
     *
     * @param bo 本地文件管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysFileInfoBo bo) {
        SysFileInfo add = MapstructUtils.convert(bo, SysFileInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setFileId(add.getFileId());
        }
        return flag;
    }

    /**
     * 修改本地文件管理
     *
     * @param bo 本地文件管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysFileInfoBo bo) {
        SysFileInfo update = MapstructUtils.convert(bo, SysFileInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysFileInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除本地文件管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
