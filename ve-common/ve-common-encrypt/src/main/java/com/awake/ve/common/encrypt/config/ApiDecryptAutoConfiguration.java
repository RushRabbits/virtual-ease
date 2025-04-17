package com.awake.ve.common.encrypt.config;

import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.encrypt.filter.CryptoFilter;
import com.awake.ve.common.encrypt.properties.ApiDecryptProperties;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * api解密自动装配
 *
 * @author wangjiaxing
 * @date 2025/2/10 17:07
 */
@AutoConfiguration
@EnableConfigurationProperties(ApiDecryptProperties.class)
@ConditionalOnProperty(value = "api-decrypt.enabled", havingValue = "true")
public class ApiDecryptAutoConfiguration {

    @Bean
    public FilterRegistrationBean<CryptoFilter> cryptoFilterRegistration() {
        FilterRegistrationBean<CryptoFilter> registration = new FilterRegistrationBean<>();
        // 设置仅处理REQUEST类型的请求
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        // 注册加解密过滤器
        registration.setFilter(new CryptoFilter(SpringUtils.getBean(ApiDecryptProperties.class)));

        // 设置过滤器的URL模式，/*表示拦截所有请求
        registration.addUrlPatterns("/*");

        // 设置过滤器名称
        registration.setName("cryptoFilter");

        // 设置过滤器优先级为最高
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);

        return registration;
    }
}
