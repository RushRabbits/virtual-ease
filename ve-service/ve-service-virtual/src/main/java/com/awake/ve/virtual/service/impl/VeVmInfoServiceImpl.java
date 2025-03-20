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
import com.awake.ve.common.ecs.core.EcsClient;
import com.awake.ve.common.ecs.domain.vm.PveVmInfo;
import com.awake.ve.common.ecs.utils.EcsUtils;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.virtual.domain.bo.VeCreateOrEditVmBo;
import com.awake.ve.virtual.domain.bo.VeShutdownOrStopVmBo;
import com.awake.ve.virtual.domain.vo.VeVmListVo;
import com.awake.ve.virtual.domain.vo.VeVmConfigVo;
import com.awake.ve.virtual.domain.vo.VeVmStatusVo;
import com.awake.ve.virtual.mapper.VeVmInfoMapper;
import com.awake.ve.virtual.service.IVeVmInfoService;
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

    private final EcsClient ecsClient;

    /**
     * 查询指定节点下的虚拟机列表
     *
     * @author wangjiaxing
     * @date 2025/3/17 16:57
     */
    @Override
    public List<VeVmListVo> vmList() {
        // api参数
        PVENodeVmListApiRequest request = PVENodeVmListApiRequest.builder().node(ecsProperties.getNode()).full(0).build();

        // api结果
        PVENodeVmListApiResponse response = (PVENodeVmListApiResponse) ecsClient.vmList(request);
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
        // api参数
        PVEGetVmConfigApiRequest request = PVEGetVmConfigApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).current(true).build();

        // api响应
        PVEGetVmConfigApiResponse response = (PVEGetVmConfigApiResponse) ecsClient.getVmConfig(request);
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
        // api参数
        PVEDestroyVmApiRequest request = PVEDestroyVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).destroyUnreferencedDisks(true).skipLock(true).purge(true).build();

        // api响应
        PVEDestroyVmApiResponse response = (PVEDestroyVmApiResponse) ecsClient.destroyVm(request);
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
        // api参数
        PVEStartVmApiRequest request = PVEStartVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).build();

        // api响应
        PVEStartVmApiResponse response = (PVEStartVmApiResponse) ecsClient.startVm(request);

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
        // api参数
        PVEVmStatusApiRequest request = PVEVmStatusApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).build();

        // api响应
        PVEVmStatusApiResponse response = (PVEVmStatusApiResponse) ecsClient.vmStatus(request);
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
        // api参数
        PVESuspendVmApiRequest request = PVESuspendVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).skipLock(true).toDisk(true).build();

        // api响应
        PVESuspendVmApiResponse response = (PVESuspendVmApiResponse) ecsClient.suspendVm(request);
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
        // api参数
        PVEResumeVmApiRequest request = PVEResumeVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).skipLock(true).build();

        // api响应
        PVEResumeVmApiResponse response = (PVEResumeVmApiResponse) ecsClient.resumeVm(request);

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
        // api参数
        PVERebootVmApiRequest request = PVERebootVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).timeout(10).build();

        // api响应
        PVERebootApiResponse response = (PVERebootApiResponse) ecsClient.rebootVm(request);

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
        // api参数
        PVEResetVmApiRequest request = PVEResetVmApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).build();

        // api响应
        PVEResetVmApiResponse response = (PVEResetVmApiResponse) ecsClient.resetVm(request);

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
        PVECreateOrRestoreVmApiResponse response = (PVECreateOrRestoreVmApiResponse) ecsClient.createVm(request);

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
        PVEPostVmConfigApiResponse response = (PVEPostVmConfigApiResponse) ecsClient.editVmConfigAsync(request);

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
        PVEPutVmConfigApiResponse response = (PVEPutVmConfigApiResponse) ecsClient.editVmConfigSync(request);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 关闭虚拟机(正常关闭)
     *
     * @param bo {@link VeShutdownOrStopVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 12:08
     */
    @Override
    public Boolean shutdown(VeShutdownOrStopVmBo bo) {
        // api参数
        PVEShutdownVmApiRequest request = PVEShutdownVmApiRequest.builder()
                .node(ecsProperties.getNode())
                .vmId(bo.getVmId())
                .skipLock(bo.getSkipLock())
                .timeout(bo.getTimeout())
                .forceStop(bo.getForceStop())
                .build();

        // api响应
        PVEShutdownVmApiResponse response = (PVEShutdownVmApiResponse) ecsClient.shutdownVm(request);
        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 关闭虚拟机(强制关闭)
     *
     * @param bo {@link VeShutdownOrStopVmBo}
     * @author wangjiaxing
     * @date 2025/3/20 12:08
     */
    @Override
    public Boolean stop(VeShutdownOrStopVmBo bo) {
        // api参数
        PVEStopVmApiRequest request = PVEStopVmApiRequest.builder()
                .node(ecsProperties.getNode())
                .vmId(bo.getVmId())
                .skipLock(bo.getSkipLock())
                .timeout(bo.getTimeout())
                .build();

        // api响应
        PVEStopVmApiResponse response = (PVEStopVmApiResponse) ecsClient.stopVm(request);
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
