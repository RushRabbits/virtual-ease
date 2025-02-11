package com.awake.ve.common.oss.factory;

import com.awake.ve.common.core.constant.CacheNames;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.json.utils.JsonUtils;
import com.awake.ve.common.oss.constant.OssConstant;
import com.awake.ve.common.oss.core.OssClient;
import com.awake.ve.common.oss.exception.OssException;
import com.awake.ve.common.oss.properties.OssProperties;
import com.awake.ve.common.translation.utils.CacheUtils;
import com.awake.ve.common.translation.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class OssFactory {

    private static final Map<String, OssClient> CLIENT_CACHE = new ConcurrentHashMap<>();
    private static final ReentrantLock LOCK = new ReentrantLock();

    public static OssClient instance() {
        // 使用redis默认类型
        String configKey = RedisUtils.getCacheObject(OssConstant.DEFAULT_CONFIG_KEY);
        if (StringUtils.isEmpty(configKey)) {
            throw new OssException("文件存储类型无法找到");
        }
        return instance(configKey);
    }

    /**
     * 根据服务商类型获取OssClient
     *
     * @param configKey 服务商配置
     * @return {@link OssClient}
     */
    public static OssClient instance(String configKey) {
        String json = CacheUtils.get(CacheNames.SYS_OSS_CONFIG, configKey);
        if (json == null) {
            throw new OssException("系统异常,'" + configKey + "'配置信息不存在");
        }

        OssProperties ossProperties = JsonUtils.parseObject(json, OssProperties.class);

        // 使用租户标识,避免多个租户相同key实例覆盖
        String key = configKey;
        if (StringUtils.isNotBlank(ossProperties.getTenantId())) {
            key = ossProperties.getTenantId() + ":" + configKey;
        }
        OssClient client = CLIENT_CACHE.get(key);

        // 客户端不存在或配置不同则重新构建
        if (client == null || !client.checkPropertiesSame(ossProperties)) {
            LOCK.lock();
            try {
                client = CLIENT_CACHE.get(key);
                if (client == null || !client.checkPropertiesSame(ossProperties)) {
                    CLIENT_CACHE.put(key, new OssClient(configKey, ossProperties));
                    log.info("创建新的OSS实例，配置KEY：{}", configKey);
                    return CLIENT_CACHE.get(key);
                }
            } finally {
                LOCK.unlock();
            }
        }
        return client;
    }
}
