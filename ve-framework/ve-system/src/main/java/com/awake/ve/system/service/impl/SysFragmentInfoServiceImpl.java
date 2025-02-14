package com.awake.ve.system.service.impl;

import com.awake.ve.common.core.utils.MapstructUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.system.domain.SysFragmentInfo;
import com.awake.ve.system.domain.bo.SysFragmentInfoBo;
import com.awake.ve.system.domain.bo.UploadRequestBo;
import com.awake.ve.system.domain.vo.SysFragmentInfoVo;
import com.awake.ve.system.mapper.SysFragmentInfoMapper;
import com.awake.ve.system.service.ISysFragmentInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 分片Service业务层处理
 *
 * @author wangjiaxing
 * @date 2025-02-14
 */
@RequiredArgsConstructor
@Service
public class SysFragmentInfoServiceImpl implements ISysFragmentInfoService {

    private final SysFragmentInfoMapper baseMapper;

    /**
     * 查询分片
     *
     * @param fragmentId 主键
     * @return 分片
     */
    @Override
    public SysFragmentInfoVo queryById(Long fragmentId) {
        return baseMapper.selectVoById(fragmentId);
    }

    /**
     * 分页查询分片列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 分片分页列表
     */
    @Override
    public TableDataInfo<SysFragmentInfoVo> queryPageList(SysFragmentInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<SysFragmentInfo> lqw = buildQueryWrapper(bo);
        Page<SysFragmentInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的分片列表
     *
     * @param bo 查询条件
     * @return 分片列表
     */
    @Override
    public List<SysFragmentInfoVo> queryList(SysFragmentInfoBo bo) {
        LambdaQueryWrapper<SysFragmentInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<SysFragmentInfo> buildQueryWrapper(SysFragmentInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<SysFragmentInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getFragmentPath()), SysFragmentInfo::getFragmentPath, bo.getFragmentPath());
        lqw.eq(bo.getFragmentStartByte() != null, SysFragmentInfo::getFragmentStartByte, bo.getFragmentStartByte());
        lqw.eq(bo.getFragmentEndByte() != null, SysFragmentInfo::getFragmentEndByte, bo.getFragmentEndByte());
        lqw.eq(bo.getFragmentSize() != null, SysFragmentInfo::getFragmentSize, bo.getFragmentSize());
        lqw.eq(bo.getFragmentNum() != null, SysFragmentInfo::getFragmentNum, bo.getFragmentNum());
        lqw.eq(StringUtils.isNotBlank(bo.getFileHash()), SysFragmentInfo::getFileHash, bo.getFileHash());
        lqw.eq(bo.getFileTotalByte() != null, SysFragmentInfo::getFileTotalByte, bo.getFileTotalByte());
        lqw.eq(StringUtils.isNotBlank(bo.getFileType()), SysFragmentInfo::getFileType, bo.getFileType());
        lqw.eq(StringUtils.isNotBlank(bo.getFileSuffix()), SysFragmentInfo::getFileSuffix, bo.getFileSuffix());
        lqw.eq(bo.getFileTotalFragments() != null, SysFragmentInfo::getFileTotalFragments, bo.getFileTotalFragments());
        return lqw;
    }

    /**
     * 新增分片
     *
     * @param bo 分片
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(SysFragmentInfoBo bo) {
        SysFragmentInfo add = MapstructUtils.convert(bo, SysFragmentInfo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setFragmentId(add.getFragmentId());
        }
        return flag;
    }

    /**
     * 修改分片
     *
     * @param bo 分片
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(SysFragmentInfoBo bo) {
        SysFragmentInfo update = MapstructUtils.convert(bo, SysFragmentInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysFragmentInfo entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除分片信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取分片列表
     *
     * @param bo {@link UploadRequestBo}
     * @return {@link List}<{@link SysFragmentInfo}></>
     * @author wangjiaxing
     * @date 2025/2/14 11:05
     */
    @Override
    public List<SysFragmentInfo> fragmentList(UploadRequestBo bo) {
        LambdaQueryWrapper<SysFragmentInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysFragmentInfo::getFileHash, bo.getHash())
                .eq(SysFragmentInfo::getFileType, bo.getType())
                .orderByDesc(SysFragmentInfo::getFragmentNum);
        return baseMapper.selectList(wrapper).stream()
                .collect(Collectors.groupingBy(
                        SysFragmentInfo::getFragmentNum,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> list.get(0)
                        )
                ))
                .values()
                .stream()
                .sorted(Comparator.comparing(SysFragmentInfo::getFragmentNum))
                .collect(Collectors.toList());
    }

    /**
     * 获取文件切片列表 （切片大小符合上传的大小，即切片上传完整）
     *
     * @author wangjiaxing
     * @date 2025/2/14 11:26
     */
    @Override
    public List<SysFragmentInfo> allFragments(UploadRequestBo bo) {
        LambdaQueryWrapper<SysFragmentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFragmentInfo::getFileHash, bo.getHash())
                .eq(SysFragmentInfo::getFileType, bo.getType())
                .apply("size = CAST(REGEXP_REPLACE(end, '[^0-9.]', '') AS DECIMAL) - CAST(REGEXP_REPLACE(start, '[^0-9.]', '') AS DECIMAL)")
                .orderByAsc(SysFragmentInfo::getFragmentNum);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据md5和type删除分片
     *
     * @param bo {@link UploadRequestBo}
     * @author wangjiaxing
     * @date 2025/2/14 11:40
     */
    @Override
    public Boolean deleteByHashAndType(UploadRequestBo bo) {
        LambdaQueryWrapper<SysFragmentInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysFragmentInfo::getFileHash, bo.getHash()).eq(SysFragmentInfo::getFileType, bo.getType());
        return baseMapper.delete(wrapper) > 0;
    }

    /**
     * 删除文件切片，并且返回删除的列表
     *
     * @param bo {@link UploadRequestBo}
     */
    @Override
    public List<SysFragmentInfo> deletedByHashAndNum(UploadRequestBo bo) {
        LambdaQueryWrapper<SysFragmentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFragmentInfo::getFileHash, bo.getHash())
                .eq(SysFragmentInfo::getFileType, bo.getType())
                .eq(SysFragmentInfo::getFragmentNum, bo.getFragmentNum());
        List<SysFragmentInfo> list = baseMapper.selectList(wrapper);
        baseMapper.delete(wrapper);
        return list;
    }
}
