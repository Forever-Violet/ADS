package io.ads.modules.analysis.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.ads.modules.analysis.excel.converter.LevelConverter;
import lombok.Data;

import java.lang.Integer;


/**
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
public class WuyuScoreExcel {
/*    @ExcelProperty(value = "主键")
    private Integer id;
    @ExcelProperty(value = "五育权重表id")
    private Integer weightId;*/
    @ExcelProperty(value = "学生学号")
    private String studentNo;
    @ExcelProperty(value = "学生姓名")
    private String studentName;
    @ExcelProperty(value = "品德评定")
    private Integer characterEthics;
    @ExcelProperty(value = "奖惩记录")
    private Integer rewardsPunishments;
    @ExcelProperty(value = "德育课程")
    private Integer moralEducationCourses;
    @ExcelProperty(value = "实践活动")
    private Integer practicalActivities;
    @ExcelProperty(value = "网络文化")
    private Integer onlineCulture;
    @ExcelProperty(value = "人际关系")
    private Integer interpersonalRelationships;
    @ExcelProperty(value = "预习管理")
    private Integer prepManagement;
    @ExcelProperty(value = "计划管理")
    private Integer planManagement;
    @ExcelProperty(value = "课堂行为")
    private Integer classroomBehavior;
    @ExcelProperty(value = "课堂考勤")
    private Integer classroomAttendance;
    @ExcelProperty(value = "作业管理")
    private Integer homeworkManagement;
    @ExcelProperty(value = "复习管理")
    private Integer reviewManagement;
    @ExcelProperty(value = "个人能力")
    private Integer personalAbilities;
    @ExcelProperty(value = "学业成绩")
    private Integer academicPerformance;
    @ExcelProperty(value = "实验竞赛")
    private Integer experimentalCompetitions;
    @ExcelProperty(value = "体检指标")
    private Integer examinationMetrics;
    @ExcelProperty(value = "体能成绩")
    private Integer physicalFitnessScores;
    @ExcelProperty(value = "体育特长")
    private Integer sportingSpecialties;
    @ExcelProperty(value = "健康生活")
    private Integer healthyLiving;
    @ExcelProperty(value = "心理素质")
    private Integer mentalQualities;
    @ExcelProperty(value = "体育课程")
    private Integer physicalEducationCourses;
    @ExcelProperty(value = "美育课程")
    private Integer artsCourses;
    @ExcelProperty(value = "美育成果")
    private Integer artsAchievements;
    @ExcelProperty(value = "美育活动")
    private Integer artsActivities;
    @ExcelProperty(value = "劳动实践")
    private Integer laborPractices;
    @ExcelProperty(value = "劳动课程")
    private Integer laborCourses;
    @ExcelProperty(value = "五育综合成绩")
    private Integer comprehensiveScore;
    @ExcelProperty(value = "综合等级", converter = LevelConverter.class)
    private Integer comprehensiveLevel;
    @ExcelProperty(value = "学业等级", converter = LevelConverter.class)
    private Integer academicLevel;

}
