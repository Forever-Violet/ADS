package io.ads.modules.analysis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
	private Integer id;

	@ApiModelProperty(value = "学生学号")
	private String studentNo;

	@ApiModelProperty(value = "奖项id")
	private Integer awardId;

	@ApiModelProperty(value = "获奖等级")
	private String grade;

	@ApiModelProperty(value = "获奖日期")
	private Date awardDate;

	@ApiModelProperty(value = "备注")
	private String remarks;


}