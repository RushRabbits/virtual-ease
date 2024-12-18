package com.awake.ve.common.excel.annotation;

import com.awake.ve.common.core.utils.StringUtils;

import java.lang.annotation.*;

/**
 * excel指定列与字典的映射关系格式化
 *
 * @author wangjiaxing
 * @date 2024/12/18 9:03
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExcelDictFormat {

    /**
     * 字典类型 sys_dict的type值
     */
    String dictType() default "";

    /**
     * 读取内容转表达式(如 0=男,1=女,2=未知)
     */
    String readConverterExp() default "";

    /**
     * 分隔符,读取字符串组的内容
     */
    String separator() default StringUtils.SEPARATOR;
}
