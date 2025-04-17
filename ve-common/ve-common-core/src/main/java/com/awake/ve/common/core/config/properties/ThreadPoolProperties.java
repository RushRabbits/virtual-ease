package com.awake.ve.common.core.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "thread-pool")
public class ThreadPoolProperties {

    /**
     * 开关
     */
    private Boolean enabled;

    /**
     * 队列最大长度
     */
    private Integer queueCapacity;

    /**
     * 线程池的线程最大空闲时间
     */
    private Integer keepAliveSeconds;
}
