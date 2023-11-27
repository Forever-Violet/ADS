package io.ads.modules.analysis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 五育分析 结果（报告）表
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-25
 */
@Data
public class WuyuAnalysisResultExcel {
    @ExcelProperty(value = "主键")
    private Long id;
    @ExcelProperty(value = "班级ID")
    private Long classId;
    @ExcelProperty(value = "五育成绩ID")
    private Long scoreId;
    @ExcelProperty(value = "学生学号")
    private String studentNo;
    @ExcelProperty(value = "学生姓名")
    private String studentName;
    @ExcelProperty(value = "诊断结果")
    private String response;
    @ExcelProperty(value = "创建时间")
    private Date createDate;

}