<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
      <el-form-item v-if="hasSchoolListPermission" prop="schoolName" label="所属学校">
        <el-select v-model="dataForm.schoolId" placeholder="选择学校" clearable>
          <!--单选 去掉multiple , clearable重置-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学期名称" prop="semesterName">
        <el-input v-model="dataForm.semesterName" placeholder="学期名称"></el-input>
      </el-form-item>
      <!-- 合并的起止日期选框 -->
      <el-form-item label="学期起止日期" prop="semesterName">
        <el-config-provider :locale="locale">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="handleDateRangeChange"
            :picker-options="{ isRange: false, showWeekNumber: false }"
          />
        </el-config-provider>
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
import {ElConfigProvider, ElMessage} from "element-plus";
import useView from "@/hooks/useView";
import locale from "element-plus/es/locale/lang/zh-cn";
const emit = defineEmits(["refreshDataList"]);

const visible = ref(false);
const dataFormRef = ref();
const schoolList = ref<any[]>([]);
const dataForm = reactive({
  id: "",
  schoolId: "",
  startDate: "",
  endDate: "",
  semesterName: "",
  remark: "",
  createDate: ""
});
const state = reactive({ ...useView(dataForm), ...toRefs(dataForm) });
const hasSchoolListPermission = state.hasPermission("sys:school:list");
const dateRange = ref<any[]>([]);
const rules = ref({
  schoolId: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  semesterName: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  remark: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
});

// 日期选择后分离起止日期到各自的字段
const handleDateRangeChange = () => {
  dataForm.startDate = dateRange.value[0];
  dataForm.endDate = dateRange.value[1];
};


const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.schoolId = "";
  dateRange.value = [];

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
  baseService.get("/sys/semester/" + id).then((res) => {
    Object.assign(dataForm, res.data);
    // 将 dataForm 中的日期数据赋值给 dateRange，回显学期起止日期
    dateRange.value = [dataForm.startDate, dataForm.endDate];
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
    (!dataForm.id ? baseService.post : baseService.put)("/sys/semester", dataForm).then((res) => {
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
