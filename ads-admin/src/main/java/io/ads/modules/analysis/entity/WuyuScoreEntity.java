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
	private Integer characterEthics;
    /**
     * 奖惩记录
     */
	private Integer rewardsPunishments;
    /**
     * 德育课程
     */
	private Integer moralEducationCourses;
    /**
     * 实践活动
     */
	private Integer practicalActivities;
    /**
     * 网络文化
     */
	private Integer onlineCulture;
    /**
     * 人际关系
     */
	private Integer interpersonalRelationships;
    /**
     * 预习管理
     */
	private Integer prepManagement;
    /**
     * 计划管理
     */
	private Integer planManagement;
    /**
     * 课堂行为
     */
	private Integer classroomBehavior;
    /**
     * 课堂考勤
     */
	private Integer classroomAttendance;
    /**
     * 作业管理
     */
	private Integer homeworkManagement;
    /**
     * 复习管理
     */
	private Integer reviewManagement;
    /**
     * 个人能力
     */
	private Integer personalAbilities;
    /**
     * 学业成绩
     */
	private Integer academicPerformance;
    /**
     * 实验竞赛
     */
	private Integer experimentalCompetitions;
    /**
     * 体检指标
     */
	private Integer examinationMetrics;
    /**
     * 体能成绩
     */
	private Integer physicalFitnessScores;
    /**
     * 体育特长
     */
	private Integer sportingSpecialties;
    /**
     * 健康生活
     */
	private Integer healthyLiving;
    /**
     * 心理素质
     */
	private Integer mentalQualities;
    /**
     * 体育课程
     */
	private Integer physicalEducationCourses;
    /**
     * 美育课程
     */
	private Integer artsCourses;
    /**
     * 美育成果
     */
	private Integer artsAchievements;
    /**
     * 美育活动
     */
	private Integer artsActivities;
    /**
     * 劳动实践
     */
	private Integer laborPractices;
    /**
     * 劳动课程
     */
	private Integer laborCourses;
    /**
     * 五育综合成绩
     */
	private Integer comprehensiveScore;
    /**
     * 综合等级 0优，1中, 2差
     */
    private Integer comprehensiveLevel;
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
