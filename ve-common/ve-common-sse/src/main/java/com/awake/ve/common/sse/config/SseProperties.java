package com.awake.ve.common.sse.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * sse配置类
 *
 * @author wangjiaxing
 * @date 2025/2/10 11:55
 */
@Data
@ConfigurationProperties(prefix = "sse")
public class SseProperties {
    /**
     * 是否开启sse
     */
    private Boolean enabled;

    /**
     * 路径
     */
    private String path;
}
