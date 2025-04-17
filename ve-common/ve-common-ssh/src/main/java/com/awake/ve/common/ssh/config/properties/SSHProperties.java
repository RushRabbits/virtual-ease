package com.awake.ve.common.ssh.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ssh")
public class SSHProperties {
    /**
     * 是否开启SSH
     */
    private Boolean enabled;
    /**
     * 服务器地址
     */
    private String host;
    /**
     * 服务器端口
     */
    private Integer port;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 连接池最大连接数
     */
    private Integer maxTotal;
    /**
     * 连接池最大空闲连接数
     */
    private Integer maxIdle;
    /**
     * 连接池最小空闲连接数
     */
    private Integer minIdle;
    /**
     * 连接池最大等待时间
     */
    private Integer maxWaitMillis;
}
