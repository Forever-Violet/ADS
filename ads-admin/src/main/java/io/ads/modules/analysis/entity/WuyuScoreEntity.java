package io.ads.modules.analysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("wuyu_score")
public class WuyuScoreEntity {

    /**
     * 主键
     */
	private Integer id;
    /**
     * 五育权重表id
     */
	private Integer weightId;
    /**
     * 学生学号
     */
	private String studentNo;
    /**
     * 学生姓名
     */
	private String studentName;
    /**
     * 品德评定
     */
	private BigDecimal characterEthics;
    /**
     * 奖惩记录
     */
	private BigDecimal rewardsPunishments;
    /**
     * 德育课程
     */
	private BigDecimal moralEducationCourses;
    /**
     * 实践活动
     */
	private BigDecimal practicalActivities;
    /**
     * 网络文化
     */
	private BigDecimal onlineCulture;
    /**
     * 人际关系
     */
	private BigDecimal interpersonalRelationships;
    /**
     * 预习管理
     */
	private BigDecimal prepManagement;
    /**
     * 计划管理
     */
	private BigDecimal planManagement;
    /**
     * 课堂行为
     */
	private BigDecimal classroomBehavior;
    /**
     * 课堂考勤
     */
	private BigDecimal classroomAttendance;
    /**
     * 作业管理
     */
	private BigDecimal homeworkManagement;
    /**
     * 复习管理
     */
	private BigDecimal reviewManagement;
    /**
     * 个人能力
     */
	private BigDecimal personalAbilities;
    /**
     * 学业成绩
     */
	private BigDecimal academicPerformance;
    /**
     * 实验竞赛
     */
	private BigDecimal experimentalCompetitions;
    /**
     * 体检指标
     */
	private BigDecimal examinationMetrics;
    /**
     * 体能成绩
     */
	private BigDecimal physicalFitnessScores;
    /**
     * 体育特长
     */
	private BigDecimal sportingSpecialties;
    /**
     * 健康生活
     */
	private BigDecimal healthyLiving;
    /**
     * 心理素质
     */
	private BigDecimal mentalQualities;
    /**
     * 体育课程
     */
	private BigDecimal physicalEducationCourses;
    /**
     * 美育课程
     */
	private BigDecimal artsCourses;
    /**
     * 美育成果
     */
	private BigDecimal artsAchievements;
    /**
     * 美育活动
     */
	private BigDecimal artsActivities;
    /**
     * 劳动实践
     */
	private BigDecimal laborPractices;
    /**
     * 劳动课程
     */
	private BigDecimal laborCourses;
    /**
     * 五育综合成绩
     */
	private BigDecimal comprehensiveScore;
    /**
     * 学业等级 0优, 1中, 2差
     */
	private Integer academicLevel;
}