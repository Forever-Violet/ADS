package io.ads.modules.sys.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 班级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Data
@ApiModel(value = "班级管理")
public class SysSchoolClassDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Long id;

	@ApiModelProperty(value = "学校ID")
	private Long schoolId;

	@ApiModelProperty(value = "年级ID")
	private Long gradeId;

	@ApiModelProperty(value = "班级名称")
	private String className;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "学校名称 （额外字段）")
	private String schoolName;

	@ApiModelProperty(value = "年级名称 （额外字段）")
	private String gradeName;


}
