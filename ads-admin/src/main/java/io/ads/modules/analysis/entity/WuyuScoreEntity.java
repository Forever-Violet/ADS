package io.ads.modules.analysis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
	private Long id;
    /**
     * 学校id
     */
	private Long schoolId;
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
	private Float characterEthics;
    /**
     * 奖惩记录
     */
	private Float rewardsPunishments;
    /**
     * 德育课程
     */
	private Float moralEducationCourses;
    /**
     * 实践活动
     */
	private Float practicalActivities;
    /**
     * 网络文化
     */
	private Float onlineCulture;
    /**
     * 人际关系
     */
	private Float interpersonalRelationships;
    /**
     * 预习管理
     */
	private Float prepManagement;
    /**
     * 计划管理
     */
	private Float planManagement;
    /**
     * 课堂行为
     */
	private Float classroomBehavior;
    /**
     * 课堂考勤
     */
	private Float classroomAttendance;
    /**
     * 作业管理
     */
	private Float homeworkManagement;
    /**
     * 复习管理
     */
	private Float reviewManagement;
    /**
     * 个人能力
     */
	private Float personalAbilities;
    /**
     * 学业成绩
     */
	private Float academicPerformance;
    /**
     * 实验竞赛
     */
	private Float experimentalCompetitions;
    /**
     * 体检指标
     */
	private Float examinationMetrics;
    /**
     * 体能成绩
     */
	private Float physicalFitnessScores;
    /**
     * 体育特长
     */
	private Float sportingSpecialties;
    /**
     * 健康生活
     */
	private Float healthyLiving;
    /**
     * 心理素质
     */
	private Float mentalQualities;
    /**
     * 体育课程
     */
	private Float physicalEducationCourses;
    /**
     * 美育课程
     */
	private Float artsCourses;
    /**
     * 美育成果
     */
	private Float artsAchievements;
    /**
     * 美育活动
     */
	private Float artsActivities;
    /**
     * 劳动实践
     */
	private Float laborPractices;
    /**
     * 劳动课程
     */
	private Float laborCourses;
    /**
     * 五育综合成绩
     */
	private Float comprehensiveScore;
    /**
     * 学业等级 0优, 1中, 2差
     */
	private Integer academicLevel;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}
