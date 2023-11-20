package io.ads.modules.analysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 五育设置（五育权重表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
@ApiModel(value = "五育设置（五育权重表）")
public class WuyuWeightDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long id;

	@ApiModelProperty(value = "学校ID")
	private Long schoolId;

	@ApiModelProperty(value = "德育")
	private Float moral;

	@ApiModelProperty(value = "智育")
	private Float intellectual;

	@ApiModelProperty(value = "体育")
	private Float physical;

	@ApiModelProperty(value = "美育")
	private Float artistic;

	@ApiModelProperty(value = "劳育")
	private Float labor;

	@ApiModelProperty(value = "品德评定")
	private Float characterEthics;

	@ApiModelProperty(value = "奖惩记录")
	private Float rewardsPunishments;

	@ApiModelProperty(value = "德育课程")
	private Float moralEducationCourses;

	@ApiModelProperty(value = "实践活动")
	private Float practicalActivities;

	@ApiModelProperty(value = "网络文化")
	private Float onlineCulture;

	@ApiModelProperty(value = "人际关系")
	private Float interpersonalRelationships;

	@ApiModelProperty(value = "预习管理")
	private Float prepManagement;

	@ApiModelProperty(value = "计划管理")
	private Float planManagement;

	@ApiModelProperty(value = "课堂行为")
	private Float classroomBehavior;

	@ApiModelProperty(value = "课堂考勤")
	private Float classroomAttendance;

	@ApiModelProperty(value = "作业管理")
	private Float homeworkManagement;

	@ApiModelProperty(value = "复习管理")
	private Float reviewManagement;

	@ApiModelProperty(value = "个人能力")
	private Float personalAbilities;

	@ApiModelProperty(value = "学业成绩")
	private Float academicPerformance;

	@ApiModelProperty(value = "实验竞赛")
	private Float experimentalCompetitions;

	@ApiModelProperty(value = "体检指标")
	private Float examinationMetrics;

	@ApiModelProperty(value = "体能成绩")
	private Float physicalFitnessScores;

	@ApiModelProperty(value = "体育特长")
	private Float sportingSpecialties;

	@ApiModelProperty(value = "健康生活")
	private Float healthyLiving;

	@ApiModelProperty(value = "心理素质")
	private Float mentalQualities;

	@ApiModelProperty(value = "体育课程")
	private Float physicalEducationCourses;

	@ApiModelProperty(value = "美育课程")
	private Float artsCourses;

	@ApiModelProperty(value = "美育成果")
	private Float artsAchievements;

	@ApiModelProperty(value = "美育活动")
	private Float artsActivities;

	@ApiModelProperty(value = "劳动实践")
	private Float laborPractices;

	@ApiModelProperty(value = "劳动课程")
	private Float laborCourses;

	@ApiModelProperty(value = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;

}
