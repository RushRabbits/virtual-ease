package com.awake.ve.common.web.config;

import com.awake.ve.common.web.core.I18nLocaleResolver;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 国际化配置
 *
 * @author Lion Li
 */
@AutoConfiguration(before = WebMvcAutoConfiguration.class)
public class I18nConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/public/");
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new I18nLocaleResolver();
    }
}
