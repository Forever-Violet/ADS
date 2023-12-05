package io.ads.modules.analysis.service;

import io.ads.common.service.CrudService;
import io.ads.modules.analysis.dto.WuyuAnalysisResultDTO;
import io.ads.modules.analysis.dto.WuyuClassAnalysisResultDTO;
import io.ads.modules.analysis.dto.WuyuScoreDTO;
import io.ads.modules.analysis.entity.WuyuScoreEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 五育分析（五育成绩表）
 *
 * @author Roxy 1250812574@qq.com
 * @since 1.0.0 2023-11-03
 */
public interface WuyuScoreService extends CrudService<WuyuScoreEntity, WuyuScoreDTO> {

    void save(WuyuScoreDTO dto);


    /**
     * 生成个人诊断报告，若已存在则直接获取
     * @param id 五育分数id
     * @return WuyuAnalysisResultDTO
     */
    WuyuAnalysisResultDTO genOrGetAnalysisReport(Long id);

    /**
     * 重新生成个人诊断报告，若不存在则直接重新生成
     * @param id 五育分数id
     */
    void reGenAnalysisReport(Long id);

    /**
     * 读取Excel文件中的数据并保存到数据库
     * @param file Excel文件
     */
    Map<String, Object> readExcel(MultipartFile file) throws IOException;

    /**
     * 生成班级诊断报告，若已存在则直接获取
     * @param classId 班级id
     * @param semesterId 学期id
     */
    WuyuClassAnalysisResultDTO genOrGetClassAnalysisReport(Long classId, Long semesterId);

    /**
     * 重新生成班级诊断报告，若不存在则直接重新生成
     * @param classId 班级id
     * @param semesterId 学期id
     */
    void reGenClassAnalysisReport(Long classId, Long semesterId);
}
