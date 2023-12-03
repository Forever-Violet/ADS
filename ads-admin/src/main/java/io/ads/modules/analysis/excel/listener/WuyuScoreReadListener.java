package io.ads.modules.analysis.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import io.ads.modules.analysis.excel.WuyuScoreExcel;
import io.ads.modules.analysis.excel.WuyuScoreImportExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 12508
 * ReadListener不能被spring管理，每次使用时直接new
 */
public class WuyuScoreReadListener implements ReadListener<WuyuScoreImportExcel> {

    // 每隔3000条就存储到数据库，然后清理list，方便内存回收。 现在先不用
    private static final int BATCH_COUNT = 3000;
    // 缓冲区
    List<WuyuScoreImportExcel> list = new ArrayList<>();

    // 实现监听器逻辑
    @Override
    public void invoke(WuyuScoreImportExcel wuyuScore, AnalysisContext context) {
        // 处理每一行数据，本来的话是在这里把每条数据存储数据库，但是我们需要存储的不是Excel对象，所以还是在Service中处理比较方便，这里就当个摆设

        //list.add(wuyuScore);
        // 达到缓冲区最大空间，去存储到数据库，防止数据太多在内存，导致OOM
/*        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储后清空缓冲区
            list.clear();
        }*/
        // 在这里校验一下每行记录的分数，如果超过100设定为100，小于0设定为0
        checkAllWuyuScore(wuyuScore);
        if (wuyuScore.getStudentName() == null) {
            wuyuScore.setStudentName(""); //如果学生名称为空，那么给它设置为空字符串
        }
    }

    private void checkAllWuyuScore(WuyuScoreImportExcel wuyuScore) {
        wuyuScore.setCharacterEthics(checkScore(wuyuScore.getCharacterEthics()));
        wuyuScore.setRewardsPunishments(checkScore(wuyuScore.getRewardsPunishments()));
        wuyuScore.setMoralEducationCourses(checkScore(wuyuScore.getMoralEducationCourses()));
        wuyuScore.setPracticalActivities(checkScore(wuyuScore.getPracticalActivities()));
        wuyuScore.setOnlineCulture(checkScore(wuyuScore.getOnlineCulture()));
        wuyuScore.setInterpersonalRelationships(checkScore(wuyuScore.getInterpersonalRelationships()));
        wuyuScore.setPrepManagement(checkScore(wuyuScore.getPrepManagement()));
        wuyuScore.setPlanManagement(checkScore(wuyuScore.getPlanManagement()));
        wuyuScore.setClassroomBehavior(checkScore(wuyuScore.getClassroomBehavior()));
        wuyuScore.setClassroomAttendance(checkScore(wuyuScore.getClassroomAttendance()));
        wuyuScore.setHomeworkManagement(checkScore(wuyuScore.getHomeworkManagement()));
        wuyuScore.setReviewManagement(checkScore(wuyuScore.getReviewManagement()));
        wuyuScore.setPersonalAbilities(checkScore(wuyuScore.getPersonalAbilities()));
        wuyuScore.setAcademicPerformance(checkScore(wuyuScore.getAcademicPerformance()));
        wuyuScore.setExperimentalCompetitions(checkScore(wuyuScore.getExperimentalCompetitions()));
        wuyuScore.setExaminationMetrics(checkScore(wuyuScore.getExaminationMetrics()));
        wuyuScore.setPhysicalFitnessScores(checkScore(wuyuScore.getPhysicalFitnessScores()));
        wuyuScore.setSportingSpecialties(checkScore(wuyuScore.getSportingSpecialties()));
        wuyuScore.setHealthyLiving(checkScore(wuyuScore.getHealthyLiving()));
        wuyuScore.setMentalQualities(checkScore(wuyuScore.getMentalQualities()));
        wuyuScore.setPhysicalEducationCourses(checkScore(wuyuScore.getPhysicalEducationCourses()));
        wuyuScore.setArtsCourses(checkScore(wuyuScore.getArtsCourses()));
        wuyuScore.setArtsAchievements(checkScore(wuyuScore.getArtsAchievements()));
        wuyuScore.setArtsActivities(checkScore(wuyuScore.getArtsActivities()));
        wuyuScore.setLaborPractices(checkScore(wuyuScore.getLaborPractices()));
        wuyuScore.setLaborCourses(checkScore(wuyuScore.getLaborCourses()));
    }

    // 存储数据到数据库
    private void saveData() {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 所有数据解析完成后的操作
    }

    // 如果分数大于100就设定为100，若小于0就设定为0，在范围内就返回本身
    private Integer checkScore(Integer score) {
        if (score < 0) {
            return 0;
        }
        if (score > 100) {
            return 100;
        }
        return score;
    }

}
