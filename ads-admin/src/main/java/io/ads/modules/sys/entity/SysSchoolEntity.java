package io.ads.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 学校管理
 *
 * @author 12508
 * @since 1.0.0 2023-10-28
 */
@Data
@TableName("sys_school")
public class SysSchoolEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 学校ID
     */
    //不加会报错，主键列名不是 id，使用MP自带的方法会出现invalid bound statement错误, 因为它默认主键列名为id, 所以加上这个注解指定主键列名
    @TableId("school_id")
	private Long schoolId;
    /**
     * 学校名称
     */
	private String schoolName;
    /**
     * 别名
     */
	private String alias;
    /**
     * 全称
     */
	private String fullName;
    /**
     * 状态 0正常 1禁用
     */
	private Integer status;
    /**
     * 所属教育局
     */
	private String educationBureau;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	private Date createDate;
    /**
     * 单位类型 0省级 1市级 2区县级
     */
	private Integer unitType;
}
