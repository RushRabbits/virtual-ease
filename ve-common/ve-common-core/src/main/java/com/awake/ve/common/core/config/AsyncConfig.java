package com.awake.ve.common.core.config;

import cn.hutool.core.util.ArrayUtil;
import com.awake.ve.common.core.exception.ServiceException;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * 异步配置
 * 如果未使用虚拟线程,则生效
 *
 * @author wangjiaxing
 * @date 2025/2/11 11:10
 */
@AutoConfiguration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 自定义@Async注解 使用系统线程池
     *
     * @author wangjiaxing
     * @date 2025/2/11 11:11
     */
    @Override
    public Executor getAsyncExecutor() {
        if (SpringUtils.isVirtual()) {
            return new VirtualThreadTaskExecutor("async-");
        }
        return SpringUtils.getBean("scheduledExecutorService");
    }

    /**
     * 异步线程的异常处理
     *
     * @author wangjiaxing
     * @date 2025/2/11 11:13
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            throwable.printStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("Exception message - ")
                    .append(throwable.getMessage())
                    .append("\nMethod name - ")
                    .append(method.getName());
            if (ArrayUtil.isNotEmpty(objects)) {
                sb.append("\nParameters - ")
                        .append(Arrays.toString(objects));
            }
            throw new ServiceException(sb.toString());
        };
    }
}
