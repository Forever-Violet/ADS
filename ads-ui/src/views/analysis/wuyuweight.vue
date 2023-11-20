<template>
  <div class="mod-analysis__wuyuweight">
    <el-form :inline="true" :model="state.dataForm">
      <el-form-item v-if="hasSchoolListPermission">
        <el-select v-model="state.dataForm.schoolId" placeholder="选择学校" clearable @change="getSchoolWeight"> <!--单选 去掉multiple-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item v-if="state.hasPermission('analysis:wuyuweight:update')">
        <el-button size="mini" type="primary" @click="addOrUpdateHandle('0', state.dataForm.schoolId)">修改一级权重</el-button>
      </el-form-item>
    </el-form>
    <table class="weight-table" style="width: 100%; text-align: center;">
      <thead>
      <tr>
        <th>五育（权重）</th>
        <th>主题名称</th>
        <th>权重</th>
        <th v-if="state.hasPermission('analysis:wuyuweight:update')">操作</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <td rowspan="6">
          德育（{{weight.moral}}）
<!--          <span v-if="state.hasPermission('analysis:wuyuweight:update')" @click="addOrUpdateHandle('0', state.dataForm.schoolId)"><u style="color:blue">德育（{{weight.moral}}）</u></span>
          <span v-else>德育（{{weight.moral}}）</span>-->
        </td>
        <td>品德评定</td>
        <td>{{weight.characterEthics}}</td>
        <td rowspan="6" v-if="state.hasPermission('analysis:wuyuweight:update')">
          <el-button v-if="state.hasPermission('analysis:wuyuweight:update')" mc-type="column-el-button" size="mini" type="primary" @click="addOrUpdateHandle('1', state.dataForm.schoolId)">修改</el-button>
        </td>
      </tr>
      <tr>
        <td>奖惩记录</td>
        <td>{{weight.rewardsPunishments}}</td>
      </tr>
      <tr>
        <td>德育课程</td>
        <td>{{weight.moralEducationCourses}}</td>
      </tr>
      <tr>
        <td>实践活动</td>
        <td>{{weight.practicalActivities}}</td>
      </tr>
      <tr>
        <td>网络文化</td>
        <td>{{weight.onlineCulture}}</td>
      </tr>
      <tr>
        <td>人际关系</td>
        <td>{{weight.interpersonalRelationships}}</td>
      </tr>
      <tr>
        <td rowspan="9">智育（{{weight.intellectual}}）</td>
        <td>预习管理</td>
        <td>{{weight.prepManagement}}</td>
        <td rowspan="9" v-if="state.hasPermission('analysis:wuyuweight:update')">
          <el-button v-if="state.hasPermission('analysis:wuyuweight:update')" mc-type="column-el-button" size="mini" type="primary" @click="addOrUpdateHandle('2', state.dataForm.schoolId)">修改</el-button>
        </td>
      </tr>
      <tr>
        <td>计划管理</td>
        <td>{{weight.planManagement}}</td>
      </tr>
      <tr>
        <td>课堂行为</td>
        <td>{{weight.classroomBehavior}}</td>
      </tr>
      <tr>
        <td>课堂考勤</td>
        <td>{{weight.classroomAttendance}}</td>
      </tr>
      <tr>
        <td>作业管理</td>
        <td>{{weight.homeworkManagement}}</td>
      </tr>
      <tr>
        <td>复习管理</td>
        <td>{{weight.reviewManagement}}</td>
      </tr>
      <tr>
        <td>个人能力</td>
        <td>{{weight.personalAbilities}}</td>
      </tr>
      <tr>
        <td>学业成绩</td>
        <td>{{weight.academicPerformance}}</td>
      </tr>
      <tr>
        <td>实验竞赛</td>
        <td>{{weight.experimentalCompetitions}}</td>
      </tr>
      <tr>
        <td rowspan="6">体育（{{weight.physical}}）</td>
        <td>体检指标</td>
        <td>{{weight.examinationMetrics}}</td>
        <td rowspan="6" v-if="state.hasPermission('analysis:wuyuweight:update')">
          <el-button v-if="state.hasPermission('analysis:wuyuweight:update')" mc-type="column-el-button" size="mini" type="primary" @click="addOrUpdateHandle('3', state.dataForm.schoolId)">修改</el-button>
        </td>
      </tr>
      <tr>
        <td>体能成绩</td>
        <td>{{weight.physicalFitnessScores}}</td>
      </tr>
      <tr>
        <td>体育特长</td>
        <td>{{weight.sportingSpecialties}}</td>
      </tr>
      <tr>
        <td>健康生活</td>
        <td>{{weight.healthyLiving}}</td>
      </tr>
      <tr>
        <td>心理素质</td>
        <td>{{weight.mentalQualities}}</td>
      </tr>
      <tr>
        <td>体育课程</td>
        <td>{{weight.physicalEducationCourses}}</td>
      </tr>
      <tr>
        <td rowspan="3">美育（{{weight.artistic}}）</td>
        <td>美育课程</td>
        <td>{{weight.artsCourses}}</td>
        <td rowspan="3" v-if="state.hasPermission('analysis:wuyuweight:update')">
          <el-button v-if="state.hasPermission('analysis:wuyuweight:update')" mc-type="column-el-button" size="mini" type="primary" @click="addOrUpdateHandle('4', state.dataForm.schoolId)">修改</el-button>
        </td>
      </tr>
      <tr>
        <td>美育成果</td>
        <td>{{weight.artsAchievements}}</td>
      </tr>
      <tr>
        <td>美育活动</td>
        <td>{{weight.artsActivities}}</td>
      </tr>
      <tr>
        <td rowspan="2">劳育（{{weight.labor}}）</td>
        <td>劳动实践</td>
        <td>{{weight.laborPractices}}</td>
        <td rowspan="2" v-if="state.hasPermission('analysis:wuyuweight:update')">
          <el-button v-if="state.hasPermission('analysis:wuyuweight:update')" mc-type="column-el-button" size="mini" type="primary" @click="addOrUpdateHandle('5', state.dataForm.schoolId)">修改</el-button>
        </td>
      </tr>
      <tr>
        <td>劳动课程</td>
        <td>{{weight.laborCourses}}</td>
      </tr>
      </tbody>
    </table>
    <!--     弹窗, 新增 / 修改-->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="getSchoolWeight">确定</add-or-update>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { onMounted, reactive, ref, toRefs } from "vue";
import AddOrUpdate from "./wuyuweight-add-or-update.vue";
import baseService from "@/service/baseService";

const view = reactive({
  deleteIsBatch: true,
  getDataListIsPage: true,
  exportURL: "/analysis/wuyuweight/export",
  dataForm: {
    schoolId: ""
  }
});

const state = reactive({ ...useView(view), ...toRefs(view) });
const schoolList = ref<any[]>([]);
const hasSchoolListPermission = state.hasPermission("sys:school:list"); // 检查用户是否拥有展示学校列表权限

const weight = reactive({
  moral: "",
  intellectual: "",
  physical: "",
  artistic: "",
  labor: "",
  characterEthics: "",
  rewardsPunishments: "",
  moralEducationCourses: "",
  practicalActivities: "",
  onlineCulture: "",
  interpersonalRelationships: "",
  prepManagement: "",
  planManagement: "",
  classroomBehavior: "",
  classroomAttendance: "",
  homeworkManagement: "",
  reviewManagement: "",
  personalAbilities: "",
  academicPerformance: "",
  experimentalCompetitions: "",
  examinationMetrics: "",
  physicalFitnessScores: "",
  sportingSpecialties: "",
  healthyLiving: "",
  mentalQualities: "",
  physicalEducationCourses: "",
  artsCourses: "",
  artsAchievements: "",
  artsActivities: "",
  laborPractices: "",
  laborCourses: ""
});

onMounted(() => {
  //有权限，才执行
  if (hasSchoolListPermission) {
    getSchoolList();
  }
  // 获取学校权重
  getSchoolWeight();
});

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};

// 获取学校的五育权重设置
const getSchoolWeight = () => {
  let schoolId = "";
  if (state.dataForm.schoolId != "") {
    schoolId = state.dataForm.schoolId;
  }
  return baseService.get("/analysis/wuyuweight/info", { schoolId }).then((res) => {
    Object.assign(weight, res.data);
  });
};

const addOrUpdateRef = ref();
const addOrUpdateHandle = (index?: string, schoolId?: string) => {
  addOrUpdateRef.value.init(index, schoolId);
};
</script>
<style>
/* 增加样式以确保表格始终可见 */
.weight-table {
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed; /* 固定列宽以便更好地适应容器 */
}

.weight-table th,
.weight-table td {
  border: 1px solid #ededed; /* 设置单元格边框样式 */
  padding: 8px; /* 添加内边距以增加单元格间距 */
  text-align: center;
}
.weight-table th {
  background-color: #f5f7fa; /* 设置表头背景颜色 */
}
</style>
