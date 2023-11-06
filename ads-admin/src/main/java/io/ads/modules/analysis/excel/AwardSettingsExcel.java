package io.ads.modules.analysis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Date;

/**
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
public class AwardSettingsExcel {
    @ExcelProperty(value = "主键")
    private Integer id;
    @ExcelProperty(value = "所属五育 0德育, 1智育, 2体育, 3美育, 4劳育")
    private Integer topic;
    @ExcelProperty(value = "所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践")
    private Integer subtopic;
    @ExcelProperty(value = "级别 0国家级, 1省级, 2市厅级, 3区级, 4校级")
    private Integer level;
    @ExcelProperty(value = "奖项名称")
    private String awardName;
    @ExcelProperty(value = "等级, 自定义")
    private String grade;
    @ExcelProperty(value = "备注")
    private String remarks;

}