package com.awake.ve.common.tenant.core;

import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.satoken.core.dao.PlusSaTokenDao;
import com.awake.ve.common.redis.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.List;

/**
 * SaToken 认证数据持久层,适配多租户
 *
 * @author wangjiaxing
 * @date 2025/1/7 12:04
 */
@Slf4j
public class TenantSaTokenDao extends PlusSaTokenDao {

    @Override
    public String get(String key) {
        return super.get(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    @Override
    public void set(String key, String value, long timeout) {
        super.set(GlobalConstants.GLOBAL_REDIS_KEY + key, value, timeout);
    }

    /**
     * 修修改指定key-value键值对 (过期时间不变)
     */
    @Override
    public void update(String key, String value) {
        long expire = getTimeout(key);
        // -2 = 无此键
        if (expire == NOT_VALUE_EXPIRE) {
            return;
        }
        this.set(key, value, expire);
    }

    /**
     * 删除指定的value
     */
    @Override
    public void delete(String key) {
        super.delete(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * 获取value的剩余存货时间
     *
     * @author wangjiaxing
     * @date 2025/1/7 12:07
     */
    @Override
    public long getTimeout(String key) {
        return super.getTimeout(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    /**
     * 修改key-value键值对过期时间
     *
     * @author wangjiaxing
     * @date 2025/1/7 12:08
     */
    @Override
    public void updateTimeout(String key, long timeout) {
        // 是否要设置为永久
        if (timeout == NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire != NEVER_EXPIRE) {
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        RedisUtils.expire(GlobalConstants.GLOBAL_REDIS_KEY + key, Duration.ofSeconds(timeout));
    }

    // TODO 写到了这里
    @Override
    public Object getObject(String key) {
        return super.getObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    @Override
    public void setObject(String key, Object object, long timeout) {
        super.setObject(GlobalConstants.GLOBAL_REDIS_KEY + key, object, timeout);
    }

    @Override
    public void updateObject(String key, Object object) {
        long expire = getObjectTimeout(key);
        if (expire == NOT_VALUE_EXPIRE) {
            return;
        }
        super.setObject(key, object, expire);
    }

    @Override
    public void deleteObject(String key) {
        super.deleteObject(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    @Override
    public long getObjectTimeout(String key) {
        return super.getObjectTimeout(GlobalConstants.GLOBAL_REDIS_KEY + key);
    }

    @Override
    public void updateObjectTimeout(String key, long timeout) {
        // 是否要设置为永久
        if (timeout == NEVER_EXPIRE) {
            long expire = getTimeout(key);
            if (expire != NEVER_EXPIRE) {
                this.set(key, this.get(key), timeout);
            }
            return;
        }
        RedisUtils.expire(GlobalConstants.GLOBAL_REDIS_KEY + key, Duration.ofSeconds(timeout));
    }

    /**
     * 搜索数据
     */
    @Override
    public List<String> searchData(String prefix, String keyword, int start, int size, boolean sortType) {
        return super.searchData(GlobalConstants.GLOBAL_REDIS_KEY + prefix, keyword, start, size, sortType);
    }
}
