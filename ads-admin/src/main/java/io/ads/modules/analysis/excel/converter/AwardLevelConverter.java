package io.ads.modules.analysis.excel.converter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * 奖项等级转换
 * 0国家级, 1省级, 2市厅级, 3区级, 4校级
 * @author 12508
 */
public class AwardLevelConverter  implements Converter<Integer> {

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
        if ("国家级".equals(cellStr)) {
            return 0;
        } else if ("省级".equals(cellStr)) {
            return 1;
        } else if ("市厅级".equals(cellStr)){
            return 2;
        } else if ("区级".equals(cellStr)){
            return 3;
        } else if ("校级".equals(cellStr)) {
            return 4;
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
            return new WriteCellData<>("国家级");
        } else if (cellValue == 1) {
            return new WriteCellData<>("省级");
        } else if (cellValue == 2) {
            return new WriteCellData<>("市厅级");
        } else if (cellValue == 3){
            return new WriteCellData<>("区级");
        } else if (cellValue == 4) {
            return new WriteCellData<>("校级");
        } else {
            return new WriteCellData<>("");
        }

    }
}
