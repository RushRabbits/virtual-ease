package com.awake.ve.common.encrypt.annotation;

import java.lang.annotation.*;

/**
 * API加密注解
 *
 * @author wangjiaxing
 * @date 2025/2/10 16:48
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiEncrypt {

    /**
     * 响应加密忽略,默认不加密,为true时加密
     */
    boolean response() default false;
}
