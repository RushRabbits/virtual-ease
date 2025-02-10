package com.awake.ve.common.websocket.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * websocket配置项
 *
 * @author wangjiaxing
 * @date 2025/2/10 15:05
 */
@ConfigurationProperties("websocket")
@Data
public class WebSocketProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * 路径
     */
    private String path;

    /**
     * 设置访问源地址
     */
    private String allowedOrigins;
}
