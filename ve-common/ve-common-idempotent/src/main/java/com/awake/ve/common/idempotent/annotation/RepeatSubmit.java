package com.awake.ve.common.idempotent.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Inherited
public @interface RepeatSubmit {

    /**
     * 时间间隔,用以规定幂等性检查的时间窗口
     */
    int internal() default 3000;

    /**
     * 时间单位,默认毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 重复提交触发的消息提示
     */
    String message() default "{repeat.submit.message}";
}
