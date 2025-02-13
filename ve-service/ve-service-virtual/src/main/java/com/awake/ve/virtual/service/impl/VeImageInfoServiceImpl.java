package com.awake.ve.virtual.service.impl;

import com.awake.ve.common.core.utils.MapstructUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.awake.ve.virtual.domain.bo.VeImageInfoBo;
import com.awake.ve.virtual.domain.vo.VeImageInfoVo;
import com.awake.ve.virtual.domain.VeImageInfo;
import com.awake.ve.virtual.mapper.VeImageInfoMapper;
import com.awake.ve.virtual.service.IVeImageInfoService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 镜像管理Service业务层处理
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@RequiredArgsConstructor
@Service
public class VeImageInfoServiceImpl implements IVeImageInfoService {

    private final VeImageInfoMapper baseMapper;

    /**
     * 查询镜像管理
     *
     * @param imageId 主键
     * @return 镜像管理
     */
    @Override
    public VeImageInfoVo queryById(Long imageId){
        return baseMapper.selectVoById(imageId);
    }

    /**
     * 分页查询镜像管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 镜像管理分页列表
     */
    @Override
    public TableDataInfo<VeImageInfoVo> queryPageList(VeImageInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<VeImageInfo> lqw = buildQueryWrapper(bo);
        Page<VeImageInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的镜像管理列表
     *
     * @param bo 查询条件
     * @return 镜像管理列表
     */
    @Override
    public List<VeImageInfoVo> queryList(VeImageInfoBo bo) {
        LambdaQueryWrapper<VeImageInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<VeImageInfo> buildQueryWrapper(VeImageInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<VeImageInfo> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getImageName()), VeImageInfo::getImageName, bo.getImageName());
        lqw.eq(StringUtils.isNotBlank(bo.getImageFormat()), VeImageInfo::getImageFormat, bo.getImageFormat());
        lqw.eq(StringUtils.isNotBlank(bo.getOsType()), VeImageInfo::getOsType, bo.getOsType());
        lqw.eq(bo.getSize() != null, VeImageInfo::getSize, bo.getSize());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), VeImageInfo::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getLocation()), VeImageInfo::getLocation, bo.getLocation());
        return lqw;
    }

    /**
     * 新增镜像管理
     *
     * @param bo 镜像管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(VeImageInfoBo bo) {
        VeImageInfo add = MapstructUtils.convert(bo, VeImageInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setImageId(add.getImageId());
        }
        return flag;
    }

    /**
     * 修改镜像管理
     *
     * @param bo 镜像管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(VeImageInfoBo bo) {
        VeImageInfo update = MapstructUtils.convert(bo, VeImageInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(VeImageInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除镜像管理信息
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
