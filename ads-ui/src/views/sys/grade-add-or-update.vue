<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
      <el-form-item v-if="hasSchoolListPermission" prop="schoolName" label="所属学校">
        <el-select v-model="dataForm.schoolId" placeholder="选择学校" clearable>
          <!--单选 去掉multiple , clearable重置-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="年级名称" prop="gradeName">
        <el-input v-model="dataForm.gradeName" placeholder="年级名称"></el-input>
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
import { reactive, ref, toRefs } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
import useView from "@/hooks/useView";

const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();
const schoolList = ref<any[]>([]);
const dataForm = reactive({
  id: "",
  schoolId: "",
  gradeName: "",
  remark: "",
  createDate: ""
});
const state = reactive({ ...useView(dataForm), ...toRefs(dataForm) });
const hasSchoolListPermission = state.hasPermission("sys:school:list");

const rules = ref({
  schoolId: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  gradeName: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  remark: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.schoolId = "";


  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }
  if (hasSchoolListPermission) {
    getSchoolList();
  }
  if (id) {
    getInfo(id);
  }

};

// 获取信息
const getInfo = (id: number) => {
  baseService.get("/sys/grade/" + id).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/sys/grade", dataForm).then((res) => {
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
