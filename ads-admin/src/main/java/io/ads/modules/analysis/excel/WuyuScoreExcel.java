package io.ads.modules.analysis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.ads.modules.analysis.excel.converter.LevelConverter;
import lombok.Data;


/**
 * 五育分析（五育成绩表）导出用
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
public class WuyuScoreExcel {
/*    @ExcelProperty(value = "主键")
    private Long id;
    @ExcelProperty(value = "五育权重表id")
    private int weightId;*/
    @ExcelProperty(value = "学生学号")
    private String studentNo;
    @ExcelProperty(value = "学生姓名")
    private String studentName;
    @ExcelProperty(value = "品德评定")
    private int characterEthics;
    @ExcelProperty(value = "奖惩记录")
    private int rewardsPunishments;
    @ExcelProperty(value = "德育课程")
    private int moralEducationCourses;
    @ExcelProperty(value = "实践活动")
    private int practicalActivities;
    @ExcelProperty(value = "网络文化")
    private int onlineCulture;
    @ExcelProperty(value = "人际关系")
    private int interpersonalRelationships;
    @ExcelProperty(value = "预习管理")
    private int prepManagement;
    @ExcelProperty(value = "计划管理")
    private int planManagement;
    @ExcelProperty(value = "课堂行为")
    private int classroomBehavior;
    @ExcelProperty(value = "课堂考勤")
    private int classroomAttendance;
    @ExcelProperty(value = "作业管理")
    private int homeworkManagement;
    @ExcelProperty(value = "复习管理")
    private int reviewManagement;
    @ExcelProperty(value = "个人能力")
    private int personalAbilities;
    @ExcelProperty(value = "学业成绩")
    private int academicPerformance;
    @ExcelProperty(value = "实验竞赛")
    private int experimentalCompetitions;
    @ExcelProperty(value = "体检指标")
    private int examinationMetrics;
    @ExcelProperty(value = "体能成绩")
    private int physicalFitnessScores;
    @ExcelProperty(value = "体育特长")
    private int sportingSpecialties;
    @ExcelProperty(value = "健康生活")
    private int healthyLiving;
    @ExcelProperty(value = "心理素质")
    private int mentalQualities;
    @ExcelProperty(value = "体育课程")
    private int physicalEducationCourses;
    @ExcelProperty(value = "美育课程")
    private int artsCourses;
    @ExcelProperty(value = "美育成果")
    private int artsAchievements;
    @ExcelProperty(value = "美育活动")
    private int artsActivities;
    @ExcelProperty(value = "劳动实践")
    private int laborPractices;
    @ExcelProperty(value = "劳动课程")
    private int laborCourses;
    @ExcelProperty(value = "五育综合成绩")
    private int comprehensiveScore;
    @ExcelProperty(value = "综合等级", converter = LevelConverter.class)
    private int comprehensiveLevel;
    @ExcelProperty(value = "学业等级", converter = LevelConverter.class)
    private int academicLevel;

}
