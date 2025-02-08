package com.awake.ve.common.idempotent.aspect;

import cn.dev33.satoken.SaManager;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.SecureUtil;
import com.awake.ve.common.core.constant.GlobalConstants;
import com.awake.ve.common.core.domain.R;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.MessageUtils;
import com.awake.ve.common.core.utils.ObjectUtils;
import com.awake.ve.common.core.utils.ServletUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.idempotent.annotation.RepeatSubmit;
import com.awake.ve.common.redis.utils.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 防重提交
 *
 * @author wangjiaxing
 * @date 2024/12/18 10:31
 */
@Aspect
public class RepeatSubmitAspect {

    private static final ThreadLocal<String> KEY_CACHE = new ThreadLocal<>();

    /**
     * 执行目标方法前
     *
     * @author wangjiaxing
     * @date 2024/12/18 10:33
     */
    @Before(value = "@annotation(repeatSubmit)")
    public void doBefore(JoinPoint joinPoint, RepeatSubmit repeatSubmit) {
        long internal = repeatSubmit.timeUnit().toMillis(repeatSubmit.internal());

        if (internal < 1000) {
            throw new ServiceException("防重提交时间间隔不能小于1秒");
        }

        HttpServletRequest request = ServletUtils.getRequest();
        HttpServletResponse response = ServletUtils.getResponse();
        String nowParams = argsArrayToString(joinPoint.getArgs());

        // 请求地址,作为存放cache的key
        String url = request.getRequestURI();

        // 唯一值,没有消息头的话则使用请求地址
        String submitKey = StringUtils.trimToEmpty(request.getHeader(SaManager.getConfig().getTokenName()));
        submitKey = SecureUtil.md5(submitKey + ":" + nowParams);

        // 唯一标识(指定key + url + 消息头 + nowParams)
        String cacheRepeatKey = GlobalConstants.REPEAT_SUBMIT_KEY + url + submitKey;
        if (RedisUtils.setObjectIfAbsent(cacheRepeatKey, "", Duration.ofMillis(internal))) {
            KEY_CACHE.set(cacheRepeatKey);
        } else {
            String message = repeatSubmit.message();
            if (StringUtils.startsWith(message, "{") && StringUtils.endsWith(message, "}")) {
                message = MessageUtils.message(StringUtils.substring(message, 1, message.length() - 1));
            }
            throw new ServiceException(message);
        }
    }

    /**
     * 处理过目标方法后
     *
     * @author wangjiaxing
     * @date 2024/12/18 10:54
     */
    @AfterReturning(pointcut = "@annotation(repeatSubmit)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Object result) {
        if (result instanceof R<?> r) {
            try {
                // 请求成功不需要清除redis缓存,因为需要防止重复提交
                if (r.getCode() == R.SUCCESS) {
                    return;
                }
                // 请求失败需要清除redis缓存
                RedisUtils.deleteObject(KEY_CACHE.get());
            } finally {
                KEY_CACHE.remove();
            }
        }
    }

    /**
     * 目标方法抛出异常后
     *
     * @author wangjiaxing
     * @date 2024/12/18 10:57
     */
    @AfterThrowing(pointcut = "@annotation(repeatSubmit)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, RepeatSubmit repeatSubmit, Throwable e) {
        RedisUtils.deleteObject(KEY_CACHE.get());
        KEY_CACHE.remove();
    }

    private String argsArrayToString(Object[] args) {
        StringJoiner params = new StringJoiner(" ");
        if (ArrayUtil.isEmpty(args)) {
            return params.toString();
        }
        for (Object arg : args) {
            if (ObjectUtils.isNotNull(arg) && !isFilterObject(arg)) {
                params.add(arg.toString());
            }
        }
        return params.toString();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return MultipartFile.class.isAssignableFrom(clazz.getComponentType());
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.values()) {
                return value instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }

}
