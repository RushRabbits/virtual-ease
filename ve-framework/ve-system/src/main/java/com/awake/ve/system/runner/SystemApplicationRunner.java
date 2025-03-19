package com.awake.ve.system.runner;

import com.awake.ve.common.core.constant.CacheConstants;
import com.awake.ve.common.core.constant.CacheNames;
import com.awake.ve.common.core.service.EcsService;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.awake.ve.system.service.ISysOssConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 初始化 system 模块对应业务数据
 *
 * @author Lion Li
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SystemApplicationRunner implements ApplicationRunner {

    private final ISysOssConfigService ossConfigService;
    private final EcsService ecsService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化oss配置
        ossConfigService.init();
        log.info("初始化OSS配置成功");

        // 获取虚拟机系统中所有的虚拟机和模板
        List<Long> existIds = ecsService.existVmAndTemplateIds();
        RedisUtils.deleteObject(CacheConstants.EXIST_VM_TEMPLATE_ID + CacheNames.PVE_EXIST_VM_TEMPLATE);
        RedisUtils.setCacheList(CacheConstants.EXIST_VM_TEMPLATE_ID + CacheNames.PVE_EXIST_VM_TEMPLATE, existIds);
        log.info("初始化虚拟机以及模板id成功");
    }

}
