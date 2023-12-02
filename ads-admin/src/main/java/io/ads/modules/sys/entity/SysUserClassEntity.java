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
@TableName("sys_user_class")
public class SysUserClassEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	private Long id;
    /**
     * 用户ID
     */
	private Long userId;
    /**
     * 班级ID
     */
	private Long classId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	private Date createDate;
}
