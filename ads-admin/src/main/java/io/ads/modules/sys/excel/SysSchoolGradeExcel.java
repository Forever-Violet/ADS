package io.ads.modules.sys.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 年级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Data
public class SysSchoolGradeExcel {
    @ExcelProperty(value = "id")
    private Long id;
    @ExcelProperty(value = "学校ID")
    private Long schoolId;
    @ExcelProperty(value = "年级名称")
    private String gradeName;
    @ExcelProperty(value = "备注")
    private String remark;
    @ExcelProperty(value = "创建时间")
    private Date createDate;

}