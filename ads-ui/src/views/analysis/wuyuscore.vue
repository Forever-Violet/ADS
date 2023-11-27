<template>
  <div class="mod-analysis__wuyuscore">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuscore:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuscore:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
      <el-form-item v-if="hasSchoolListPermission">
        <el-select v-model="state.dataForm.schoolId" placeholder="选择学校" clearable> <!--单选 去掉multiple-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <ren-select v-model="state.dataForm.comprehensiveLevel" dict-type="comprehensiveLevel" placeholder="综合等级"></ren-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="state.dataForm.studentNameOrNo" placeholder="学号或姓名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:user:export')" type="info" @click="state.exportHandle()">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border :cell-border="true" @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>

              <el-table-column prop="studentNo" label="学生学号" header-align="center" align="center" width="100"></el-table-column>
              <el-table-column prop="studentName" label="学生姓名" header-align="center" align="center" width="100"></el-table-column>
              <el-table-column label="德育" align="center">
                <el-table-column prop="characterEthics" label="品德评定" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="rewardsPunishments" label="奖惩记录" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="moralEducationCourses" label="德育课程" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="practicalActivities" label="实践活动" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="onlineCulture" label="网络文化" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="interpersonalRelationships" label="人际关系" header-align="center" align="center" width="100"></el-table-column>
              </el-table-column>
              <el-table-column label="智育" align="center">
                <el-table-column prop="prepManagement" label="预习管理" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="planManagement" label="计划管理" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="classroomBehavior" label="课堂行为" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="classroomAttendance" label="课堂考勤" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="homeworkManagement" label="作业管理" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="reviewManagement" label="复习管理" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="personalAbilities" label="个人能力" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="academicPerformance" label="学业成绩" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="experimentalCompetitions" label="实验竞赛" header-align="center" align="center" width="100"></el-table-column>
              </el-table-column>
              <el-table-column label="体育" align="center">
                <el-table-column prop="examinationMetrics" label="体检指标" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="physicalFitnessScores" label="体能成绩" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="sportingSpecialties" label="体育特长" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="healthyLiving" label="健康生活" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="mentalQualities" label="心理素质" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="physicalEducationCourses" label="体育课程" header-align="center" align="center" width="100"></el-table-column>
              </el-table-column>
              <el-table-column label="美育" align="center">
                <el-table-column prop="artsCourses" label="美育课程" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="artsAchievements" label="美育成果" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="artsActivities" label="美育活动" header-align="center" align="center" width="100"></el-table-column>
              </el-table-column>
              <el-table-column label="劳育" align="center">
                <el-table-column prop="laborPractices" label="劳动实践" header-align="center" align="center" width="100"></el-table-column>
                <el-table-column prop="laborCourses" label="劳动课程" header-align="center" align="center" width="100"></el-table-column>
              </el-table-column>
              <el-table-column prop="comprehensiveScore" label="五育综合" header-align="center" align="center" width="100"></el-table-column>
              <el-table-column prop="comprehensiveLevel" label="综合等级" header-align="center" align="center" width="100">
                <template v-slot="scope">
                  {{ state.getDictLabel("comprehensiveLevel", scope.row.comprehensiveLevel) }}
                </template>
              </el-table-column>
              <el-table-column prop="academicLevel" label="学业等级" header-align="center" align="center" width="100">
                <template v-slot="scope">
                  {{ state.getDictLabel("academicLevel", scope.row.academicLevel) }}
                </template>
              </el-table-column>
            <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('analysis:wuyuscore:individual')" type="primary" link @click="individualAnalysisHandle(scope.row.id)">个人诊断报告</el-button>
          <el-button v-if="state.hasPermission('analysis:wuyuscore:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('analysis:wuyuscore:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"> </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList">确定</add-or-update>

    <individualAnalysis ref="individualAnalysisRef"></individualAnalysis>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs, onMounted } from "vue";
import AddOrUpdate from "./wuyuscore-add-or-update.vue";
import baseService from "@/service/baseService";
import individualAnalysis from "./wuyuscore-individual-analysis.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/analysis/wuyuscore/page",
  getDataListIsPage: true,
  exportURL: "/analysis/wuyuscore/export",
  deleteURL: "/analysis/wuyuscore",
  dataForm: {
    schoolId: "",
    studentNameOrNo: "",
    comprehensiveLevel: ""
  }
});

const state = reactive({ ...useView(view), ...toRefs(view) });
const schoolList = ref<any[]>([]);
const hasSchoolListPermission = state.hasPermission("sys:school:list"); // 检查用户是否拥有展示学校列表权限

onMounted(() => {
  //有权限，才执行
  if (hasSchoolListPermission) {
    getSchoolList();
  }
});

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};

const individualAnalysisRef = ref();
const individualAnalysisHandle = (id?: number) => {
  individualAnalysisRef.value.init(id);
};
</script>
