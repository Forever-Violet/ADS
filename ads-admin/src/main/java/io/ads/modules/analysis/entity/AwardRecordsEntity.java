package io.ads.modules.analysis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
@TableName("award_records")
public class AwardRecordsEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 学生学号
     */
	private String studentNo;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 奖项id
     */
	private Long awardId;
    /**
     * 获奖等级
     */
	private String grade;
    /**
     * 获奖日期
     */
	private Date awardDate;
    /**
     * 备注
     */
	private String remarks;

    /**
     * 所属五育 0德育, 1智育, 2体育, 3美育, 4劳育
     */
    @TableField(exist = false)
    private Integer topic;

    /**
     * 所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践
     */
    @TableField(exist = false)
    private Integer subtopic;
    /**
     * 级别 0国家级, 1省级, 2市厅级, 3区级, 4校级
     */
    @TableField(exist = false)
    private Integer level;
    /**
     * 奖项名称
     */
    @TableField(exist = false)
    private String awardName;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

}
