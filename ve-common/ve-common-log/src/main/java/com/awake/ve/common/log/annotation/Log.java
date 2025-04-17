package com.awake.ve.common.log.annotation;

import com.awake.ve.common.log.enums.BusinessType;
import com.awake.ve.common.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author wangjiaxing
 * @date 2024/12/17 14:17
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;

    /**
     * 排除指定的请求参数
     */
    String[] excludeParamNames() default {};
}
