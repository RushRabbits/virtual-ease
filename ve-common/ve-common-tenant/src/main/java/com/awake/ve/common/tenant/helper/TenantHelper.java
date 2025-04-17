package com.awake.ve.common.tenant.helper;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.reflect.ReflectUtils;
import com.awake.ve.common.satoken.utils.LoginHelper;
import com.awake.ve.common.translation.utils.RedisUtils;
import com.baomidou.mybatisplus.core.plugins.IgnoreStrategy;
import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import io.micrometer.common.util.StringUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;
import java.util.function.Supplier;

@Slf4j
@NoArgsConstructor
public class TenantHelper {

    /**
     * 动态租户key
     */
    private static final String DYNAMIC_TENANT_KEY = GlobalConstants.GLOBAL_REDIS_KEY + "dynamicTenant";

    /**
     * 临时动态租户
     */
    private static final ThreadLocal<String> TEMP_DYNAMIC_TENANT = new ThreadLocal<>();

    /**
     * 重入计数器(猜的)
     */
    private static final ThreadLocal<Stack<Integer>> REENTRANT_IGNORE = ThreadLocal.withInitial(Stack::new);

    /**
     * 是否开启了多租户的功能
     *
     * @author wangjiaxing
     * @date 2025/1/7 10:17
     */
    public static boolean isEnable() {
        return Convert.toBool(SpringUtils.getProperty("tenant.enable"), false);
    }

    public static IgnoreStrategy getIgnoreStrategy() {
        Object ignoreStrategyLocal = ReflectUtils.getStaticFieldValue(ReflectUtils.getField(InterceptorIgnoreHelper.class, "IGNORE_STRATEGY_LOCAL"));
        // JDK16新特性,如果instanceof成立,那么直接就完成类型转换,并转换后的结果赋给 IGNORE_STRATEGY_LOCAL
        if (ignoreStrategyLocal instanceof ThreadLocal<?> IGNORE_STRATEGY_LOCAL) {
            if (IGNORE_STRATEGY_LOCAL.get() instanceof IgnoreStrategy ignoreStrategy) {
                return ignoreStrategy;
            }
        }
        return null;
    }

    /**
     * 开启忽略租户(开启后需要手动调用 {@link  #disableIgnore()} 进行关闭)
     *
     * @author wangjiaxing
     * @date 2025/1/7 10:26
     */
    public static void enableIgnore() {
        // 获取忽略策略
        IgnoreStrategy ignoreStrategy = getIgnoreStrategy();
        if (ObjectUtil.isNull(ignoreStrategy)) {
            // 如果没有忽略策略,则创建新的
            InterceptorIgnoreHelper.handle(IgnoreStrategy.builder().tenantLine(true).build());
        } else {
            // 如果有忽略策略,则更新已经有的
            ignoreStrategy.setTenantLine(true);
        }
        Stack<Integer> reentrantStack = REENTRANT_IGNORE.get();
        reentrantStack.push(reentrantStack.size() + 1);
    }

    /**
     * 关闭忽略租户
     *
     * @author wangjiaxing
     * @date 2025/1/7 10:31
     */
    public static void disableIgnore() {
        // 1. 获取当前忽略策略
        IgnoreStrategy ignoreStrategy = getIgnoreStrategy();

        if (ObjectUtil.isNotNull(ignoreStrategy)) {
            // 2. 检查是否没有其他活跃的忽略策略
            boolean noOtherIgnoreStrategy = checkNoOtherStrategies(ignoreStrategy);

            // 3. 处理重入计数
            Stack<Integer> reentrantStack = REENTRANT_IGNORE.get();
            boolean empty = reentrantStack.isEmpty() || reentrantStack.pop() == 1;

            // 4. 根据条件决定清理方式
            if (noOtherIgnoreStrategy && empty) {
                // 完全清理
                InterceptorIgnoreHelper.clearIgnoreStrategy();
            } else if (empty) {
                // 仅禁用租户线
                ignoreStrategy.setTenantLine(false);
            }
        }
    }

    private static boolean checkNoOtherStrategies(IgnoreStrategy ignoreStrategy) {
        return !Boolean.TRUE.equals(ignoreStrategy.getDynamicTableName())
                && !Boolean.TRUE.equals(ignoreStrategy.getBlockAttack())
                && !Boolean.TRUE.equals(ignoreStrategy.getIllegalSql())
                && !Boolean.TRUE.equals(ignoreStrategy.getDataPermission())
                && CollectionUtil.isEmpty(ignoreStrategy.getOthers());
    }

    /**
     * 在忽略租户中执行
     *
     * @param runnable {@link Runnable}
     * @author wangjiaxing
     * @date 2025/1/7 10:45
     */
    public static void ignore(Runnable runnable) {
        enableIgnore();
        try {
            runnable.run();
        } finally {
            disableIgnore();
        }
    }

    /**
     * 在忽略租户中执行
     *
     * @param supplier {@link Supplier}
     * @author wangjiaxing
     * @date 2025/1/7 10:46
     */
    public static <T> T ignore(Supplier<T> supplier) {
        enableIgnore();
        try {
            return supplier.get();
        } finally {
            disableIgnore();
        }
    }

    public static void setDynamic(String tenantId) {
        setDynamic(tenantId, false);
    }

    public static void setDynamic(String tenantId, boolean global) {
        if (!isEnable()) {
            return;
        }
        if (!LoginHelper.isLogin() || !global) {
            TEMP_DYNAMIC_TENANT.set(tenantId);
            return;
        }
        String cacheKey = DYNAMIC_TENANT_KEY + ":" + LoginHelper.getUserId();
        RedisUtils.setCacheObject(cacheKey, tenantId);
    }

    /**
     * 获取动态租户(一直有效,需要动态清理)
     * 如果为未登录的情况,那么旨在当前线程内有效
     *
     * @author wangjiaxing
     * @date 2025/1/7 10:53
     */
    public static String getDynamic() {
        if (!isEnable()) {
            return null;
        }
        if (!LoginHelper.isLogin()) {
            return TEMP_DYNAMIC_TENANT.get();
        }
        // 如果当前线程内有值 优先返回
        String tenantId = TEMP_DYNAMIC_TENANT.get();
        if (!StringUtils.isNotBlank(tenantId)) {
            return tenantId;
        }
        // 线程内没有值,去缓存获取
        String cacheKey = DYNAMIC_TENANT_KEY + ":" + LoginHelper.getUserId();
        tenantId = RedisUtils.getCacheObject(cacheKey);
        return tenantId;
    }

    /**
     * 清楚动态租户
     *
     * @author wangjiaxing
     * @date 2025/1/7 10:59
     */
    public static void clearDynamic() {
        if (!isEnable()) {
            return;
        }
        if (!LoginHelper.isLogin()) {
            TEMP_DYNAMIC_TENANT.remove();
            return;
        }
        TEMP_DYNAMIC_TENANT.remove();
        String cacheKey = DYNAMIC_TENANT_KEY + ":" + LoginHelper.getUserId();
        RedisUtils.deleteObject(cacheKey);
    }

    /**
     * 在动态租户中执行
     *
     * @author wangjiaxing
     * @date 2025/1/7 11:01
     */
    public static void dynamic(String tenantId, Runnable runnable) {
        setDynamic(tenantId);
        try {
            runnable.run();
        } finally {
            clearDynamic();
        }
    }

    /**
     * 在动态租户中执行
     *
     * @author wangjiaxing
     * @date 2025/1/7 11:01
     */
    public static <T> T dynamic(String tenantId, Supplier<T> supplier) {
        setDynamic(tenantId);
        try {
            return supplier.get();
        } finally {
            clearDynamic();
        }
    }

    /**
     * 获取租户id
     *
     * @author wangjiaxing
     * @date 2025/1/7 11:05
     */
    public static String getTenantId() {
        if (!isEnable()) {
            return null;
        }
        String tenantId = TenantHelper.getDynamic();
        if (StringUtils.isBlank(tenantId)) {
            tenantId = LoginHelper.getTenantId();
        }
        return tenantId;
    }

}
