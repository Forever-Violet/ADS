package io.ads.modules.analysis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import java.math.BigDecimal;

/**
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
@ApiModel(value = "五育分析（五育成绩表）")
public class WuyuScoreDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "五育权重表id")
	private Integer weightId;

	@ApiModelProperty(value = "学生学号")
	private String studentNo;

	@ApiModelProperty(value = "学生姓名")
	private String studentName;

	@ApiModelProperty(value = "品德评定")
	private BigDecimal characterEthics;

	@ApiModelProperty(value = "奖惩记录")
	private BigDecimal rewardsPunishments;

	@ApiModelProperty(value = "德育课程")
	private BigDecimal moralEducationCourses;

	@ApiModelProperty(value = "实践活动")
	private BigDecimal practicalActivities;

	@ApiModelProperty(value = "网络文化")
	private BigDecimal onlineCulture;

	@ApiModelProperty(value = "人际关系")
	private BigDecimal interpersonalRelationships;

	@ApiModelProperty(value = "预习管理")
	private BigDecimal prepManagement;

	@ApiModelProperty(value = "计划管理")
	private BigDecimal planManagement;

	@ApiModelProperty(value = "课堂行为")
	private BigDecimal classroomBehavior;

	@ApiModelProperty(value = "课堂考勤")
	private BigDecimal classroomAttendance;

	@ApiModelProperty(value = "作业管理")
	private BigDecimal homeworkManagement;

	@ApiModelProperty(value = "复习管理")
	private BigDecimal reviewManagement;

	@ApiModelProperty(value = "个人能力")
	private BigDecimal personalAbilities;

	@ApiModelProperty(value = "学业成绩")
	private BigDecimal academicPerformance;

	@ApiModelProperty(value = "实验竞赛")
	private BigDecimal experimentalCompetitions;

	@ApiModelProperty(value = "体检指标")
	private BigDecimal examinationMetrics;

	@ApiModelProperty(value = "体能成绩")
	private BigDecimal physicalFitnessScores;

	@ApiModelProperty(value = "体育特长")
	private BigDecimal sportingSpecialties;

	@ApiModelProperty(value = "健康生活")
	private BigDecimal healthyLiving;

	@ApiModelProperty(value = "心理素质")
	private BigDecimal mentalQualities;

	@ApiModelProperty(value = "体育课程")
	private BigDecimal physicalEducationCourses;

	@ApiModelProperty(value = "美育课程")
	private BigDecimal artsCourses;

	@ApiModelProperty(value = "美育成果")
	private BigDecimal artsAchievements;

	@ApiModelProperty(value = "美育活动")
	private BigDecimal artsActivities;

	@ApiModelProperty(value = "劳动实践")
	private BigDecimal laborPractices;

	@ApiModelProperty(value = "劳动课程")
	private BigDecimal laborCourses;

	@ApiModelProperty(value = "五育综合成绩")
	private BigDecimal comprehensiveScore;

	@ApiModelProperty(value = "学业等级 0优, 1中, 2差")
	private Integer academicLevel;


}