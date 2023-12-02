package io.ads.modules.analysis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
@TableName("award_settings")
public class AwardSettingsEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 所属五育 0德育, 1智育, 2体育, 3美育, 4劳育
     */
	private Integer topic;
    /**
     * 学校id
     */
    private Long schoolId;
    /**
     * 所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践
     */
	private Integer subtopic;
    /**
     * 级别 0国家级, 1省级, 2市厅级, 3区级, 4校级
     */
	private Integer level;
    /**
     * 奖项名称
     */
	private String awardName;
    /**
     * 等级, 自定义
     */
	private String grade;
    /**
     * 备注
     */
	private String remarks;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}
