package io.ads.modules.analysis.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 五育班级分析报告
 * @author 12508
 */
@Data
@ApiModel(value = "五育班级报告dto")
public class WuyuClassAnalysisResultDTO extends WuyuAnalysisResultDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "年级名称")
    private String gradeName;

    @ApiModelProperty(value = "班级人数")
    private Integer studentNum;

    @ApiModelProperty(value = "德育平均分")
    private Integer moralScore;

    @ApiModelProperty(value = "智育平均分")
    private Integer intellectualScore;

    @ApiModelProperty(value = "体育平均分")
    private Integer physicalScore;

    @ApiModelProperty(value = "美育平均分")
    private Integer artisticScore;

    @ApiModelProperty(value = "劳育平均分")
    private Integer laborScore;

    @ApiModelProperty(value = "五育等级为差的人数")
    private Integer lowLevelNum;

    @ApiModelProperty(value = "五育等级为中的人数")
    private Integer middleLevelNum;

    @ApiModelProperty(value = "五育等级为优的人数")
    private Integer highLevelNum;

}
