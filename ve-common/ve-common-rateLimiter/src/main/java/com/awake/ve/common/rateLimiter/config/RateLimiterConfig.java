package com.awake.ve.common.rateLimiter.config;

import com.awake.ve.common.rateLimiter.aspect.RateLimiterAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConfiguration;

/**
 * 限流配置类
 *
 * @author wangjiaxing
 * @date 2025/1/8 10:07
 */
@AutoConfiguration(after = RedisConfiguration.class)
public class RateLimiterConfig {

    /**
     * 配置限流的切面
     *
     * @author wangjiaxing
     * @date 2025/1/8 10:08
     */
    @Bean
    public RateLimiterAspect rateLimiterAspect() {
        return new RateLimiterAspect();
    }
}
