package io.ads.modules.sys.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 学期管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-12-01
 */
@Data
public class SysSchoolSemesterExcel {
    @ExcelProperty(value = "id")
    private Long id;
    @ExcelProperty(value = "学校ID")
    private Long schoolId;
    @ExcelProperty(value = "学期名称")
    private String semesterName;
    @ExcelProperty(value = "备注")
    private String remark;
    @ExcelProperty(value = "创建时间")
    private Date createDate;

}