<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false"
             :close-on-press-escape="false" width="600px">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()"
             label-width="120px">
      <el-form-item label="学号（姓名）" prop="studentNo" v-if="dataForm.id == ''">
        <!--去掉multiple为单选 , clearable重置, @blur下拉框失去焦点后执行该函数-->
        <el-select v-model="dataForm.studentNo" placeholder="选择学生" clearable @blur="resetStudentList" @change="updateStudentName">
          <el-input v-model="searchInput" placeholder="请输入关键字" @input="handleSearch" clearable></el-input>
          <el-option v-if="studentList.length == 0" :disabled="true" :style="{ textAlign: 'center' }">未查询到数据</el-option>
          <el-option v-for="student in studentList" :key="student.username" :label="student.username + '（' + student.realName + ')'" :value="student.username"></el-option>
        </el-select>
      </el-form-item>
      <el-row>
        <el-form-item label="品德评定" prop="characterEthics">
          <el-input-number v-model="dataForm.characterEthics" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="奖惩记录" prop="rewardsPunishments">
          <el-input-number v-model="dataForm.rewardsPunishments" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="德育课程" prop="moralEducationCourses">
          <el-input-number v-model="dataForm.moralEducationCourses" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="实践活动" prop="practicalActivities">
          <el-input-number v-model="dataForm.practicalActivities" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="网络文化" prop="onlineCulture">
          <el-input-number v-model="dataForm.onlineCulture" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="人际关系" prop="interpersonalRelationships">
          <el-input-number v-model="dataForm.interpersonalRelationships" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="预习管理" prop="prepManagement">
          <el-input-number v-model="dataForm.prepManagement" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="计划管理" prop="planManagement">
          <el-input-number v-model="dataForm.planManagement" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="课堂行为" prop="classroomBehavior">
          <el-input-number v-model="dataForm.classroomBehavior" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="课堂考勤" prop="classroomAttendance">
          <el-input-number v-model="dataForm.classroomAttendance" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="作业管理" prop="homeworkManagement">
          <el-input-number v-model="dataForm.homeworkManagement" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="复习管理" prop="reviewManagement">
          <el-input-number v-model="dataForm.reviewManagement" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="个人能力" prop="personalAbilities">
          <el-input-number v-model="dataForm.personalAbilities" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="学业成绩" prop="academicPerformance">
          <el-input-number v-model="dataForm.academicPerformance" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="实验竞赛" prop="experimentalCompetitions">
          <el-input-number v-model="dataForm.experimentalCompetitions" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="体检指标" prop="examinationMetrics">
          <el-input-number v-model="dataForm.examinationMetrics" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="体能成绩" prop="physicalFitnessScores">
          <el-input-number v-model="dataForm.physicalFitnessScores" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="体育特长" prop="sportingSpecialties">
          <el-input-number v-model="dataForm.sportingSpecialties" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="健康生活" prop="healthyLiving">
          <el-input-number v-model="dataForm.healthyLiving" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="心理素质" prop="mentalQualities">
          <el-input-number v-model="dataForm.mentalQualities" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="体育课程" prop="physicalEducationCourses">
          <el-input-number v-model="dataForm.physicalEducationCourses" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="美育课程" prop="artsCourses">
          <el-input-number v-model="dataForm.artsCourses" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="美育成果" prop="artsAchievements">
          <el-input-number v-model="dataForm.artsAchievements" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="美育活动" prop="artsActivities">
          <el-input-number v-model="dataForm.artsActivities" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="劳动实践" prop="laborPractices">
          <el-input-number v-model="dataForm.laborPractices" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
        <el-form-item label="劳动课程" prop="laborCourses">
          <el-input-number v-model="dataForm.laborCourses" :min="0" :max="100" size="default" :step="1"></el-input-number>
        </el-form-item>
      </el-row>
    </el-form>
    <template v-slot:footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import {reactive, ref} from "vue";
import baseService from "@/service/baseService";
import {ElMessage} from "element-plus";

const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();
const searchInput = ref("");
const studentList = ref<any[]>([]);
const originalStudentList = ref<any[]>([]); //原始的学生列表，没有查询到匹配的学号或姓名，可以通过这个列表重置学生列表
const dataForm = reactive({
  id: '',
  schoolId: '',
  studentNo: '',
  studentName: '',
  characterEthics: '',
  rewardsPunishments: '',
  moralEducationCourses: '',
  practicalActivities: '',
  onlineCulture: '',
  interpersonalRelationships: '',
  prepManagement: '',
  planManagement: '',
  classroomBehavior: '',
  classroomAttendance: '',
  homeworkManagement: '',
  reviewManagement: '',
  personalAbilities: '',
  academicPerformance: '',
  experimentalCompetitions: '',
  examinationMetrics: '',
  physicalFitnessScores: '',
  sportingSpecialties: '',
  healthyLiving: '',
  mentalQualities: '',
  physicalEducationCourses: '',
  artsCourses: '',
  artsAchievements: '',
  artsActivities: '',
  laborPractices: '',
  laborCourses: '',
  comprehensiveScore: '',
  academicLevel: ''
});

const rules = ref({
  weightId: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  studentNo: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  studentName: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  characterEthics: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  rewardsPunishments: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  moralEducationCourses: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  practicalActivities: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  onlineCulture: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  interpersonalRelationships: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  prepManagement: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  planManagement: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  classroomBehavior: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  classroomAttendance: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  homeworkManagement: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  reviewManagement: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  personalAbilities: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  academicPerformance: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  experimentalCompetitions: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  examinationMetrics: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  physicalFitnessScores: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  sportingSpecialties: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  healthyLiving: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  mentalQualities: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  physicalEducationCourses: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  artsCourses: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  artsAchievements: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  artsActivities: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  laborPractices: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  laborCourses: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  comprehensiveScore: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  academicLevel: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ]
});

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.studentNo = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  if (id) {
    getInfo(id);
  }
  getStudentList();
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/analysis/wuyuscore/" + id).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 获取学生列表
const getStudentList = () => {
  return baseService.get("/sys/user/student").then((res) => {
    studentList.value = res.data;
    originalStudentList.value = res.data;
  });
};

const handleSearch = () => {
  // 在这里过滤学生列表
  const keyword = searchInput.value.toLowerCase();
  // 更新过滤后的学生列表
  studentList.value = studentList.value.filter((student) => {
    return student.username.toLowerCase().includes(keyword) || student.realName.toLowerCase().includes(keyword);
  });
  if (keyword == "") {
    resetStudentList();
  }
};

// 当选择了学生后，填充学生姓名到表单
const updateStudentName = () => {
  // 根据选中的学号查找对应的学生姓名
  const selectStudent = studentList.value.find((student) => student.username === dataForm.studentNo);
  if (selectStudent) {
    dataForm.studentName = selectStudent.realName;
  }
};

// 重置学生列表
const resetStudentList = () => {
  searchInput.value = "";
  studentList.value = originalStudentList.value;
};


// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/analysis/wuyuscore", dataForm).then((res) => {
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
