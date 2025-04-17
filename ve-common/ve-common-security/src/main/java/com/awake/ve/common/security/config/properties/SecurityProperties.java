package com.awake.ve.common.security.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Security配置属性
 *
 * @author wangjiaxing
 * @date 2025/2/10 10:54
 */
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    /**
     * 放行的路径
     */
    private String[] excludes;

}
