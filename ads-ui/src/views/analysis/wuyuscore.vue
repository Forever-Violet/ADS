<template>
  <div class="mod-analysis__wuyuscore">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item v-if="hasSchoolListPermission">
        <el-select v-model="state.dataForm.schoolId" placeholder="选择学校" clearable @change="resetGradeAndSemesterList">
          <!--单选 去掉multiple-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="state.dataForm.semesterId" placeholder="选择学期" clearable>
          <el-option v-for="semester in semesterList" :key="semester.id" :label="semester.semesterName" :value="semester.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-if="hasGradeListPermission" v-model="state.dataForm.gradeId" placeholder="选择年级" clearable @change="resetClassList">
          <el-option v-for="grade in gradeList" :key="grade.id" :label="grade.gradeName" :value="grade.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-if="hasGradeListPermission" v-model="state.dataForm.classId" placeholder="选择班级" clearable>
          <el-option v-for="clazz in classList" :key="clazz.id" :label="clazz.className" :value="clazz.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <ren-select v-if="hasGradeListPermission" v-model="state.dataForm.comprehensiveLevel" dict-type="comprehensiveLevel" placeholder="综合等级"></ren-select>
      </el-form-item>
      <el-form-item>
        <el-input v-if="hasGradeListPermission" v-model="state.dataForm.studentNameOrNo" placeholder="学号或姓名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuscore:save')" type="primary" @click="addOrUpdateHandle()"> 新增 </el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuscore:delete')" type="danger" @click="state.deleteHandle()"> 删除 </el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuscore:export')" type="info" @click="state.exportHandle()">导出 </el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuscore:export')" type="info" @click="exportTemplateHandle()">导出Excel模板</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuscore:export')" type="primary" @click="importHandle()">Excel导入</el-button>
      </el-form-item>
      <el-form-item>
        <!--只有选择了班级后才能点击-->
        <el-button v-if="state.hasPermission('analysis:wuyuscore:class')" type="success" @click="classAnalysisHandle(state.dataForm.classId, state.dataForm.semesterId)" :disabled="!state.dataForm.classId">班级诊断报告</el-button>
      </el-form-item>
    </el-form>
    <input type="file" id="fileInput" style="display: none" @change="fileChanged" />
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border :cell-border="true" @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>

      <el-table-column prop="studentNo" label="学生学号" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      <el-table-column prop="studentName" label="学生姓名" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      <el-table-column label="德育" align="center">
        <el-table-column prop="characterEthics" label="品德评定" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="rewardsPunishments" label="奖惩记录" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="moralEducationCourses" label="德育课程" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="practicalActivities" label="实践活动" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="onlineCulture" label="网络文化" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="interpersonalRelationships" label="人际关系" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      </el-table-column>
      <el-table-column label="智育" align="center">
        <el-table-column prop="prepManagement" label="预习管理" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="planManagement" label="计划管理" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="classroomBehavior" label="课堂行为" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="classroomAttendance" label="课堂考勤" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="homeworkManagement" label="作业管理" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="reviewManagement" label="复习管理" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="personalAbilities" label="个人能力" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="academicPerformance" label="学业成绩" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="experimentalCompetitions" label="实验竞赛" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      </el-table-column>
      <el-table-column label="体育" align="center">
        <el-table-column prop="examinationMetrics" label="体检指标" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="physicalFitnessScores" label="体能成绩" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="sportingSpecialties" label="体育特长" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="healthyLiving" label="健康生活" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="mentalQualities" label="心理素质" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="physicalEducationCourses" label="体育课程" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      </el-table-column>
      <el-table-column label="美育" align="center">
        <el-table-column prop="artsCourses" label="美育课程" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="artsAchievements" label="美育成果" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="artsActivities" label="美育活动" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      </el-table-column>
      <el-table-column label="劳育" align="center">
        <el-table-column prop="laborPractices" label="劳动实践" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="laborCourses" label="劳动课程" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      </el-table-column>
      <el-table-column prop="comprehensiveScore" label="五育综合" header-align="center" align="center" width="100" sortable="custom"></el-table-column>
      <el-table-column prop="comprehensiveLevel" label="综合等级" header-align="center" align="center" width="100" sortable="custom">
        <template v-slot="scope">
          {{ state.getDictLabel("comprehensiveLevel", scope.row.comprehensiveLevel) }}
        </template>
      </el-table-column>
      <el-table-column prop="academicLevel" label="学业等级" header-align="center" align="center" width="100" sortable="custom">
        <template v-slot="scope">
          {{ state.getDictLabel("academicLevel", scope.row.academicLevel) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('analysis:wuyuscore:individual')" type="primary" link @click="individualAnalysisHandle(scope.row.id)">个人诊断报告 </el-button>
          <el-button v-if="state.hasPermission('analysis:wuyuscore:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改 </el-button>
          <el-button v-if="state.hasPermission('analysis:wuyuscore:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"></el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList">确定</add-or-update>

    <individualAnalysis ref="individualAnalysisRef"></individualAnalysis>
    <classAnalysis ref="classAnalysisRef"></classAnalysis>

  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs, onMounted, watch } from "vue";
import AddOrUpdate from "./wuyuscore-add-or-update.vue";
import baseService from "@/service/baseService";
import individualAnalysis from "./wuyuscore-individual-analysis.vue";
import classAnalysis from "./wuyuscore-class-analysis.vue";
import { getToken } from "@/utils/cache";
import app from "@/constants/app";
import axios from "axios";
import { ElMessage } from "element-plus";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/analysis/wuyuscore/page",
  // 这个属性是设置此页面是否在创建时，调用查询数据列表接口。这个页面里创建时不需要调用，而是在自动选择的学期后调用
  createdIsNeed: false,
  getDataListIsPage: true,
  exportURL: "/analysis/wuyuscore/export",
  deleteURL: "/analysis/wuyuscore",
  dataForm: {
    schoolId: "",
    gradeId: "",
    classId: "",
    semesterId: "",
    studentNameOrNo: "",
    comprehensiveLevel: ""
  }
});
const url = ref("");
const state = reactive({ ...useView(view), ...toRefs(view) });
const schoolList = ref<any[]>([]);
const gradeList = ref<any[]>([]);
const classList = ref<any[]>([]);
const semesterList = ref<any[]>([]);
const hasSchoolListPermission = state.hasPermission("sys:school:list"); // 检查用户是否拥有展示学校列表权限
const hasGradeListPermission = state.hasPermission("sys:grade:list"); // 检查用户是否拥有展示年级列表权限，即判断是否非学生

onMounted(() => {
  //有权限，才执行
  if (hasSchoolListPermission) {
    // 如果是超级管理员，那么先不获取年级列表和学期列表，在选择了学校后会自动获取
    getSchoolList();
  } else if (hasGradeListPermission){
    //如果不是超级管理员，即如果没有学校列表权限，直接获取年级列表和学期列表
    getGradeList();
    getSemesterList();
  } else {
    // 学生
    getSemesterList();

  }
});

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
    // 默认选择第一个学校
    if (schoolList.value && schoolList.value.length > 0) {
      state.dataForm.schoolId = schoolList.value[0].schoolId;
    }
    // 重新获取数据
    //state.getDataList();
    // 选择后直接获取列表
    getGradeList();
    getSemesterList();
  });
};
// 获取学期列表
const getSemesterList = () => {
  let schoolId = "";
  if (state.dataForm.schoolId != "") {
    schoolId = state.dataForm.schoolId;
  }
  return baseService.get("/sys/semester/list", { schoolId }).then((res) => {
    semesterList.value = res.data;
    // 检查返回的列表是否非空
    if (semesterList.value && semesterList.value.length > 0) {
      // 设置默认选中第一个学期
      state.dataForm.semesterId = semesterList.value[0].id;
    }
    state.getDataList();
  });
};
// 获取年级列表
const getGradeList = () => {
  let schoolId = "";
  if (state.dataForm.schoolId != "") {
    schoolId = state.dataForm.schoolId;
  }
  return baseService.get("/sys/grade/list", { schoolId }).then((res) => {
    gradeList.value = res.data;
  });
};
// 获取班级列表
const getClassList = () => {
  let schoolId = "";
  let gradeId = "";
  if (state.dataForm.schoolId != "") {
    schoolId = state.dataForm.schoolId;
  }
  if (state.dataForm.gradeId != "") {
    gradeId = state.dataForm.gradeId;
  }
  return baseService.get("/sys/class/list", { schoolId, gradeId }).then((res) => {
    classList.value = res.data;
  });
};
// 当选择的学校改变后，重置gradeList和semesterList, 并重置年级选项和班级选项以及学期选项
const resetGradeAndSemesterList = () => {
  state.dataForm.gradeId = "";
  state.dataForm.classId = "";
  state.dataForm.semesterId = "";
  getGradeList();
  getSemesterList();
};
// 当选择的年级改变后，重置classList, 并重置班级选项
const resetClassList = () => {
  state.dataForm.classId = "";
  getClassList();
};

// 导出模板
const exportTemplateHandle = () => {
  url.value = `${app.api}/analysis/wuyuscore/exportTemplate?token=${getToken()}`;
  window.location.href = url.value;
};

// 上传excel
const importHandle = () => {
  const fileInput = document.getElementById("fileInput");
  if (fileInput) {
    fileInput.click(); //触发上传文件
  } else {
    console.error("FileInput not found");
  }
};

const fileChanged = (event: Event) => {
  const target = event.target as HTMLInputElement;
  const file = target.files ? target.files[0] : null;
  if (!file || (file.type !== "application/vnd.ms-excel" && file.type !== "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
    // 前端校验 ，后端也校验，主打健壮性
    ElMessage.error("请上传 Excel 文件 (.xls 或 .xlsx)");
    return;
  }
  const uploadUrl = `${app.api}/analysis/wuyuscore/upload?token=${getToken()}`;
  const formData = new FormData();
  formData.append("file", file);

  axios
    .post(uploadUrl, formData, {
      headers: { "Content-Type": "multipart/form-data" }
    })
    .then((response) => {
      // 检查响应码
      if (response.data.code === 500) {
        // 发生异常，显示错误信息
        ElMessage.error(response.data.msg);
      } else {
        // 提取响应结果, 嵌套在data对象里了
        const successNum = response.data.data.successNum;
        const failedNum = response.data.data.failedNum;
        console.log(successNum);
        console.log(failedNum);

        // 显示成功和失败的记录数
        ElMessage({
          message: `成功处理 ${successNum} 条记录，处理失败 ${failedNum} 条记录。`,
          type: "success"
        });
        // 刷新列表
        state.getDataList();
      }
    })
    .catch((error) => {
      console.error("文件上传失败", error);
      ElMessage.error("文件上传失败");
    });
};

// 监听 state.dataForm 中的某些属性
/*watch(
  () => [state.dataForm.schoolId, state.dataForm.gradeId, state.dataForm.classId, state.dataForm.semesterId],
  () => {
    // 当这些属性之一发生变化时，重新获取数据，    这个功能先放着，暂时还是让用户自己点击查询去更新数据
    state.getDataList();
  },
  { deep: true }
);*/

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};

const individualAnalysisRef = ref();
const individualAnalysisHandle = (id?: number) => {
  individualAnalysisRef.value.init(id);
};

const classAnalysisRef = ref();
const classAnalysisHandle = (classId: string, semesterId: string) => {
  console.log(classId + " " + semesterId);
  classAnalysisRef.value.init(classId, semesterId);
};
</script>
