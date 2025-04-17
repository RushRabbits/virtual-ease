package com.awake.ve.common.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "virtual-ease")
public class VirtualEaseConfig {
    private String name;
    private String version;
    private String copyrightYear;
}
