package io.ads.modules.analysis.excel.converter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;

/**
 * 奖项二级主题（所属小类）转换
 * 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践
 * @author 12508
 */
public class AwardSubtopicConverter  implements Converter<Integer> {
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
            case "奖惩记录":
                return 0;
            case "实验与竞赛":
                return 1;
            case "学业成绩":
                return 2;
            case "体育特长":
                return 3;
            case "美育成果":
                return 4;
            case "劳动实践":
                return 5;
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
            return new WriteCellData<>("奖惩记录");
        } else if (cellValue == 1) {
            return new WriteCellData<>("实验与竞赛");
        } else if (cellValue == 2) {
            return new WriteCellData<>("学业成绩");
        } else if (cellValue == 3) {
            return new WriteCellData<>("体育特长");
        } else if (cellValue == 4) {
            return new WriteCellData<>("美育成果");
        } else if (cellValue == 5) {
            return new WriteCellData<>("劳动实践");
        } else {
            return new WriteCellData<>("");
        }

    }
}
