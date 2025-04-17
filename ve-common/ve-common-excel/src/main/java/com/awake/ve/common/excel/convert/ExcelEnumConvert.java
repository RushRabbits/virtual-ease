package com.awake.ve.common.excel.convert;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.convert.Convert;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.awake.ve.common.excel.annotation.ExcelEnumFormat;
import com.awake.ve.common.core.utils.ObjectUtils;
import com.awake.ve.common.core.utils.reflect.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举转换器
 *
 * @author wangjiaxing
 * @date 2024/12/18 9:37
 */
@Slf4j
public class ExcelEnumConvert implements Converter<Object> {

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
        cellData.checkEmpty();
        // Excel中填入的是枚举中指定的描述
        Object descValue = switch (cellData.getType()) {
            case STRING, DIRECT_STRING, RICH_TEXT_STRING -> cellData.getStringValue();
            case NUMBER -> cellData.getNumberValue();
            case BOOLEAN -> cellData.getBooleanValue();
            default -> throw new IllegalArgumentException("单元格类型异常");
        };
        // 判空
        if (ObjectUtils.isNull(descValue)) {
            return null;
        }
        Map<Object, String> enumCodeToDescMap = beforeConvert(contentProperty);
        // Java -> Excel : code -> desc
        // Excel -> Java : desc -> code
        Map<Object, Object> enumDescToCodeMap = new HashMap<>();
        enumCodeToDescMap.forEach((code, desc) -> enumDescToCodeMap.put(desc, code));
        // 从 desc -> code 中查找
        Object codeValue = enumDescToCodeMap.get(descValue);
        return Convert.convert(contentProperty.getField().getType(), codeValue);
    }

    @Override
    public WriteCellData<?> convertToExcelData(Object object, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (ObjectUtils.isNull(object)) {
            return new WriteCellData<>("");
        }
        Map<Object, String> enumValueMap = beforeConvert(contentProperty);
        String value = Convert.toStr(enumValueMap.get(object), "");
        return new WriteCellData<>(value);
    }

    private Map<Object, String> beforeConvert(ExcelContentProperty contentProperty) {
        ExcelEnumFormat annotation = getAnnotation(contentProperty.getField());
        Map<Object, String> map = new HashMap<>();
        Enum<?>[] enumConstants = annotation.enumClass().getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            Object code = ReflectUtils.invokeGetter(enumConstant, annotation.codeField());
            String desc = ReflectUtils.invokeGetter(enumConstant, annotation.descField());
            map.put(code, desc);
        }
        return map;
    }

    private ExcelEnumFormat getAnnotation(Field field) {
        return AnnotationUtil.getAnnotation(field, ExcelEnumFormat.class);
    }
}
