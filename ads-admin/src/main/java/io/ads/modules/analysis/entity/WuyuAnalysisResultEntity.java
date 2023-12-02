package io.ads.modules.analysis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 五育分析 结果（报告）表
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-25
 */
@Data
@TableName("wuyu_analysis_result")
public class WuyuAnalysisResultEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Long id;
    /**
     * 班级ID
     */
	private Long classId;
    /**
     * 五育成绩ID
     */
	private Long scoreId;
    /**
     * 学生学号
     */
	private String studentNo;
    /**
     * 学生姓名
     */
	private String studentName;
    /**
     * 诊断结果
     */
	private String response;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
	private Date createDate;
}
