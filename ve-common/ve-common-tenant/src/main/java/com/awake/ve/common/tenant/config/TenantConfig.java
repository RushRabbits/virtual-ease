package com.awake.ve.common.tenant.config;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.hutool.core.util.ObjectUtil;
import com.awake.ve.common.core.utils.reflect.ReflectUtils;
import com.awake.ve.common.mybatis.config.MybatisPlusConfig;
import com.awake.ve.common.tenant.core.TenantSaTokenDao;
import com.awake.ve.common.tenant.handle.PlusTenantLineHandler;
import com.awake.ve.common.tenant.handle.TenantKeyPrefixHandler;
import com.awake.ve.common.tenant.manager.TenantSpringCacheManager;
import com.awake.ve.common.tenant.properties.TenantProperties;
import com.awake.ve.common.redis.config.RedisConfig;
import com.awake.ve.common.redis.config.properties.RedissonProperties;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 租户的配置类
 *
 * @author wangjiaxing
 * @date 2025/1/7 9:59
 */
@EnableConfigurationProperties(TenantProperties.class)
@AutoConfiguration(after = {RedisConfig.class})
@ConditionalOnProperty(value = "tenant.enable", havingValue = "true")
public class TenantConfig {

    @ConditionalOnBean(MybatisPlusConfig.class)
    @AutoConfiguration(after = {MybatisPlusConfig.class})
    static class MybatisPlusConfiguration {

        /**
         * 多租户插件
         *
         * @author wangjiaxing
         * @date 2025/1/7 10:01
         */
        @Bean
        public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties tenantProperties) {
            return new TenantLineInnerInterceptor(new PlusTenantLineHandler(tenantProperties));
        }
    }

    /**
     * 定义 Redisson 的配置，主要是为了实现 Redis多租户的键前缀功能
     *
     * @author wangjiaxing
     * @date 2025/1/7 14:29
     */
    @Bean
    public RedissonAutoConfigurationCustomizer tenantRedissonCustomizer(RedissonProperties redissonProperties) {
        return config -> {

            TenantKeyPrefixHandler nameMapper = new TenantKeyPrefixHandler(redissonProperties.getKeyPrefix());

            // 单机模式
            SingleServerConfig singleServerConfig = ReflectUtils.invokeGetter(config, "singleServerConfig");
            if (ObjectUtil.isNotNull(singleServerConfig)) {
                // 使用单机模式
                // 设置多租户 redis key 前缀
                singleServerConfig.setNameMapper(nameMapper);
                ReflectUtils.invokeSetter(config, "singleServerConfig", singleServerConfig);
            }

            // 集群模式
            ClusterServersConfig clusterServersConfig = ReflectUtils.invokeGetter(config, "clusterServersConfig");
            // 集群配置方式,参考下方注释
            if (ObjectUtil.isNotNull(clusterServersConfig)) {
                // 设置多租户 redis key 前缀
                clusterServersConfig.setNameMapper(nameMapper);
                ReflectUtils.invokeSetter(config, "clusterServersConfig", clusterServersConfig);
            }
        };
    }

    /**
     * 多租户缓存管理器
     *
     * @author wangjiaxing
     * @date 2025/1/7 11:53
     */
    @Primary
    @Bean
    public CacheManager tenantCacheManager() {
        return new TenantSpringCacheManager();
    }

    /**
     * 多租户鉴权dao实现
     */
    @Primary
    @Bean
    public SaTokenDao tenantSatokenDao() {
        return new TenantSaTokenDao();
    }
}
