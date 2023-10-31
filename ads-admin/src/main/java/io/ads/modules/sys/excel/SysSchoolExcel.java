package io.ads.modules.sys.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 学校管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-10-28
 */
@Data
public class SysSchoolExcel {
    @ExcelProperty(value = "学校ID")
    private Long schoolId;
    @ExcelProperty(value = "学校名称")
    private String schoolName;
    @ExcelProperty(value = "别名")
    private String alias;
    @ExcelProperty(value = "全称")
    private String fullName;
    @ExcelProperty(value = "状态 0正常 1禁用")
    private String status;
    @ExcelProperty(value = "所属教育局")
    private String educationBureau;
    @ExcelProperty(value = "创建时间")
    private Date createDate;
    @ExcelProperty(value = "单位类型 0省级 1市级 2区县级")
    private String unitType;

}
