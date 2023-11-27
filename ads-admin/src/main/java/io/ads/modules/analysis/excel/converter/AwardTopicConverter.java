package io.ads.modules.analysis.excel.converter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * 奖项一级主题（所属五育）转换
 * 0德育, 1智育, 2体育, 3美育, 4劳育
 * @author 12508
 */
public class AwardTopicConverter  implements Converter<Integer> {
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
        switch (cellStr) {
            case "德育":
                return 0;
            case "智育":
                return 1;
            case "体育":
                return 2;
            case "美育":
                return 3;
            case "劳育":
                return 4;
            default:
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
            return new WriteCellData<>("德育");
        } else if (cellValue == 1) {
            return new WriteCellData<>("智育");
        } else if (cellValue == 2) {
            return new WriteCellData<>("体育");
        } else if (cellValue == 3){
            return new WriteCellData<>("美育");
        } else if (cellValue == 4) {
            return new WriteCellData<>("劳育");
        } else {
            return new WriteCellData<>("");
        }

    }
}
