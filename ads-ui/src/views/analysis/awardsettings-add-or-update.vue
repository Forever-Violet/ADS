<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
          <el-form-item label="所属五育 0德育, 1智育, 2体育, 3美育, 4劳育" prop="topic">
        <el-input v-model="dataForm.topic" placeholder="所属五育 0德育, 1智育, 2体育, 3美育, 4劳育"></el-input>
      </el-form-item>
          <el-form-item label="所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践" prop="subtopic">
        <el-input v-model="dataForm.subtopic" placeholder="所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践"></el-input>
      </el-form-item>
          <el-form-item label="级别 0国家级, 1省级, 2市厅级, 3区级, 4校级" prop="level">
        <el-input v-model="dataForm.level" placeholder="级别 0国家级, 1省级, 2市厅级, 3区级, 4校级"></el-input>
      </el-form-item>
          <el-form-item label="奖项名称" prop="awardName">
        <el-input v-model="dataForm.awardName" placeholder="奖项名称"></el-input>
      </el-form-item>
          <el-form-item label="等级, 自定义" prop="grade">
        <el-input v-model="dataForm.grade" placeholder="等级, 自定义"></el-input>
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
  id: '',  topic: '',  subtopic: '',  level: '',  awardName: '',  grade: '',  remarks: ''});

const rules = ref({
          topic: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          subtopic: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          level: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          awardName: [
      { required: true, message: '必填项不能为空', trigger: 'blur' }
    ],
          grade: [
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
  baseService.get("/analysis/awardsettings/" + id).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/analysis/awardsettings", dataForm).then((res) => {
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
