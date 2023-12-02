<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false"
             :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()"
             label-width="120px">
      <el-form-item v-if="hasSchoolListPermission" prop="schoolName" label="所属学校">
        <el-select v-model="dataForm.schoolId" placeholder="选择学校" clearable @change="resetGradeList">
          <!--单选 去掉multiple , clearable重置-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="gradeName" label="所属年级">
        <el-select v-model="dataForm.gradeId" placeholder="选择年级" clearable>
          <el-option v-for="grade in gradeList" :key="grade.id" :label="grade.gradeName" :value="grade.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="班级名称" prop="className">
        <el-input v-model="dataForm.className" placeholder="班级名称"></el-input>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
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
import {ElMessage} from "element-plus";
import useView from "@/hooks/useView";
import htmlToPdf from "@/utils/htmlToPDF";

const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();
const schoolList = ref<any[]>([]);
const gradeList = ref<any[]>([]);
const dataForm = reactive({
  id: "",
  schoolId: "",
  gradeId: "",
  className: "",
  remark: "",
  createDate: ""
});

const state = reactive({...useView(dataForm), ...toRefs(dataForm)});
const hasSchoolListPermission = state.hasPermission("sys:school:list"); // 检查用户是否拥有展示学校列表权限


const rules = ref({
  schoolId: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  gradeId: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  className: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  remark: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
});
// async + await，一个函数执行完再执行下一个函数，这样就不会函数并发执行，以至于getInfo函数中schoolId还没被赋值，getGradeList就执行了
const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.schoolId = "";
  dataForm.gradeId = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }
  //超级管理员才拥有该权限
  if (hasSchoolListPermission) {
    getSchoolList();
  }
  getGradeList();
  if (id) {
    getInfo(id);
  }
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/sys/class/" + id).then((res) => {
    Object.assign(dataForm, res.data);
    getGradeList(); //在这里再获取一次年级列表，在修改窗口需要用到schoolId
  });
};
// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};

// 获取年级列表
const getGradeList = () => {
  let schoolId = "";
  if (dataForm.schoolId != "") {
    schoolId = dataForm.schoolId;
  }
  return baseService.get("/sys/grade/list", { schoolId }).then((res) => {
    gradeList.value = res.data;
  });
};
// 当选择的学校改变后，重置gradeList，并且重置当前的年级选项
const resetGradeList = () => {
  dataForm.gradeId = "";
  getGradeList();
};
// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/sys/class", dataForm).then((res) => {
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
