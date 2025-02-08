package com.awake.ve.common.redis.annotation;

import com.awake.ve.common.redis.core.handler.TranslationHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = TranslationHandler.class)
public @interface Translation {

    /**
     * 类型 (需要与实现类上的{@link TranslationType}注解的type保持一致)
     * <p>
     * 默认取当前字段的值,如果设置了{@link Translation#mapper()} 则取映射字段的值
     */
    String type() default "";

    /**
     * 映射字段(如不为空则取此字段的值)
     */
    String mapper() default "";

    /**
     * 其他条件 例如: 字典type
     */
    String other() default "";
}
