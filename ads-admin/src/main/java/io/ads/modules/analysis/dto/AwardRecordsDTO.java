package io.ads.modules.analysis.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;


/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
@ApiModel(value = "获奖记录设置")
public class AwardRecordsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "学生学号")
	private String studentNo;

	@ApiModelProperty(value = "学生姓名")
	private String studentName;

	@ApiModelProperty(value = "奖项id")
	private Long awardId;

	@ApiModelProperty(value = "获奖等级")
	private String grade;

	@ApiModelProperty(value = "获奖日期")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd") //显式的指定一个日期转换格式
	private Date awardDate;

	@ApiModelProperty(value = "备注")
	@Length(max = 255, min = 0, message = "备注超过字数限制（255字）！")
	private String remarks;

	@ApiModelProperty(value = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;

	/**
	 * 所属五育 0德育, 1智育, 2体育, 3美育, 4劳育
	 */
	@ApiModelProperty(value = "所属五育")
	private Integer topic;
	/**
	 * 所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践
	 */
	@ApiModelProperty(value = "所属小类")
	private Integer subtopic;
	/**
	 * 级别 0国家级, 1省级, 2市厅级, 3区级, 4校级
	 */
	@ApiModelProperty(value = "级别")
	private Integer level;
	/**
	 * 奖项名称
	 */
	@ApiModelProperty(value = "奖项名称")
	@Length(max = 100, min = 0, message = "奖项名称超过字数限制（100字）！")
	private String awardName;




}
