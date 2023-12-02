package io.ads.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 班级管理
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-29
 */
@Data
@TableName("sys_school_class")
public class SysSchoolClassEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;
    /**
     * 学校ID
     */
    private Long schoolId;
    /**
     * 年级ID
     */
    private Long gradeId;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;

    /**
     * 学校名称 （额外字段）
     */
    @TableField(exist = false)
    private String schoolName;

    /**
     * 年级名称 （额外字段）
     */
    @TableField(exist = false)
    private String gradeName;
}
