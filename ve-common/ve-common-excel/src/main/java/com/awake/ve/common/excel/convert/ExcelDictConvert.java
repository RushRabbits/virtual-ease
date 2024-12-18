package com.awake.ve.common.excel.convert;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.awake.ve.common.excel.annotation.ExcelDictFormat;
import com.awake.ve.common.core.utils.ObjectUtils;
import com.awake.ve.common.core.utils.SpringUtils;
import com.awake.ve.common.core.utils.StringUtils;
import com.awake.ve.common.service.DictService;
import com.awake.ve.common.excel.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class ExcelDictConvert implements Converter<Object> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Object.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Object convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        ExcelDictFormat annotation = getAnnotation(contentProperty.getField());
        String dictType = annotation.dictType();
        String label = cellData.getStringValue();
        String value;
        // 没有指定的字典,则根据注解中给定的表达式进行转换
        if (StringUtils.isBlank(dictType)) {
            value = ExcelUtil.reverseByExp(label, annotation.readConverterExp(), annotation.separator());
        } else {
            value = SpringUtils.getBean(DictService.class).getDictValue(dictType, label, annotation.separator());
        }
        return Convert.convert(contentProperty.getField().getType(), value);
    }

    @Override
    public WriteCellData<?> convertToExcelData(Object o, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (ObjectUtils.isNull(o)) {
            return new WriteCellData<>("");
        }
        ExcelDictFormat annotation = getAnnotation(contentProperty.getField());
        String dictType = annotation.dictType();
        String value = Convert.toStr(o);
        String label;
        if (StringUtils.isBlank(dictType)) {
            label = ExcelUtil.reverseByExp(value, annotation.readConverterExp(), annotation.separator());
        } else {
            label = SpringUtils.getBean(DictService.class).getDictLabel(dictType, value, annotation.separator());
        }
        return new WriteCellData<>(label);
    }

    private ExcelDictFormat getAnnotation(Field field) {
        return AnnotationUtil.getAnnotation(field, ExcelDictFormat.class);
    }
}
