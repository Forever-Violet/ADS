package io.ads.modules.analysis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
public class WuyuScoreExcel {
    @ExcelProperty(value = "主键")
    private Integer id;
    @ExcelProperty(value = "五育权重表id")
    private Integer weightId;
    @ExcelProperty(value = "学生学号")
    private String studentNo;
    @ExcelProperty(value = "学生姓名")
    private String studentName;
    @ExcelProperty(value = "品德评定")
    private BigDecimal characterEthics;
    @ExcelProperty(value = "奖惩记录")
    private BigDecimal rewardsPunishments;
    @ExcelProperty(value = "德育课程")
    private BigDecimal moralEducationCourses;
    @ExcelProperty(value = "实践活动")
    private BigDecimal practicalActivities;
    @ExcelProperty(value = "网络文化")
    private BigDecimal onlineCulture;
    @ExcelProperty(value = "人际关系")
    private BigDecimal interpersonalRelationships;
    @ExcelProperty(value = "预习管理")
    private BigDecimal prepManagement;
    @ExcelProperty(value = "计划管理")
    private BigDecimal planManagement;
    @ExcelProperty(value = "课堂行为")
    private BigDecimal classroomBehavior;
    @ExcelProperty(value = "课堂考勤")
    private BigDecimal classroomAttendance;
    @ExcelProperty(value = "作业管理")
    private BigDecimal homeworkManagement;
    @ExcelProperty(value = "复习管理")
    private BigDecimal reviewManagement;
    @ExcelProperty(value = "个人能力")
    private BigDecimal personalAbilities;
    @ExcelProperty(value = "学业成绩")
    private BigDecimal academicPerformance;
    @ExcelProperty(value = "实验竞赛")
    private BigDecimal experimentalCompetitions;
    @ExcelProperty(value = "体检指标")
    private BigDecimal examinationMetrics;
    @ExcelProperty(value = "体能成绩")
    private BigDecimal physicalFitnessScores;
    @ExcelProperty(value = "体育特长")
    private BigDecimal sportingSpecialties;
    @ExcelProperty(value = "健康生活")
    private BigDecimal healthyLiving;
    @ExcelProperty(value = "心理素质")
    private BigDecimal mentalQualities;
    @ExcelProperty(value = "体育课程")
    private BigDecimal physicalEducationCourses;
    @ExcelProperty(value = "美育课程")
    private BigDecimal artsCourses;
    @ExcelProperty(value = "美育成果")
    private BigDecimal artsAchievements;
    @ExcelProperty(value = "美育活动")
    private BigDecimal artsActivities;
    @ExcelProperty(value = "劳动实践")
    private BigDecimal laborPractices;
    @ExcelProperty(value = "劳动课程")
    private BigDecimal laborCourses;
    @ExcelProperty(value = "五育综合成绩")
    private BigDecimal comprehensiveScore;
    @ExcelProperty(value = "学业等级 0优, 1中, 2差")
    private Integer academicLevel;

}