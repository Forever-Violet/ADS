package io.ads.modules.analysis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;


/**
 * 五育分析 结果（报告）表
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-25
 */
@Data
@ApiModel(value = "五育分析 结果（报告）表")
public class WuyuAnalysisResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "班级ID")
	private Long classId;

	@ApiModelProperty(value = "学期ID")
	private Long semesterId;

	@ApiModelProperty(value = "五育成绩ID")
	private Long scoreId;

	@ApiModelProperty(value = "学生学号")
	private String studentNo;

	@ApiModelProperty(value = "学生姓名")
	private String studentName;

	@ApiModelProperty(value = "诊断结果")
	@Length(max = 3000, min = 0, message = "超过字数限制（3000字）！")
	private String response;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;


}
