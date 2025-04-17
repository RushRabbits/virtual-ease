package com.awake.ve.common.ecs.config;

import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.ecs.config.propterties.EcsProperties;
import com.awake.ve.common.ecs.core.EcsClient;
import com.awake.ve.common.ecs.core.impl.PveClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(EcsProperties.class)
public class EcsConfig {

    @Bean
    public EcsClient ecsClient(EcsProperties ecsProperties) {
        if (StringUtils.equalsIgnoreCase(ecsProperties.getService(), "pve")) {
            return new PveClient();
        }
        throw new ServiceException("暂不支持该服务");
    }
}
