package com.awake.ve.virtual.service.impl;

import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.core.constant.CacheNames;
import com.awake.ve.common.core.constant.HttpStatus;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.service.EcsService;
import com.awake.ve.common.core.utils.MapstructUtils;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ecs.api.template.request.PVECreateTemplateApiRequest;
import com.awake.ve.common.ecs.api.template.request.PVETemplateCreateVmApiRequest;
import com.awake.ve.common.ecs.api.template.response.PVECreateTemplateApiResponse;
import com.awake.ve.common.ecs.api.template.response.PVETemplateCreateVmApiResponse;
import com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiRequest;
import com.awake.ve.common.ecs.api.vm.status.PVENodeVmListApiResponse;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.core.EcsClient;
import com.awake.ve.common.ecs.domain.vm.PveVmInfo;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.virtual.domain.bo.VeCloneVmByTemplateBo;
import com.awake.ve.virtual.domain.vo.VeTemplateListVo;
import com.awake.ve.virtual.service.IVeTemplateInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class VeTemplateInfoServiceImpl implements IVeTemplateInfoService {

    private final EcsService ecsService;

    private final EcsClient ecsClient;

    private final EcsProperties ecsProperties = SpringUtils.getBean(EcsProperties.class);

    /**
     * 获取模板列表
     *
     * @author wangjiaxing
     * @date 2025/3/19 9:28
     */
    @Override
    public List<VeTemplateListVo> templates() {
        // api参数
        PVENodeVmListApiRequest request = PVENodeVmListApiRequest.builder().node(ecsProperties.getNode()).full(0).build();

        // api结果
        PVENodeVmListApiResponse response = (PVENodeVmListApiResponse) ecsClient.vmList(request);
        List<PveVmInfo> vmList = response.getVmList();
        vmList = vmList.stream().filter(PveVmInfo::getTemplate).sorted(Comparator.comparing(PveVmInfo::getVmId)).toList();

        return MapstructUtils.convert(vmList, VeTemplateListVo.class);
    }

    /**
     * 根据虚拟机创建模板
     *
     * @param vmId 虚拟机id
     * @author wangjiaxing
     * @date 2025/3/19 14:38
     */
    @Override
    public Boolean createTemplate(Long vmId) {
        // api参数
        PVECreateTemplateApiRequest request = PVECreateTemplateApiRequest.builder().node(ecsProperties.getNode()).vmId(vmId).build();

        // api响应
        PVECreateTemplateApiResponse response = (PVECreateTemplateApiResponse) ecsClient.createTemplate(request);

        return StringUtils.isNotBlank(response.getData());
    }

    /**
     * 根据模板clone虚拟机
     *
     * @param bo {@link VeCloneVmByTemplateBo}
     * @author wangjiaxing
     * @date 2025/3/19 15:25
     */
    @Override
    public Boolean cloneVmByTemplate(VeCloneVmByTemplateBo bo) {
        // 判断目标vmId是否存在
        List<Long> existIds = RedisUtils.getCacheList(CacheConstants.EXIST_VM_TEMPLATE_ID + CacheNames.PVE_EXIST_VM_TEMPLATE);
        if (existIds.contains(bo.getNewId())) {
            log.warn("[VeTemplateInfoServiceImpl][cloneVmByTemplate] 目标虚拟机id已存在");
            throw new ServiceException("目标虚拟机id已存在", HttpStatus.WARN);
        }

        // api参数
        PVETemplateCreateVmApiRequest request = PVETemplateCreateVmApiRequest.builder().node(ecsProperties.getNode()).vmId(bo.getTemplateId()).newId(bo.getNewId()).full(bo.getFull()).build();

        // api响应
        PVETemplateCreateVmApiResponse response = (PVETemplateCreateVmApiResponse) ecsClient.templateCloneVm(request);

        // 清除原有id缓存
        RedisUtils.deleteObject(CacheConstants.EXIST_VM_TEMPLATE_ID + CacheNames.PVE_EXIST_VM_TEMPLATE);
        RedisUtils.setCacheList(CacheConstants.EXIST_VM_TEMPLATE_ID + CacheNames.PVE_EXIST_VM_TEMPLATE, ecsService.existVmAndTemplateIds());
        return StringUtils.isNotBlank(response.getData());
    }
}
