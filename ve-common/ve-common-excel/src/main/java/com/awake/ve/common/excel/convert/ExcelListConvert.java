package com.awake.ve.common.excel.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 列表转换器
 *
 * @author wangjiaxing
 * @date 2024/12/18 10:08
 */
@Slf4j
public class ExcelListConvert implements Converter<List<T>> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return List.class;
    }

    @Override
    public List<T> convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return Converter.super.convertToJavaData(cellData, contentProperty, globalConfiguration);
    }

    @Override
    public WriteCellData<?> convertToExcelData(List<T> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<List<T>>(value.toString());
    }
}
