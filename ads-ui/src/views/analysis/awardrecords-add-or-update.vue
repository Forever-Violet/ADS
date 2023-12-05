<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="160px">
      <el-form-item v-if="hasSchoolListPermission && !dataForm.id" prop="schoolName" label="所属学校">
        <el-select v-model="params.schoolId" placeholder="选择学校" clearable @change="schoolChangedHandle">
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学号（姓名）" prop="studentNo" v-if="dataForm.id == ''">
        <!--去掉multiple为单选 , clearable重置, @blur下拉框失去焦点后执行该函数-->
        <el-select v-model="dataForm.studentNo" placeholder="选择学生" clearable @blur="resetStudentList" @change="updateStudentName">
          <el-input v-model="searchInput" placeholder="请输入关键字" @input="handleSearch" clearable></el-input>
          <!--<el-button type="text" @click="resetStudentList">重置</el-button>-->
          <el-option v-if="studentList.length == 0" :disabled="true" :style="{ textAlign: 'center' }">未查询到数据</el-option>
          <el-option v-for="student in studentList" :key="student.username" :label="student.username + '（' + student.realName + ')'" :value="student.username"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属五育" prop="topic">
        <ren-select v-model="params.topic" dict-type="topic" placeholder="所属五育" @change="topicChange"></ren-select>
      </el-form-item>
      <el-form-item label="所属小类" prop="subtopic">
        <el-select v-model="params.subtopic" placeholder="所属小类" clearable @change="subtopicChange">
          <el-option v-if="params.topic == 0" label="奖惩记录" :key="0" :value="0"></el-option>
          <el-option v-if="params.topic == 1" label="实验与竞赛" :key="1" :value="1"></el-option>
          <el-option v-if="params.topic == 1" label="学业成绩" :key="2" :value="2"></el-option>
          <el-option v-if="params.topic == 2" label="体育特长" :key="3" :value="3"></el-option>
          <el-option v-if="params.topic == 3" label="美育成果" :key="4" :value="4"></el-option>
          <el-option v-if="params.topic == 4" label="劳动实践" :key="5" :value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="类型" prop="level">
        <ren-select v-model="params.level" dict-type="award_level" placeholder="类型" @change="levelChange"></ren-select>
      </el-form-item>
      <el-form-item label="奖项名称" prop="awardName">
        <el-select v-model="dataForm.awardId" placeholder="选择奖项" clearable @click="getAwardList" @change="resetAwardGrade">
          <!--单选 去掉multiple , clearable重置-->
          <el-option v-for="award in awardList" :key="award.id" :label="award.awardName" :value="award.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="获奖等级" prop="grade">
        <el-select v-model="dataForm.grade" placeholder="选择等级" clearable @click="getAwardGradeList">
          <!--单选 去掉multiple , clearable重置-->
          <el-option v-for="grade in awardGradeList" :key="grade" :label="grade.name" :value="grade.name"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="获奖日期" prop="awardDate">
        <el-date-picker v-model="dataForm.awardDate" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD" placeholder="获奖日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input v-model="dataForm.remarks" placeholder="备注" style="width: 50%"></el-input>
      </el-form-item>
    </el-form>
    <template v-slot:footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmitHandle()">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import {reactive, ref, toRefs} from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
import useView from "@/hooks/useView";

const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();
const searchInput = ref("");
const awardList = ref<any[]>([]);
const studentList = ref<any[]>([]);
const originalStudentList = ref<any[]>([]); //原始的学生列表，没有查询到匹配的学号或姓名，可以通过这个列表重置学生列表
const dataForm = reactive({
  id: "",
  studentNo: "",
  studentName: "",
  awardId: "",
  grade: "",
  awardDate: "",
  remarks: "",
  topic: "",
  subtopic: "",
  level: "",
  awardName: ""
});
const schoolList = ref<any[]>([]);
const state = reactive({ ...useView(dataForm), ...toRefs(dataForm) });
const hasSchoolListPermission = state.hasPermission("sys:school:list");
const awardGradeList = ref([{ name: "" }, { name: "" }, { name: "" }]);
const params = reactive({
  topic: "",
  subtopic: "",
  level: "",
  // 学校id单独放出来，因为奖项记录没有schoolId字段，这里只是给超级管理员查询学生列表用的
  schoolId: ""
});
const rules = ref({
  studentNo: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  awardId: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  grade: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  awardDate: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  remarks: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});


const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.studentNo = "";
  dataForm.awardId = "";
  params.schoolId = "";
  params.topic = "";
  params.subtopic = "";
  params.level = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }
  if (id) {
    // 如果是更新
    getInfo(id);
  } else {
    // 如果是新增 ，判断有无学校列表权限
    if (hasSchoolListPermission) {
      // 获取学校列表 和学生列表
      getSchoolList();
    } else { //无学校列表权限直接获取学生列表
      getStudentList();
    }
  }
};

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
    // 检查返回的列表是否非空
    if (schoolList.value && schoolList.value.length > 0) {
      // 设置默认选中第一个学校
      params.schoolId = schoolList.value[0].schoolId;
    }
    // 根据默认选中的学校获取数据
    getStudentList();
  });
};

// 获取学生列表
const getStudentList = () => {
  let schoolId = "";
  if (params.schoolId != "") {
    schoolId = params.schoolId;
  }
  return baseService.get("/sys/user/student", { schoolId }).then((res) => {
    studentList.value = res.data;
    originalStudentList.value = res.data;
  });
};
// 获取奖项列表
const getAwardList = () => {
  if (params.topic !== "" && params.subtopic !== "" && params.level !== "") {
    const topic = params.topic;
    const subtopic = params.subtopic;
    const level = params.level;
    return baseService.get("/analysis/awardsettings/list", { topic, subtopic, level }).then((res) => {
      if (res.data.length == 0) {
        dataForm.awardId = ""; //如果没找到对应的奖项，那么当前选择的奖项置空
      }
      awardList.value = res.data;
    });
  }
};

// 获取当前奖项对应的奖项等级，并分割成列表
const getAwardGradeList = () => {
  // 获取当前选择的奖项ID
  const selectedAwardId = dataForm.awardId;
  // 在awardList中查找当前选择的奖项
  const selectedAward = awardList.value.find((award) => award.id === selectedAwardId);
  // 检查是否找到了匹配的奖项
  if (selectedAward) {
    // 提取奖项的 grade等级字段，并通过，分隔成字符串列表
    awardGradeList.value = selectedAward.grade.split(",").map((name: string) => ({ name }));
    if (selectedAward.grade == "无") {
      //如果该奖项的等级设定是 "无"，那么直接给他设定好
      dataForm.grade = "无";
    }
  } else {
    // 如果没有找到匹配的奖项，清空awardGradeList
    awardGradeList.value = [];
    // 表单中的奖项等级置空
    dataForm.grade = "";
  }
};

const topicChange = () => {
  // 当所属五育选项改变后，置空当前的所属小类, 类型，奖项名称选项，获奖等级
  params.subtopic = "";
  params.level = "";
  dataForm.awardId = "";
  dataForm.grade = "";
};

const subtopicChange = () => {
  // 当所属小类选项改变后，置空当前 类型，奖项名称选项，获奖等级
  params.level = "";
  dataForm.awardId = "";
  dataForm.grade = "";
};

const levelChange = () => {
  // 当类型选项改变后，置空当前的 奖项名称选项，获奖等级
  dataForm.awardId = "";
  dataForm.grade = "";
};

const resetAwardGrade = () => {
  // 当奖项名称改变后，置空当前的获奖等级
  dataForm.grade = "";
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

// 学校改变后，重新获取学生列表
const schoolChangedHandle = () => {
  // 学生选框的搜索框清空
  searchInput.value = "";
  // 选择的学生置空
  dataForm.studentNo = "";
  getStudentList();
};


// 重置学生列表
const resetStudentList = () => {
  searchInput.value = "";
  studentList.value = originalStudentList.value;
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/analysis/awardrecords/" + id).then((res) => {
    Object.assign(dataForm, res.data);
    // 回显三个选项
    params.topic = dataForm.topic;
    params.subtopic = dataForm.subtopic;
    params.level = dataForm.level;
    getAwardList(); //奖项列表也重新获取一下，不然首先会显示id??
  });
};

// 当选择了学生后，填充学生姓名到表单
const updateStudentName = () => {
  // 根据选中的学号查找对应的学生姓名
  const selectStudent = studentList.value.find((student) => student.username === dataForm.studentNo);
  if (selectStudent) {
    dataForm.studentName = selectStudent.realName;
  }
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/analysis/awardrecords", dataForm).then((res) => {
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
