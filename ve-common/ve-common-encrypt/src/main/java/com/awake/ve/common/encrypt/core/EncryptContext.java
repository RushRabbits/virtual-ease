package com.awake.ve.common.encrypt.core;

import com.awake.ve.common.encrypt.enums.AlgorithmType;
import com.awake.ve.common.encrypt.enums.EncodeType;
import lombok.Data;

/**
 * 加密的上下文,用于encryptor传递必要的参数
 *
 * @author wangjiaxing
 * @date 2025/2/10 17:01
 */
@Data
public class EncryptContext {

    /**
     * 默认算法
     */
    private AlgorithmType algorithm;

    /**
     * 密码
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
