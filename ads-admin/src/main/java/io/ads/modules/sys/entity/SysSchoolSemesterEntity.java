package io.ads.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 学期管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-12-01
 */
@Data
@TableName("sys_school_semester")
public class SysSchoolSemesterEntity {

    /**
     * id
     */
	private Long id;
    /**
     * 学校ID
     */
	private Long schoolId;
    /**
     * 学期开始日期
     */
    private Date startDate;
    /**
     * 学期结束日期
     */
    private Date endDate;

    /**
     * 学期名称
     */
	private String semesterName;
    /**
     * 备注
     */
	private String remark;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	private Date createDate;
}
