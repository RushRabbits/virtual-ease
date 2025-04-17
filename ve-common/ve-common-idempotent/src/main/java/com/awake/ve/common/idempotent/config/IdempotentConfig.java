package com.awake.ve.common.idempotent.config;

import com.awake.ve.common.idempotent.aspect.RepeatSubmitAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConfiguration;

/**
 * 幂等功能配置
 *
 * @author wangjiaxing
 * @date 2024/12/18 10:23
 */
@AutoConfiguration(after = RedisConfiguration.class)
public class IdempotentConfig {

    @Bean
    public RepeatSubmitAspect repeatSubmitAspect() {
        return new RepeatSubmitAspect();
    }
}
