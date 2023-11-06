package io.ads.modules.analysis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
public class AwardRecordsExcel {
    @ExcelProperty(value = "主键")
    private Integer id;
    @ExcelProperty(value = "学生学号")
    private String studentNo;
    @ExcelProperty(value = "奖项id")
    private Integer awardId;
    @ExcelProperty(value = "获奖等级")
    private String grade;
    @ExcelProperty(value = "获奖日期")
    private Date awardDate;
    @ExcelProperty(value = "备注")
    private String remarks;

}