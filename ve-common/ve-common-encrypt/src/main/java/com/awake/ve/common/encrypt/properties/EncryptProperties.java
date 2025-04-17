package com.awake.ve.common.encrypt.properties;

import com.awake.ve.common.encrypt.enums.AlgorithmType;
import com.awake.ve.common.encrypt.enums.EncodeType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加密属性类
 *
 * @author wangjiaxing
 * @date 2025/2/10 18:32
 */
@Data
@ConfigurationProperties(prefix = "mybatis-encryptor")
public class EncryptProperties {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 算法类型
     */
    private AlgorithmType algorithm;

    /**
     * 密钥
     */
    private String password;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 编码方式
     */
    private EncodeType encode;
}
