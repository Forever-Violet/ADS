package io.ads.modules.analysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;


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
	private Long id;

	@ApiModelProperty(value = "学校id")
	private Long schoolId;

	@ApiModelProperty(value = "学期id")
	private Long semesterId;

	@ApiModelProperty(value = "年级id")
	private Long gradeId;

	@ApiModelProperty(value = "班级id")
	private Long classId;

	@ApiModelProperty(value = "诊断报告id")
	private Long reportId;

	@ApiModelProperty(value = "学生学号")
	private String studentNo;

	@ApiModelProperty(value = "学生姓名")
	private String studentName;

	@ApiModelProperty(value = "品德评定")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer characterEthics;

	@ApiModelProperty(value = "奖惩记录")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer rewardsPunishments;

	@ApiModelProperty(value = "德育课程")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer moralEducationCourses;

	@ApiModelProperty(value = "实践活动")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer practicalActivities;

	@ApiModelProperty(value = "网络文化")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer onlineCulture;

	@ApiModelProperty(value = "人际关系")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer interpersonalRelationships;


	@ApiModelProperty(value = "预习管理")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer prepManagement;

	@ApiModelProperty(value = "计划管理")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer planManagement;

	@ApiModelProperty(value = "课堂行为")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer classroomBehavior;

	@ApiModelProperty(value = "课堂考勤")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer classroomAttendance;

	@ApiModelProperty(value = "作业管理")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer homeworkManagement;

	@ApiModelProperty(value = "复习管理")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer reviewManagement;

	@ApiModelProperty(value = "个人能力")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer personalAbilities;

	@ApiModelProperty(value = "学业成绩")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer academicPerformance;

	@ApiModelProperty(value = "实验竞赛")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer experimentalCompetitions;


	@ApiModelProperty(value = "体检指标")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer examinationMetrics;

	@ApiModelProperty(value = "体能成绩")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer physicalFitnessScores;

	@ApiModelProperty(value = "体育特长")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer sportingSpecialties;

	@ApiModelProperty(value = "健康生活")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer healthyLiving;

	@ApiModelProperty(value = "心理素质")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer mentalQualities;

	@ApiModelProperty(value = "体育课程")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer physicalEducationCourses;


	@ApiModelProperty(value = "美育课程")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer artsCourses;

	@ApiModelProperty(value = "美育成果")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer artsAchievements;

	@ApiModelProperty(value = "美育活动")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer artsActivities;


	@ApiModelProperty(value = "劳动实践")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer laborPractices;

	@ApiModelProperty(value = "劳动课程")
	@Max(value = 100, message = "分数必须小于等于100")
	@Min(value = 0, message = "分数必须大于等于0")
	private Integer laborCourses;

	@ApiModelProperty(value = "五育综合成绩")
	private Integer comprehensiveScore;

	@ApiModelProperty(value = "五育综合等级 0优, 1中, 2差")
	private Integer comprehensiveLevel;

	@ApiModelProperty(value = "学业等级 0优, 1中, 2差")
	private Integer academicLevel;

	@ApiModelProperty(value = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;


}
