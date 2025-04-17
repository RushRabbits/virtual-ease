package com.awake.ve.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.awake.ve.common.core.utils.SpringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel json内容与json对象转换器
 *
 * @author wangjiaxing
 * @date 2024/12/18 10:02
 */
@Slf4j
public class ExcelJsonConvert implements Converter<JsonNode> {
    /**
     * Jackson对象映射器
     */
    private static final ObjectMapper MAPPER = SpringUtils.getBean(ObjectMapper.class);

    @Override
    public Class<?> supportJavaTypeKey() {
        return JsonNode.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public JsonNode convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String cellValue = cellData.getStringValue();
        return MAPPER.readTree(cellValue);
    }

    @Override
    public JsonNode convertToJavaData(ReadConverterContext<?> context) throws Exception {
        String cellValue = context.getReadCellData().getStringValue();
        return MAPPER.readTree(cellValue);
    }

    /**
     * 自定义对 JsonNode 类型数据的处理
     * 我这里就拿 String 去包装了下
     */
    @Override
    public WriteCellData<?> convertToExcelData(JsonNode value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<JsonNode>(String.valueOf(value));
    }
}
