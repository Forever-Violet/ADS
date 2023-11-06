<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
          <el-form-item label="学生学号" prop="studentNo">
        <el-input v-model="dataForm.studentNo" placeholder="学生学号"></el-input>
      </el-form-item>
          <el-form-item label="奖项id" prop="awardId">
        <el-input v-model="dataForm.awardId" placeholder="奖项id"></el-input>
      </el-form-item>
          <el-form-item label="获奖等级" prop="grade">
        <el-input v-model="dataForm.grade" placeholder="获奖等级"></el-input>
      </el-form-item>
          <el-form-item label="获奖日期" prop="awardDate">
        <el-input v-model="dataForm.awardDate" placeholder="获奖日期"></el-input>
      </el-form-item>
          <el-form-item label="备注" prop="remarks">
        <el-input v-model="dataForm.remarks" placeholder="备注"></el-input>
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
  id: '',  studentNo: '',  awardId: '',  grade: '',  awardDate: '',  remarks: ''});

const rules = ref({
          studentNo: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          awardId: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          grade: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          awardDate: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          remarks: [
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
  baseService.get("/analysis/awardrecords/" + id).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/analysis/awardrecords", dataForm).then((res) => {
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
