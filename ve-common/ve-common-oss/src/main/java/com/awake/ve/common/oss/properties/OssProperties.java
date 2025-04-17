package com.awake.ve.common.oss.properties;

import lombok.Data;

/**
 * OSS对象存储的配置属性
 *
 * @author wangjiaxing
 * @date 2024/12/17 17:57
 */
@Data
public class OssProperties {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 访问站点
     */
    private String endpoint;

    /**
     * 自定义域名
     */
    private String domain;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * access key
     */
    private String accessKey;

    /**
     * secret key
     */
    private String secretKey;

    /**
     * 桶名
     */
    private String bucketName;

    /**
     * 存储区域
     */
    private String region;

    /**
     * 是否https(Y=是;N=否)
     */
    private String isHttps;

    /**
     * 桶权限类型
     * 0    private
     * 1    public
     * 2    custom
     */
    private String accessPolicy;
}
