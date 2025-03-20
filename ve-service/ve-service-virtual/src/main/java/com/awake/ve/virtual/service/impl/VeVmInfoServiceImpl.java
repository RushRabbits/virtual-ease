package com.awake.ve.virtual.service.impl;

import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.core.constant.CacheNames;
import com.awake.ve.common.core.constant.HttpStatus;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.MapstructUtils;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ecs.api.vm.config.*;
import com.awake.ve.common.ecs.api.vm.status.*;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.domain.vm.PveVmInfo;
import com.awake.ve.common.ecs.enums.api.PVEApi;
import com.awake.ve.common.ecs.handler.ApiHandler;
import com.awake.ve.common.ecs.utils.EcsUtils;
import com.awake.ve.common.mybatis.core.page.PageQuery;
import com.awake.ve.common.mybatis.core.page.TableDataInfo;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.virtual.domain.VeVmInfo;
import com.awake.ve.virtual.domain.bo.VeCreateOrEditVmBo;
import com.awake.ve.virtual.domain.vo.VeVmListVo;
import com.awake.ve.virtual.domain.bo.VeVmInfoBo;
import com.awake.ve.virtual.domain.vo.VeVmConfigVo;
import com.awake.ve.virtual.domain.vo.VeVmInfoVo;
import com.awake.ve.virtual.domain.vo.VeVmStatusVo;
import com.awake.ve.virtual.mapper.VeVmInfoMapper;
import com.awake.ve.virtual.service.IVeVmInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 虚拟机信息Service业务层处理
 *
 * @author 突突兔
 * @date 2025-02-28
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class VeVmInfoServiceImpl implements IVeVmInfoService {

    private final VeVmInfoMapper baseMapper;

    private final EcsProperties ecsProperties = SpringUtils.getBean(EcsProperties.class);

    /**
     * 查询虚拟机信息
     *
     * @param vmId 主键
     * @return 虚拟机信息
     */
    @Override
    public VeVmInfoVo queryById(Long vmId) {
        return baseMapper.selectVoById(vmId);
    }

    /**
     * 分页查询虚拟机信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 虚拟机信息分页列表
     */
    @Override
    public TableDataInfo<VeVmInfoVo> queryPageList(VeVmInfoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<VeVmInfo> lqw = buildQueryWrapper(bo);
        Page<VeVmInfoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的虚拟机信息列表
     *
     * @param bo 查询条件
     * @return 虚拟机信息列表
     */
    @Override
    public List<VeVmInfoVo> queryList(VeVmInfoBo bo) {
        LambdaQueryWrapper<VeVmInfo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<VeVmInfo> buildQueryWrapper(VeVmInfoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<VeVmInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getVmId() != null, VeVmInfo::getVmId, bo.getVmId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), VeVmInfo::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getCpu()), VeVmInfo::getCpu, bo.getCpu());
        lqw.eq(StringUtils.isNotBlank(bo.getOsType()), VeVmInfo::getOsType, bo.getOsType());
        lqw.eq(StringUtils.isNotBlank(bo.getBios()), VeVmInfo::getBios, bo.getBios());
        return lqw;
    }

    /**
     * 新增虚拟机信息
     *
     * @param bo 虚拟机信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(VeVmInfoBo bo) {
        PVECreateOrRestoreVmApiRequest request = PVECreateOrRestoreVmApiRequest.builder()
                .node(ecsProperties.getNode())
                .vmId(bo.getVmId())
                .name(bo.getName())
                .memory(Double.parseDouble(bo.getMemory().toString()))
                .boot(bo.getBoot())
                .ciUser(bo.getCiUser())
                .ciPassword(bo.getCiPassword())
                .ciUpgrade(bo.getCiUpgrade())
                .cpu(bo.getCpu())
                .sockets(Integer.parseInt(bo.getSockets().toString()))
                .cores(Integer.parseInt(bo.getCores().toString()))
                .vga(bo.getVga())
                .agent(bo.getAgent())
                .osType(bo.getOsType())
                .scsiHw(bo.getScsiHw())
                .scsi(Collections.singletonList(bo.getScsi()))
                .ide(Collections.singletonList(bo.getIde()))
                .ipConfig(Collections.singletonList(bo.getIpConfig()))
                .net(Collections.singletonList(bo.getNet()))
                .bios(bo.getBios())
                .build();

        PVECreateOrRestoreVmApiResponse response = (PVECreateOrRestoreVmApiResponse) PVEApi.CREATE_OR_RESTORE_VM.handle(request);
        return StringUtils.isNotBlank(response.getData());

        // VeVmInfo add = MapstructUtils.convert(bo, VeVmInfo.class);
        // validEntityBeforeSave(add);
        // boolean flag = baseMapper.insert(add) > 0;
        // if (flag) {
        //     bo.setVmId(add.getVmId());
        // }
        // return flag;
    }

    /**
     * 修改虚拟机信息
     *
     * @param bo 虚拟机信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(VeVmInfoBo bo) {
        VeVmInfo update = MapstructUtils.convert(bo, VeVmInfo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(VeVmInfo entity) {
        // TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除虚拟机信息信息
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
     * 查询指定节点下的虚拟机列表
     *
     * @author wangjiaxing
     * @date 2025/3/17 16:57
     */
    @Override
    public List<VeVmListVo> vmList() {
        ApiHandler apiHandler = PVEApi.NODE_VM_LIST.getApiHandler();

        // api参数
        PVENodeVmListApiRequest request = new PVENodeVmListApiRequest();
        request.setNode(ecsProperties.getNode());
        request.setFull(0);

        // api结果
        PVENodeVmListApiResponse response = (PVENodeVmListApiResponse) apiHandler.handle(request);
        List<PveVmInfo> vmList = response.getVmList();
        vmList = vmList.stream().filter(pveVmInfo -> !pveVmInfo.getTemplate()).sorted(Comparator.comparing(PveVmInfo::getVmId)).toList();

        return MapstructUtils.convert(vmList, VeVmListVo.class);
    }

    /**
     * 获取虚拟机配置信息
     *
     * @param vmId 虚拟机id
     * @return {@link VeVmConfigVo}
     * @author wangjiaxing
     * @date 2025/3/18 14:08
     */
    @Override
    public VeVmConfigVo getVmConfig(Long vmId) {
        ApiHandler apiHandler = PVEApi.GET_VM_CONFIG.getApiHandler();

        PVEGetVmConfigApiRequest request = new PVEGetVmConfigApiRequest();
        request.setNode(ecsProperties.getNode());
        request.setVmId(vmId);
        request.setCurrent(true);

        PVEGetVmConfigApiResponse response = (PVEGetVmConfigApiResponse) apiHandler.handle(request);
        return MapstructUtils.convert(response, VeVmConfigVo.class);
    }

    /**
     * 销毁指定虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 17:52
     */
    @Override
    public Boolean destroyVm(Long vmId) {
        ApiHandler apiHandler = PVEApi.DESTROY_VM.getApiHandler();

        // api参数
        PVEDestroyVmApiRequest request = PVEDestroyVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).destroyUnreferencedDisks(true).skipLock(true).purge(true).build();

        // api响应
        PVEDestroyVmApiResponse response = (PVEDestroyVmApiResponse) apiHandler.handle(request);
        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 启动虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:06
     */
    @Override
    public Boolean startVm(Long vmId) {
        ApiHandler apiHandler = PVEApi.START_VM.getApiHandler();

        // api参数
        PVEStartVmApiRequest request = PVEStartVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).build();

        // api响应
        PVEStartVmApiResponse response = (PVEStartVmApiResponse) apiHandler.handle(request);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 获取虚拟机状态
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:28
     */
    @Override
    public VeVmStatusVo vmStatus(Long vmId) {
        ApiHandler apiHandler = PVEApi.VM_STATUS.getApiHandler();

        // api参数
        PVEVmStatusApiRequest request = PVEVmStatusApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).build();

        // api响应
        PVEVmStatusApiResponse response = (PVEVmStatusApiResponse) apiHandler.handle(request);
        return MapstructUtils.convert(response, VeVmStatusVo.class);
    }

    /**
     * 挂起虚拟机
     *
     * @param vmId 挂起虚拟机
     * @author wangjiaxing
     * @date 2025/3/19 18:48
     */
    @Override
    public Boolean suspendVm(Long vmId) {
        ApiHandler apiHandler = PVEApi.SUSPEND_VM.getApiHandler();

        // api参数
        PVESuspendVmApiRequest request = PVESuspendVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).skipLock(true).toDisk(true).build();

        // api响应
        PVESuspendVmApiResponse response = (PVESuspendVmApiResponse) apiHandler.handle(request);
        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 恢复虚拟机
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 18:56
     */
    @Override
    public Boolean resumeVm(Long vmId) {
        ApiHandler apiHandler = PVEApi.RESUME_VM.getApiHandler();

        // api参数
        PVEResumeVmApiRequest request = PVEResumeVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).skipLock(true).build();

        // api响应
        PVEResumeVmApiResponse response = (PVEResumeVmApiResponse) apiHandler.handle(request);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 重启虚拟机(正常的重启)
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 19:04
     */
    @Override
    public Boolean rebootVm(Long vmId) {
        ApiHandler apiHandler = PVEApi.REBOOT_VM.getApiHandler();

        // api参数
        PVERebootVmApiRequest request = PVERebootVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).timeout(10).build();

        // api响应
        PVERebootApiResponse response = (PVERebootApiResponse) apiHandler.handle(request);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 重启虚拟机(强制重启)
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 19:04
     */
    @Override
    public Boolean resetVm(Long vmId) {
        ApiHandler apiHandler = PVEApi.RESET_VM.getApiHandler();

        // api参数
        PVEResetVmApiRequest request = PVEResetVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).build();

        // api响应
        PVEResetVmApiResponse response = (PVEResetVmApiResponse) apiHandler.handle(request);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 创建虚拟机
     *
     * @param bo {@link VeCreateOrEditVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 9:46
     */
    @Override
    public Boolean createVm(VeCreateOrEditVmBo bo) {
        // 校验vmId是否存在
        List<Long> idCache = existsVmIds(bo);
        if (idCache.contains(bo.getVmId())) {
            log.warn("[VeVmInfoServiceImpl][createVm] 目标虚拟机id已存在");
            throw new ServiceException("目标虚拟机id已存在", HttpStatus.WARN);
        }

        ApiHandler apiHandler = PVEApi.CREATE_OR_RESTORE_VM.getApiHandler();

        // api参数
        PVECreateOrRestoreVmApiRequest request = PVECreateOrRestoreVmApiRequest.builder()
                .node(ecsProperties.getNode())
                .vmId(bo.getVmId())
                .ipConfig(EcsUtils.ipConfig2String(bo.getIpConfig()))
                .memory(bo.getMemory())
                .boot(bo.getBoot().getBoot())
                .ciUser(bo.getCiUser())
                .ciPassword(bo.getCiPassword())
                .ciUpgrade(bo.getCiUpgrade())
                .sockets(bo.getSockets())
                .cores(bo.getCores())
                .vga(bo.getVga())
                .agent(EcsUtils.agent2String(bo.getAgent()))
                .cpu(bo.getCpu())
                .osType(bo.getOsType())
                .scsiHw(bo.getScsiHw())
                .net(EcsUtils.net2String(bo.getNet()))
                .scsi(EcsUtils.scsi2String(bo.getScsi()))
                .ide(EcsUtils.ide2String(bo.getIde()))
                .bios(bo.getBios())
                .build();

        // api响应
        PVECreateOrRestoreVmApiResponse response = (PVECreateOrRestoreVmApiResponse) apiHandler.handle(request);

        // 添加新的虚拟机id
        idCache.add(bo.getVmId());
        refreshExistsVmIds(idCache);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 异步修改虚拟机配置
     *
     * @param bo {@link VeCreateOrEditVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 11:37
     */
    @Override
    public Boolean editAsync(VeCreateOrEditVmBo bo) {
        // 校验vmId是否存在
        List<Long> idCache = existsVmIds(bo);
        if (idCache.contains(bo.getVmId())) {
            log.warn("[VeVmInfoServiceImpl][createVm] 目标虚拟机id已存在");
            throw new ServiceException("目标虚拟机id已存在", HttpStatus.WARN);
        }

        ApiHandler apiHandler = PVEApi.POST_VM_CONFIG.getApiHandler();

        // api参数
        PVEPostVmConfigApiRequest request = PVEPostVmConfigApiRequest.builder()
                .node(ecsProperties.getNode())
                .vmId(bo.getVmId())
                .ipConfig(EcsUtils.ipConfig2String(bo.getIpConfig()))
                .memory(bo.getMemory())
                .boot(bo.getBoot().getBoot())
                .ciUser(bo.getCiUser())
                .ciPassword(bo.getCiPassword())
                .ciUpgrade(bo.getCiUpgrade())
                .sockets(bo.getSockets())
                .cores(bo.getCores())
                .vga(bo.getVga())
                .agent(EcsUtils.agent2String(bo.getAgent()))
                .cpu(bo.getCpu())
                .osType(bo.getOsType())
                .scsiHw(bo.getScsiHw())
                .net(EcsUtils.net2String(bo.getNet()))
                .scsi(EcsUtils.scsi2String(bo.getScsi()))
                .ide(EcsUtils.ide2String(bo.getIde()))
                .bios(bo.getBios())
                .build();

        // api响应
        PVEPostVmConfigApiResponse response = (PVEPostVmConfigApiResponse) apiHandler.handle(request);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 同步修改虚拟机配置
     *
     * @param bo {@link VeCreateOrEditVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 11:37
     */
    @Override
    public Boolean editSync(VeCreateOrEditVmBo bo) {
        // 校验vmId是否存在
        List<Long> idCache = existsVmIds(bo);
        if (idCache.contains(bo.getVmId())) {
            log.warn("[VeVmInfoServiceImpl][createVm] 目标虚拟机id已存在");
            throw new ServiceException("目标虚拟机id已存在", HttpStatus.WARN);
        }

        ApiHandler apiHandler = PVEApi.PUT_VM_CONFIG.getApiHandler();

        // api参数
        PVEPutVmConfigApiRequest request = PVEPutVmConfigApiRequest.builder()
                .node(ecsProperties.getNode())
                .vmId(bo.getVmId())
                .ipConfig(EcsUtils.ipConfig2String(bo.getIpConfig()))
                .memory(bo.getMemory())
                .boot(bo.getBoot().getBoot())
                .ciUser(bo.getCiUser())
                .ciPassword(bo.getCiPassword())
                .ciUpgrade(bo.getCiUpgrade())
                .sockets(bo.getSockets())
                .cores(bo.getCores())
                .vga(bo.getVga())
                .agent(EcsUtils.agent2String(bo.getAgent()))
                .cpu(bo.getCpu())
                .osType(bo.getOsType())
                .scsiHw(bo.getScsiHw())
                .net(EcsUtils.net2String(bo.getNet()))
                .scsi(EcsUtils.scsi2String(bo.getScsi()))
                .ide(EcsUtils.ide2String(bo.getIde()))
                .bios(bo.getBios())
                .build();

        // api响应
        PVEPutVmConfigApiResponse response = (PVEPutVmConfigApiResponse) apiHandler.handle(request);

        return StringUtils.isNotBlank(response.getData());
    }

    private void refreshExistsVmIds(List<Long> idCache) {
        RedisUtils.deleteObject(existVmCacheKey());
        RedisUtils.setCacheList(existVmCacheKey(), idCache);
    }

    private List<Long> existsVmIds(VeCreateOrEditVmBo bo) {
        return RedisUtils.getCacheList(existVmCacheKey());
    }

    private String existVmCacheKey() {
        return CacheConstants.EXIST_VM_TEMPLATE_ID + CacheNames.PVE_EXIST_VM_TEMPLATE;
    }
}
