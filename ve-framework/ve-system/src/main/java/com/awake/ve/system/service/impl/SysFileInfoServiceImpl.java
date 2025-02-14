package com.awake.ve.system.service.impl;

import cn.hutool.core.text.StrPool;
import com.awake.ve.common.core.constant.HttpStatus;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.MapstructUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.core.utils.file.FileUtils;
import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.system.config.properties.FragmentUploadProperties;
import com.awake.ve.system.domain.SysFileInfo;
import com.awake.ve.system.domain.SysFragmentInfo;
import com.awake.ve.system.domain.bo.SysFileInfoBo;
import com.awake.ve.system.domain.bo.SysFragmentInfoBo;
import com.awake.ve.system.domain.bo.UploadRequestBo;
import com.awake.ve.system.domain.vo.SysFileInfoVo;
import com.awake.ve.system.domain.vo.UploadCheckVo;
import com.awake.ve.system.mapper.SysFileInfoMapper;
import com.awake.ve.system.service.ISysFileInfoService;
import com.awake.ve.system.service.ISysFragmentInfoService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.awake.ve.common.core.utils.DateUtils.getDate;

/**
 * 本地文件管理Service业务层处理
 *
 * @author wangjiaxing
 * @date 2025-02-13
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SysFileInfoServiceImpl implements ISysFileInfoService {

    private final SysFileInfoMapper baseMapper;
    private final FragmentUploadProperties fragmentUploadProperties;
    private final ISysFragmentInfoService fragmentService;
    private final static String PATH_SEPARATOR = File.pathSeparator;

    /**
     * 查询本地文件管理
     *
     * @param fileId 主键
     * @return 本地文件管理
     */
    @Override
    public SysFileInfoVo queryById(Long fileId) {
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
        lqw.like(StringUtils.isNotBlank(bo.getName()), SysFileInfo::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getOriginName()), SysFileInfo::getOriginName, bo.getOriginName());
        lqw.eq(StringUtils.isNotBlank(bo.getSuffix()), SysFileInfo::getSuffix, bo.getSuffix());
        lqw.eq(StringUtils.isNotBlank(bo.getPath()), SysFileInfo::getPath, bo.getPath());
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
    private void validEntityBeforeSave(SysFileInfo entity) {
        // TODO 做一些数据校验,如唯一约束
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
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public UploadCheckVo beforeUpload(UploadRequestBo bo) {
        // 如果文件存在,直接返回文件路径 (秒传)
        SysFileInfo fileInfo = this.fileInfo(bo);
        if (Objects.nonNull(fileInfo) && StringUtils.isNotBlank(fileInfo.getPath()) && new File(fileInfo.getPath()).exists()) {
            return UploadCheckVo.ofCompleted(fragmentUploadProperties.getHost() + fileInfo.getPath());
        }

        // 如果文件不存在 (分片未合并,只上传了一部分)
        List<SysFragmentInfo> fragmentList = fragmentService.fragmentList(bo);
        // 分片序号集合
        List<Long> numList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(fragmentList)) {
            return UploadCheckVo.ofPartial(numList);
        }

        for (SysFragmentInfo fragmentInfo : fragmentList) {
            // 校验切片大小是否完整
            if (!Objects.equals(new File(fragmentInfo.getFragmentPath()).length(), fragmentInfo.getFragmentSize())) {
                log.warn("[SysFileInfoServiceImpl][beforeUpload] 分片大小不匹配,文件hash:{},分片num:{},分片大小:{},实际分片大小:{}",
                        bo.getHash(), fragmentInfo.getFragmentNum(), fragmentInfo.getFragmentSize(), new File(fragmentInfo.getFragmentPath()).length());
                throw new ServiceException("分片大小不匹配", HttpStatus.WARN);
            }
            numList.add(fragmentInfo.getFragmentNum());
        }
        // 返回已上传的分片的位置列表
        return UploadCheckVo.ofPartial(numList);
    }

    /**
     * 获取指定的文件
     *
     * @param bo {@link UploadRequestBo}
     * @return {@link SysFileInfo}
     * @author wangjiaxing
     * @date 2025/2/14 10:44
     */
    @Override
    public SysFileInfo fileInfo(UploadRequestBo bo) {
        LambdaQueryWrapper<SysFileInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysFileInfo::getHash, bo.getHash()).eq(SysFileInfo::getType, bo.getType());
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 删除文件及分片
     *
     * @param bo {@link UploadRequestBo}
     * @author wangjiaxing
     * @date 2025/2/14 11:23
     */
    @Override
    public String deleteFileAndFragments(UploadRequestBo bo) {
        // 获取文件及分片信息
        SysFileInfo fileInfo = this.fileInfo(bo);
        List<SysFragmentInfo> fragments = fragmentService.allFragments(bo);

        // 删除文件
        this.deleteFiles(Collections.singletonList(fileInfo));
        this.deleteFiles(Collections.singletonList(fragments));

        // 删除文件信息
        this.deleteByHashAndType(bo);
        fragmentService.deleteByHashAndType(bo);

        return "文件删除成功";
    }

    /**
     * 根据hash和type删除文件及分片
     *
     * @param bo {@link UploadRequestBo}
     * @author wangjiaxing
     * @date 2025/2/14 11:38
     */
    @Override
    public Boolean deleteByHashAndType(UploadRequestBo bo) {
        LambdaQueryWrapper<SysFileInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysFileInfo::getHash, bo.getHash()).eq(SysFileInfo::getType, bo.getType());
        return baseMapper.delete(wrapper) > 0;
    }

    /**
     * 上传文件分片
     *
     * @param bo {@link SysFragmentInfoBo}
     * @author wangjiaxing
     * @date 2025/2/14 11:51
     */
    @Override
    public Boolean uploadFragment(SysFragmentInfoBo bo) {
        MultipartFile fragmentFile = bo.getFragmentFile();
        if (!Objects.equals(fragmentFile.getSize(), bo.getFragmentSize())) {
            log.warn("[SysFileInfoServiceImpl][uploadFragment] 分片大小不匹配,分片num:{},分片大小:{},实际分片大小:{}", bo.getFragmentNum(), bo.getFragmentSize(), fragmentFile.getSize());
            throw new ServiceException("分片大小不匹配", HttpStatus.WARN);
        }

        String dir = fragmentUploadProperties.getPath() + PATH_SEPARATOR + getDate();

        // 确保目录存在
        FileUtils.mkdir(dir);

        // 保存文件
        String filePath = dir + PATH_SEPARATOR + UUID.randomUUID();
        File fragment = new File(filePath);

        try (InputStream in = fragmentFile.getInputStream(); OutputStream out = new FileOutputStream(fragment)) {
            int byteRead;
            int length = 8192;
            byte[] buffer = new byte[length];
            while ((byteRead = in.read(buffer, 0, length)) != -1) {
                out.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            throw new ServiceException("文件切片保存失败", HttpStatus.WARN);
        }

        // 删除分片
        this.deleteFragment(bo);

        log.info("第 [{}] 文件切片上传成功! hash:{} , type:{}", bo.getFragmentNum(), bo.getFileHash(), bo.getFileType());
        bo.setFragmentPath(filePath);
        bo.setFragmentSize(fragmentFile.getSize());
        return fragmentService.insertByBo(bo);
    }

    /**
     * 合并文件分片
     *
     * @param bo {@link UploadRequestBo}
     * @return {@link String} 合并后的文件存储地址
     * @author wangjiaxing
     * @date 2025/2/14 14:12
     */
    @Override
    public String mergeFragments(UploadRequestBo bo) {
        // 删除已有文件,避免重复上传
        this.deleteFile(bo);

        // 兼容重复上传的文件分片
        Map<Long, String> uploaded = new HashMap<>();

        List<SysFragmentInfo> uploadedFragments = fragmentService.fragmentList(bo);
        if (CollectionUtils.isEmpty(uploadedFragments)) {
            throw new ServiceException("分片合并失败 , 已上传文件不完整", HttpStatus.WARN);
        }
        for (SysFragmentInfo fragment : uploadedFragments) {
            uploaded.put(fragment.getFragmentNum(), fragment.getFragmentPath());
        }
        if (CollectionUtils.isEmpty(uploaded)) {
            throw new ServiceException("分片合并失败 , 已上传文件不完整", HttpStatus.WARN);
        }

        String dir = fragmentUploadProperties.getPath() + PATH_SEPARATOR + getDate();
        // 确保目录存在
        FileUtils.mkdir(dir);

        // 合并分片
        String fileName = UUID.randomUUID().toString();
        String suffix = StrPool.DOT + uploadedFragments.get(0).getFileSuffix();
        String originFileName = fileName + suffix;
        String filePath = dir + PATH_SEPARATOR + originFileName;
        try (OutputStream out = new FileOutputStream(filePath)) {
            for (long i = 0; i < uploaded.size(); i++) {
                File fragment = new File(uploaded.get(i));
                if (!fragment.exists()) {
                    log.warn("[SystemFileInfoServiceImpl][merge] 分片文件:{} 不存在 , path:{}", fragment.getName(), fragment.getPath());
                    throw new ServiceException("分片文件不存在:" + fragment.getPath(), HttpStatus.WARN);
                }

                try (InputStream in = new FileInputStream(fragment)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                } catch (IOException e) {
                    log.error("[SystemFileInfoServiceImpl][merge] 文件合并失败", e);
                    throw new ServiceException("文件合并失败", HttpStatus.WARN);
                }
                FileUtils.del(fragment);
            }
        } catch (Exception e) {
            throw new ServiceException("文件合并失败", HttpStatus.WARN);
        }

        // 删除分片文件记录
        fragmentService.deleteByHashAndType(bo);

        // 记录完整的文件信息
        SysFileInfo fileInfo = new SysFileInfo();
        fileInfo.setHash(bo.getHash());
        fileInfo.setName(fileName);
        fileInfo.setOriginName(originFileName);
        fileInfo.setPath(filePath);
        fileInfo.setSuffix(suffix);
        fileInfo.setType(bo.getType());
        baseMapper.insert(fileInfo);

        return fragmentUploadProperties.getHost() + filePath;
    }

    /**
     * 根据md5和分片序号删除分片
     *
     * @param bo {@link UploadRequestBo}
     * @return {@link List}<{@link SysFragmentInfo}></>
     * @author wangjiaxing
     * @date 2025/2/14 14:03
     */
    @Override
    public Boolean deleteFile(UploadRequestBo bo) {
        SysFileInfo fileInfo = this.fileInfo(bo);
        if (fileInfo == null) {
            return true;
        }
        this.deleteFiles(Collections.singletonList(fileInfo));
        this.deleteByHashAndType(bo);
        return true;
    }

    @Async
    public void deleteFragment(SysFragmentInfoBo bo) {
        UploadRequestBo uploadRequestBo = new UploadRequestBo(bo.getFileHash(), bo.getFileType(), bo.getFragmentNum());
        List<SysFragmentInfo> fragments = fragmentService.deletedByHashAndNum(uploadRequestBo);
        fragments.forEach(item -> {
            try {
                Files.deleteIfExists(Paths.get(item.getFragmentPath()));
            } catch (IOException e) {
                log.error("[SysFileInfoServiceImpl][deleteChunk] 文件删除失败, path: {}", item.getFragmentPath(), e);
            }
        });
    }

    /**
     * 异步删除文件
     *
     * @param objects 要删除的对象列表
     * @return CompletableFuture<Void> 异步操作的Future
     */
    @Async
    public CompletableFuture<Void> deleteFiles(List<Object> objects) {
        return CompletableFuture.runAsync(() -> {
            // 将列表分组处理，提高性能
            objects.parallelStream().forEach(object -> {
                try {
                    deleteFileAsync(object);
                } catch (Exception e) {
                    String path = getFilePath(object);
                    log.error("[SysFileInfoServiceImpl][deleteFiles] 文件删除失败, path: {}", path, e);
                }
            });
        });
    }

    /**
     * 获取文件路径
     */
    private String getFilePath(Object object) {
        if (object instanceof SysFragmentInfo) {
            return ((SysFragmentInfo) object).getFragmentPath();
        } else if (object instanceof SysFileInfo) {
            return ((SysFileInfo) object).getPath();
        }
        return null;
    }

    /**
     * 删除单个文件
     */
    private void deleteFileAsync(Object object) {
        String path = getFilePath(object);
        if (StringUtils.isBlank(path)) {
            log.warn("[SysFileInfoServiceImpl][deleteFile] 无效的文件对象类型: {}", object.getClass());
            return;
        }

        Path filePath = Paths.get(path);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("[SysFileInfoServiceImpl][deleteFile] 文件删除失败, path: {}", path, e);
        }
    }
}
