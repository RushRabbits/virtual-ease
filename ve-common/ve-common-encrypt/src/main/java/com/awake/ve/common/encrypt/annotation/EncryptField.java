package com.awake.ve.common.encrypt.annotation;

import com.awake.ve.common.encrypt.enums.AlgorithmType;
import com.awake.ve.common.encrypt.enums.EncodeType;

import java.lang.annotation.*;

/**
 * 加密字段注解
 *
 * @author wangjiaxing
 * @date 2025/2/10 16:50
 */
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptField {

    /**
     * 加密算法
     */
    AlgorithmType algorithm() default AlgorithmType.DEFAULT;

    /**
     * 密钥 AES SM4需要
     */
    String password() default "";

    /**
     * 公钥 RSA SM2需要
     */
    String publicKey() default "";

    /**
     * 私钥 RSA SM2需要
     */
    String privateKey() default "";

    /**
     * 编码方式
     */
    EncodeType encode() default EncodeType.DEFAULT;
}
