package io.ads.modules.analysis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 获奖记录设置
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
@Data
@TableName("award_records")
public class AwardRecordsEntity {

    /**
     * 主键
     */
	private Long id;
    /**
     * 学生学号
     */
	private String studentNo;
    /**
     * 奖项id
     */
	private Long awardId;
    /**
     * 获奖等级
     */
	private String grade;
    /**
     * 获奖日期
     */
	private Date awardDate;
    /**
     * 备注
     */
	private String remarks;
}
