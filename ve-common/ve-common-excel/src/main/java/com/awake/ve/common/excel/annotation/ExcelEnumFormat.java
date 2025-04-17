package com.awake.ve.common.excel.annotation;

import java.lang.annotation.*;

/**
 * 枚举格式化
 *
 * @author wangjiaxing
 * @date 2024/12/18 9:09
 */
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelEnumFormat {

    /**
     * 字典枚举类型
     * 字段映射的枚举类
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * 字典枚举类中对应的code属性名,默认code
     */
    String codeField() default "code";

    /**
     * 字典枚举类中对应的desc属性名,默认desc
     */
    String descField() default "desc";

}
