package com.awake.ve.common.encrypt.core;

import com.awake.ve.common.encrypt.enums.AlgorithmType;
import com.awake.ve.common.encrypt.enums.EncodeType;

/**
 * 加密解密接口
 *
 * @author wangjiaxing
 * @date 2025/2/10 16:55
 */
public interface IEncryptor {

    /**
     * 加密/解密算法
     */
    AlgorithmType algorithm();

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     * @return 加密后的字符串
     * @author wangjiaxing
     * @date 2025/2/10 16:56
     */
    String encrypt(String value, EncodeType encodeType);

    /**
     * 解密
     *
     * @param value 待解密的值
     * @return 解密后的值
     * @author wangjiaxing
     * @date 2025/2/10 16:57
     */
    String decrypt(String value);
}
