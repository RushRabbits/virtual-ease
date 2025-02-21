package com.awake.ve.common.ecs.config.propterties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ecs")
public class EcsProperties {
    /**
     * ecs服务提供者
     */
    private String service;
    /**
     * ecs服务地址
     */
    private String host;
    /**
     * ecs服务端口
     */
    private Integer port;
    /**
     * ecs服务登录用户名
     */
    private String username;
    /**
     * ecs服务登录密码
     */
    private String password;
}
