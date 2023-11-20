<template>
  <el-dialog v-model="visible" title="修改" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="80px">
      <el-form-item v-if="indexSelected.index == '0'">
        <el-form-item label="德育" prop="moral">
          <el-input-number v-model="dataForm.moral" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="智育" prop="intellectual">
          <el-input-number v-model="dataForm.intellectual" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="体育" prop="physical">
          <el-input-number v-model="dataForm.physical" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="美育" prop="artistic">
          <el-input-number v-model="dataForm.artistic" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="劳育" prop="labor">
          <el-input-number v-model="dataForm.labor" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form-item>
      <el-form-item v-if="indexSelected.index == '1'">
        <el-form-item label="品德评定" prop="characterEthics">
          <el-input-number v-model="dataForm.characterEthics" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="奖惩记录" prop="rewardsPunishments">
          <el-input-number v-model="dataForm.rewardsPunishments" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="德育课程" prop="moralEducationCourses">
          <el-input-number v-model="dataForm.moralEducationCourses" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="实践活动" prop="practicalActivities">
          <el-input-number v-model="dataForm.practicalActivities" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="网络文化" prop="onlineCulture">
          <el-input-number v-model="dataForm.onlineCulture" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="人际关系" prop="interpersonalRelationships">
          <el-input-number v-model="dataForm.interpersonalRelationships" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form-item>
      <el-form-item v-if="indexSelected.index == '2'">
        <el-form-item label="预习管理" prop="prepManagement">
          <el-input-number v-model="dataForm.prepManagement" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="计划管理" prop="planManagement">
          <el-input-number v-model="dataForm.planManagement" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="课堂行为" prop="classroomBehavior">
          <el-input-number v-model="dataForm.classroomBehavior" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="课堂考勤" prop="classroomAttendance">
          <el-input-number v-model="dataForm.classroomAttendance" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="作业管理" prop="homeworkManagement">
          <el-input-number v-model="dataForm.homeworkManagement" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="复习管理" prop="reviewManagement">
          <el-input-number v-model="dataForm.reviewManagement" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="个人能力" prop="personalAbilities">
          <el-input-number v-model="dataForm.personalAbilities" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="学业成绩" prop="academicPerformance">
          <el-input-number v-model="dataForm.academicPerformance" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="实验竞赛" prop="experimentalCompetitions">
          <el-input-number v-model="dataForm.experimentalCompetitions" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form-item>
      <el-form-item v-if="indexSelected.index == '3'">
        <el-form-item label="体检指标" prop="examinationMetrics">
          <el-input-number v-model="dataForm.examinationMetrics" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="体能成绩" prop="physicalFitnessScores">
          <el-input-number v-model="dataForm.physicalFitnessScores" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="体育特长" prop="sportingSpecialties">
          <el-input-number v-model="dataForm.sportingSpecialties" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="健康生活" prop="healthyLiving">
          <el-input-number v-model="dataForm.healthyLiving" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="心理素质" prop="mentalQualities">
          <el-input-number v-model="dataForm.mentalQualities" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="体育课程" prop="physicalEducationCourses">
          <el-input-number v-model="dataForm.physicalEducationCourses" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form-item>
      <el-form-item v-if="indexSelected.index === '4'">
        <el-form-item label="美育课程" prop="artsCourses">
          <el-input-number v-model="dataForm.artsCourses" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="美育成果" prop="artsAchievements">
          <el-input-number v-model="dataForm.artsAchievements" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="美育活动" prop="artsActivities">
          <el-input-number v-model="dataForm.artsActivities" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form-item>
      <el-form-item v-if="indexSelected.index === '5'">
        <el-form-item label="劳动实践" prop="laborPractices">
          <el-input-number v-model="dataForm.laborPractices" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
        <el-form-item label="劳动课程" prop="laborCourses">
          <el-input-number v-model="dataForm.laborCourses" :precision="2" :min="0" :max="1" size="small" :step="0.01"></el-input-number>
        </el-form-item>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";

const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();
const indexSelected = reactive({
  index: ""
});

const dataForm = reactive({
  id: "",
  schoolId: "",
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

const rules = ref({
  schoolId: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  moral: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  intellectual: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  physical: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  artistic: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  labor: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  characterEthics: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  rewardsPunishments: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  moralEducationCourses: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  practicalActivities: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  onlineCulture: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  interpersonalRelationships: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  prepManagement: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  planManagement: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  classroomBehavior: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  classroomAttendance: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  homeworkManagement: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  reviewManagement: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  personalAbilities: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  academicPerformance: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  experimentalCompetitions: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  examinationMetrics: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  physicalFitnessScores: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  sportingSpecialties: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  healthyLiving: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  mentalQualities: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  physicalEducationCourses: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  artsCourses: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  artsAchievements: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  artsActivities: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  laborPractices: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  laborCourses: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});

const init = (index?: string, schoolId?: string) => {
  visible.value = true;
  dataForm.id = "";
  indexSelected.index = ""; //置空

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }
  if (index) {
    indexSelected.index = index;
  }
  if (schoolId) {
    getSchoolWeight(schoolId);
  }
};

// 获取信息
/*const getInfo = (id: number) => {
  baseService.get("/analysis/wuyuweight/").then((res) => {
    Object.assign(dataForm, res.data);
  });
};*/

// 获取学校的五育权重设置
const getSchoolWeight = (schoolId: string) => {
  return baseService.get("/analysis/wuyuweight/info", { schoolId }).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/analysis/wuyuweight", dataForm).then((res) => {
      ElMessage.success({
        message: "成功",
        duration: 500,
        onClose: () => {
          visible.value = false;
          emit("refreshDataList");
        }
      });
    });
  });
};

defineExpose({
  init
});
</script>
