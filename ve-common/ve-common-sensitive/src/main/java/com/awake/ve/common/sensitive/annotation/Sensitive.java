package com.awake.ve.common.sensitive.annotation;

import com.awake.ve.common.sensitive.core.SensitiveStrategy;
import com.awake.ve.common.sensitive.handler.SensitiveHandler;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveHandler.class)
public @interface Sensitive {

    /**
     * 脱敏策略
     */
    SensitiveStrategy strategy();

    /**
     * 脱敏角色(拥有指定角色,则不脱敏)
     */
    String roleKey() default "";

    /**
     * 脱敏权限(拥有指定权限则不脱敏)
     */
    String perms() default "";

}
