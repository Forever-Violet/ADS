package io.ads.modules.analysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;


/**
 * 奖项设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
@ApiModel(value = "奖项设置")
public class AwardSettingsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "学校id")
	private Long schoolId;

	@ApiModelProperty(value = "所属五育 0德育, 1智育, 2体育, 3美育, 4劳育")
	private Integer topic;

	@ApiModelProperty(value = "所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践")
	private Integer subtopic;

	@ApiModelProperty(value = "级别 0国家级, 1省级, 2市厅级, 3区级, 4校级")
	private Integer level;

	@ApiModelProperty(value = "奖项名称")
	@Length(max = 100, min = 0, message = "奖项名称超过字数限制（100字）！")
	private String awardName;

	@ApiModelProperty(value = "等级, 自定义")
	@Length(max = 100, min = 0, message = "超过等级字数限制！")
	private String grade;

	@ApiModelProperty(value = "备注")
	@Length(max = 255, min = 0, message = "超过备注字数限制！")
	private String remarks;

	@ApiModelProperty(value = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;

}
