package com.awake.ve.common.encrypt.config;

import com.awake.ve.common.encrypt.core.EncryptorManager;
import com.awake.ve.common.encrypt.interceptor.MybatisDecryptInterceptor;
import com.awake.ve.common.encrypt.interceptor.MybatisEncryptInterceptor;
import com.awake.ve.common.encrypt.properties.EncryptProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 加密自动配置
 *
 * @author wangjiaxing
 * @date 2025/2/10 18:29
 */
@AutoConfiguration
@EnableConfigurationProperties(EncryptProperties.class)
@ConditionalOnProperty(value = "mybatis-encryptor.enable", havingValue = "true")
@Slf4j
public class EncryptorAutoConfiguration {

    @Autowired
    private EncryptProperties encryptProperties;

    @Bean
    public EncryptorManager encryptorManager(MybatisPlusProperties mybatisPlusProperties) {
        return new EncryptorManager(mybatisPlusProperties.getTypeAliasesPackage());
    }

    @Bean
    public MybatisEncryptInterceptor mybatisEncryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisEncryptInterceptor(encryptorManager, encryptProperties);
    }

    @Bean
    public MybatisDecryptInterceptor mybatisDecryptInterceptor(EncryptorManager encryptorManager) {
        return new MybatisDecryptInterceptor(encryptorManager, encryptProperties);
    }
}
