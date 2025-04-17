package com.awake.ve.common.tenant.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "tenant")
public class TenantProperties {

    /**
     * 是否开启多租户
     */
    private Boolean enable;

    /**
     * 排除的数据表
     */
    private List<String> excludes;

}
