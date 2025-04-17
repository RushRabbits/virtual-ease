package com.awake.ve.common.excel.annotation;

import java.lang.annotation.*;

/**
 * excel单元格合并注解
 *
 * @author wangjiaxing
 * @date 2024/12/18 9:00
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface CellMerge {

    /**
     * col index
     */
    int index() default -1;

    /**
     * 合并需要依赖的其他字段名称
     */
    String[] mergeBy() default {};
}
