package io.ads.modules.sys.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 学校管理
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-10-28
 */
@Data
@ApiModel(value = "学校管理")
public class SysSchoolDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "学校ID")
	private Long schoolId;

	@ApiModelProperty(value = "学校名称")
	private String schoolName;

	@ApiModelProperty(value = "别名")
	private String alias;

	@ApiModelProperty(value = "全称")
	private String fullName;

	@ApiModelProperty(value = "状态 0正常 1禁用")
	private Integer status;

	@ApiModelProperty(value = "所属教育局")
	private String educationBureau;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "单位类型 0省级 1市级 2区县级")
	private Integer unitType;


}
