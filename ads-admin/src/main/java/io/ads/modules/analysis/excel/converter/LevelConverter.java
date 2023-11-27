package io.ads.modules.analysis.excel.converter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 导出/读取 excel时， 转换 综合等级 学业水平
 * @author 12508
 */
public class LevelConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        // 对象属性类型
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        // CellData属性类型
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) throws Exception {
        // CellData 转 对象属性
        String cellStr = context.getReadCellData().getStringValue();
        if (StrUtil.isEmpty(cellStr)) {
            return null;
        }
        if ("优".equals(cellStr)) {
            return 0;
        } else if ("中".equals(cellStr)) {
            return 1;
        } else if ("差".equals(cellStr)){
            return 2;
        } else {
            return null;
        }
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        // 对象属性 转 CellData
        Integer cellValue = context.getValue();
        if (cellValue == null) {
            return new WriteCellData<>("");
        }
        if (cellValue == 0) {
            return new WriteCellData<>("优");
        } else if (cellValue == 1) {
            return new WriteCellData<>("中");
        } else if (cellValue == 2) {
            return new WriteCellData<>("差");
        } else {
            return new WriteCellData<>("");
        }

    }
}
