<template>
  <el-dialog v-model="visible" :title="!dataForm.schoolId ? '新增' : '修改'" :close-on-click-modal="false"
             :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()"
             label-width="120px">
      <el-form-item label="学校名称" prop="schoolName">
        <el-input v-model="dataForm.schoolName" placeholder="学校名称"></el-input>
      </el-form-item>
      <el-form-item label="别名" prop="alias">
        <el-input v-model="dataForm.alias" placeholder="别名"></el-input>
      </el-form-item>
      <el-form-item label="全称" prop="fullName">
        <el-input v-model="dataForm.fullName" placeholder="全称"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <ren-radio-group v-model="dataForm.status" dict-type="schoolStatus"></ren-radio-group>
      </el-form-item>
      <el-form-item label="所属教育局" prop="educationBureau">
        <el-input v-model="dataForm.educationBureau" placeholder="所属教育局"></el-input>
      </el-form-item>
      <el-form-item label="单位类型" prop="unitType">
        <ren-radio-group v-model="dataForm.unitType" dict-type="unitType"></ren-radio-group>
      </el-form-item>
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

const dataForm = reactive({
  schoolId: '', schoolName: '', alias: '', fullName: '', status: '', educationBureau: '', createDate: '', unitType: ''
});

const rules = ref({
  schoolName: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  alias: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  fullName: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  status: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  educationBureau: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ],
  unitType: [
    {required: true, message: '必填项不能为空', trigger: 'blur'}
  ]
});

const init = (schoolId?: number) => {
  visible.value = true;
  dataForm.schoolId = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  if (schoolId) {
    getInfo(schoolId);
  }
};

// 获取信息
const getInfo = (schoolId: number) => {
  baseService.get("/sys/school/" + schoolId).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.schoolId ? baseService.post : baseService.put)("/sys/school/", dataForm).then((res) => {
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

<style lang="less" scoped>
.mod-sys__school {
  .role-list {
    .el-select {
      width: 100%;
    }
  }
}
</style>
