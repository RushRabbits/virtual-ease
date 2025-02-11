package com.awake.ve.common.sms.core.dao;


import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.dao.SmsDao;

import java.time.Duration;

/**
 * SmsDao缓存配置
 *
 * @author wangjiaxing
 * @date 2025/2/11 10:41
 */
@Slf4j
public class PlusSmsDao implements SmsDao {

    /**
     * 存储
     *
     * @param key       键
     * @param value     值
     * @param cacheTime 超时时间
     * @author wangjiaxing
     * @date 2025/2/11 10:42
     */
    @Override
    public void set(String key, Object value, long cacheTime) {
        RedisUtils.setCacheObject(GlobalConstants.GLOBAL_REDIS_KEY + key, value, Duration.ofSeconds(cacheTime));
    }

    /**
     * 存储
     *
     * @param key   键
     * @param value 值
     * @author wangjiaxing
     * @date 2025/2/11 10:42
     */
    @Override
    public void set(String key, Object value) {
        RedisUtils.setCacheObject(GlobalConstants.GLOBAL_REDIS_KEY + key, value);
    }

    /**
     * 获取
     *
     * @param key 键
     * @return 值
     * @author wangjiaxing
     * @date 2025/2/11 10:44
     */
    @Override
    public Object get(String key) {
        return RedisUtils.getCacheObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * 移除
     *
     * @param key 键
     * @return 移除的值
     * @author wangjiaxing
     * @date 2025/2/11 10:45
     */
    @Override
    public Object remove(String key) {
        return RedisUtils.deleteObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * 清空
     */
    @Override
    public void clean() {
        RedisUtils.deleteObject(GlobalConstants.GLOBAL_REDIS_KEY + "sms:");
    }
}
