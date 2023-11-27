package io.ads.modules.analysis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.ads.modules.analysis.excel.converter.AwardLevelConverter;
import io.ads.modules.analysis.excel.converter.AwardSubtopicConverter;
import io.ads.modules.analysis.excel.converter.AwardTopicConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
public class AwardRecordsExcel {

    @ExcelProperty(value = "学生学号")
    private String studentNo;

    @ExcelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 所属五育 0德育, 1智育, 2体育, 3美育, 4劳育
     */
    @ExcelProperty(value = "所属五育", converter = AwardTopicConverter.class)
    private Integer topic;
    /**
     * 所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践
     */
    @ExcelProperty(value = "所属小类", converter = AwardSubtopicConverter.class)
    private Integer subtopic;
    /**
     * 级别 0国家级, 1省级, 2市厅级, 3区级, 4校级
     */
    @ExcelProperty(value = "级别", converter = AwardLevelConverter.class)
    private Integer level;
    /**
     * 奖项名称
     */
    @ExcelProperty(value = "奖项名称")
    @Length(max = 100, min = 0, message = "奖项名称超过字数限制（100字）！")
    private String awardName;

    @ExcelProperty(value = "获奖等级")
    private String grade;
    @ExcelProperty(value = "获奖日期")
    private Date awardDate;
    @ExcelProperty(value = "备注")
    private String remarks;

}
