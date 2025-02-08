package com.awake.ve.common.redis.annotation;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TranslationType {

    /**
     * 类型
     */
    String type() default "";
}
