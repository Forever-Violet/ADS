<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
          <el-form-item label="学校ID" prop="schoolId">
        <el-input v-model="dataForm.schoolId" placeholder="学校ID"></el-input>
      </el-form-item>
          <el-form-item label="德育" prop="moral">
        <el-input v-model="dataForm.moral" placeholder="德育"></el-input>
      </el-form-item>
          <el-form-item label="智育" prop="intellectual">
        <el-input v-model="dataForm.intellectual" placeholder="智育"></el-input>
      </el-form-item>
          <el-form-item label="体育" prop="physical">
        <el-input v-model="dataForm.physical" placeholder="体育"></el-input>
      </el-form-item>
          <el-form-item label="美育" prop="artistic">
        <el-input v-model="dataForm.artistic" placeholder="美育"></el-input>
      </el-form-item>
          <el-form-item label="劳育" prop="labor">
        <el-input v-model="dataForm.labor" placeholder="劳育"></el-input>
      </el-form-item>
          <el-form-item label="品德评定" prop="characterEthics">
        <el-input v-model="dataForm.characterEthics" placeholder="品德评定"></el-input>
      </el-form-item>
          <el-form-item label="奖惩记录" prop="rewardsPunishments">
        <el-input v-model="dataForm.rewardsPunishments" placeholder="奖惩记录"></el-input>
      </el-form-item>
          <el-form-item label="德育课程" prop="moralEducationCourses">
        <el-input v-model="dataForm.moralEducationCourses" placeholder="德育课程"></el-input>
      </el-form-item>
          <el-form-item label="实践活动" prop="practicalActivities">
        <el-input v-model="dataForm.practicalActivities" placeholder="实践活动"></el-input>
      </el-form-item>
          <el-form-item label="网络文化" prop="onlineCulture">
        <el-input v-model="dataForm.onlineCulture" placeholder="网络文化"></el-input>
      </el-form-item>
          <el-form-item label="人际关系" prop="interpersonalRelationships">
        <el-input v-model="dataForm.interpersonalRelationships" placeholder="人际关系"></el-input>
      </el-form-item>
          <el-form-item label="预习管理" prop="prepManagement">
        <el-input v-model="dataForm.prepManagement" placeholder="预习管理"></el-input>
      </el-form-item>
          <el-form-item label="计划管理" prop="planManagement">
        <el-input v-model="dataForm.planManagement" placeholder="计划管理"></el-input>
      </el-form-item>
          <el-form-item label="课堂行为" prop="classroomBehavior">
        <el-input v-model="dataForm.classroomBehavior" placeholder="课堂行为"></el-input>
      </el-form-item>
          <el-form-item label="课堂考勤" prop="classroomAttendance">
        <el-input v-model="dataForm.classroomAttendance" placeholder="课堂考勤"></el-input>
      </el-form-item>
          <el-form-item label="作业管理" prop="homeworkManagement">
        <el-input v-model="dataForm.homeworkManagement" placeholder="作业管理"></el-input>
      </el-form-item>
          <el-form-item label="复习管理" prop="reviewManagement">
        <el-input v-model="dataForm.reviewManagement" placeholder="复习管理"></el-input>
      </el-form-item>
          <el-form-item label="个人能力" prop="personalAbilities">
        <el-input v-model="dataForm.personalAbilities" placeholder="个人能力"></el-input>
      </el-form-item>
          <el-form-item label="学业成绩" prop="academicPerformance">
        <el-input v-model="dataForm.academicPerformance" placeholder="学业成绩"></el-input>
      </el-form-item>
          <el-form-item label="实验竞赛" prop="experimentalCompetitions">
        <el-input v-model="dataForm.experimentalCompetitions" placeholder="实验竞赛"></el-input>
      </el-form-item>
          <el-form-item label="体检指标" prop="examinationMetrics">
        <el-input v-model="dataForm.examinationMetrics" placeholder="体检指标"></el-input>
      </el-form-item>
          <el-form-item label="体能成绩" prop="physicalFitnessScores">
        <el-input v-model="dataForm.physicalFitnessScores" placeholder="体能成绩"></el-input>
      </el-form-item>
          <el-form-item label="体育特长" prop="sportingSpecialties">
        <el-input v-model="dataForm.sportingSpecialties" placeholder="体育特长"></el-input>
      </el-form-item>
          <el-form-item label="健康生活" prop="healthyLiving">
        <el-input v-model="dataForm.healthyLiving" placeholder="健康生活"></el-input>
      </el-form-item>
          <el-form-item label="心理素质" prop="mentalQualities">
        <el-input v-model="dataForm.mentalQualities" placeholder="心理素质"></el-input>
      </el-form-item>
          <el-form-item label="体育课程" prop="physicalEducationCourses">
        <el-input v-model="dataForm.physicalEducationCourses" placeholder="体育课程"></el-input>
      </el-form-item>
          <el-form-item label="美育课程" prop="artsCourses">
        <el-input v-model="dataForm.artsCourses" placeholder="美育课程"></el-input>
      </el-form-item>
          <el-form-item label="美育成果" prop="artsAchievements">
        <el-input v-model="dataForm.artsAchievements" placeholder="美育成果"></el-input>
      </el-form-item>
          <el-form-item label="美育活动" prop="artsActivities">
        <el-input v-model="dataForm.artsActivities" placeholder="美育活动"></el-input>
      </el-form-item>
          <el-form-item label="劳动实践" prop="laborPractices">
        <el-input v-model="dataForm.laborPractices" placeholder="劳动实践"></el-input>
      </el-form-item>
          <el-form-item label="劳动课程" prop="laborCourses">
        <el-input v-model="dataForm.laborCourses" placeholder="劳动课程"></el-input>
      </el-form-item>
      </el-form>
    <template slot="footer">
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

const dataForm = reactive({
  id: '',  schoolId: '',  moral: '',  intellectual: '',  physical: '',  artistic: '',  labor: '',  characterEthics: '',  rewardsPunishments: '',  moralEducationCourses: '',  practicalActivities: '',  onlineCulture: '',  interpersonalRelationships: '',  prepManagement: '',  planManagement: '',  classroomBehavior: '',  classroomAttendance: '',  homeworkManagement: '',  reviewManagement: '',  personalAbilities: '',  academicPerformance: '',  experimentalCompetitions: '',  examinationMetrics: '',  physicalFitnessScores: '',  sportingSpecialties: '',  healthyLiving: '',  mentalQualities: '',  physicalEducationCourses: '',  artsCourses: '',  artsAchievements: '',  artsActivities: '',  laborPractices: '',  laborCourses: ''});

const rules = ref({
          schoolId: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          moral: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          intellectual: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          physical: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          artistic: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          labor: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          characterEthics: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          rewardsPunishments: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          moralEducationCourses: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          practicalActivities: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          onlineCulture: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          interpersonalRelationships: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          prepManagement: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          planManagement: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          classroomBehavior: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          classroomAttendance: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          homeworkManagement: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          reviewManagement: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          personalAbilities: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          academicPerformance: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          experimentalCompetitions: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          examinationMetrics: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          physicalFitnessScores: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          sportingSpecialties: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          healthyLiving: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          mentalQualities: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          physicalEducationCourses: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          artsCourses: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          artsAchievements: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          artsActivities: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          laborPractices: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          laborCourses: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ]
  });

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  if (id) {
    getInfo(id);
  }
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/analysis/wuyuweight/" + id).then((res) => {
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
        message: '成功',
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
