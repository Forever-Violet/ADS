package io.ads.modules.sys.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 学期管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-12-01
 */
@Data
@ApiModel(value = "学期管理")
public class SysSchoolSemesterDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "学校ID")
	private Long schoolId;

	@ApiModelProperty(value = "学期开始日期")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd") //显式的指定一个日期转换格式
	private Date startDate;

	@ApiModelProperty(value = "学期结束日期")
	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd") //显式的指定一个日期转换格式
	private Date endDate;


	@ApiModelProperty(value = "学期名称")
	private String semesterName;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;


}
