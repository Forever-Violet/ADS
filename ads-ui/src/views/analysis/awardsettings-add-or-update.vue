<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
      <el-form-item v-if="hasSchoolListPermission && !dataForm.id" prop="schoolName" label="所属学校">
        <el-select v-model="dataForm.schoolId" placeholder="选择学校" clearable>
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属五育" prop="topic">
        <ren-select v-model="dataForm.topic" dict-type="topic" placeholder="所属五育" @change="resetSubtopicOption"></ren-select>
      </el-form-item>
      <el-form-item label="所属小类" prop="subtopic">
        <el-select v-model="dataForm.subtopic" placeholder="所属小类" clearable>
          <el-option v-if="dataForm.topic == 0" label="奖惩记录" :key="0" :value="0"></el-option>
          <el-option v-if="dataForm.topic == 1" label="实验与竞赛" :key="1" :value="1"></el-option>
          <el-option v-if="dataForm.topic == 1" label="学业成绩" :key="2" :value="2"></el-option>
          <el-option v-if="dataForm.topic == 2" label="体育特长" :key="3" :value="3"></el-option>
          <el-option v-if="dataForm.topic == 3" label="美育成果" :key="4" :value="4"></el-option>
          <el-option v-if="dataForm.topic == 4" label="劳动实践" :key="5" :value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="级别" prop="level">
        <ren-radio-group v-model="dataForm.level" dict-type="award_level"></ren-radio-group>
      </el-form-item>
      <el-form-item label="奖项名称" prop="awardName">
        <el-input v-model="dataForm.awardName" placeholder="奖项名称" style="width: 50%"></el-input>
      </el-form-item>
      <el-form-item label="等级" prop="grade">
        <el-radio-group v-model="dataForm.grade">
          <el-radio label="无">无</el-radio>
          <el-radio label="custom">自定义</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item v-if="dataForm.grade !== '无'">
        <div v-for="(level, index) in customLevels" :key="index" class="custom-grade-input">
          <el-input v-model="customLevels[index].name" placeholder="自定义等级"></el-input>
          <el-button v-if="customLevels.length > 1" @click="removeCustomLevel(index)" type="danger" class="custom-button">-</el-button>
          <el-button @click="addCustomLevel(index)" type="success" class="custom-button">+</el-button>
        </div>
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
const dataForm = reactive({
  id: "",
  schoolId: "",
  topic: "",
  subtopic: "",
  level: "",
  awardName: "",
  grade: "无", //新增时，等级单选框默认为无
  remarks: ""
});
// 自定义的等级列表
const customLevels = ref([{ name: "" }, { name: "" }, { name: "" }]);

const schoolList = ref<any[]>([]);
const state = reactive({ ...useView(dataForm), ...toRefs(dataForm) });
const hasSchoolListPermission = state.hasPermission("sys:school:list");
const rules = ref({
  topic: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  subtopic: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  level: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  awardName: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  grade: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  remarks: [{ required: true, message: "必填项不能为空", trigger: "blur" }]
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
  } else if (hasSchoolListPermission) {
    // 获取学校列表
    getSchoolList();
  }
};
// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
    // 检查返回的列表是否非空
    if (schoolList.value && schoolList.value.length > 0) {
      // 设置默认选中第一个学校
      dataForm.schoolId = schoolList.value[0].schoolId;
    }
  });
};
// 获取信息
const getInfo = (id: number) => {
  baseService.get("/analysis/awardsettings/" + id).then((res) => {
    Object.assign(dataForm, res.data);
    // 如果等级不为无，设置 grade 的值为 'custom', 并将grade原本内容分隔至customLevels
    if (dataForm.grade !== "无") {
      dataForm.grade = "custom"; //用于单选框选择自定义
      // 处理自定义等级的回显
      customLevels.value = res.data.grade.split(",").map((name: string) => ({ name }));
    }
  });

};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }

    // 如果单选选项不是"无"，将所有自定义等级以逗号分隔的形式存储在dataForm.grade中
    if (dataForm.grade !== "无") {
      dataForm.grade = customLevels.value.map((level) => level.name).join(",");
    }

    (!dataForm.id ? baseService.post : baseService.put)("/analysis/awardsettings", dataForm).then((res) => {
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

const addCustomLevel = (index: number) => {
  customLevels.value.splice(index + 1, 0, { name: "" });
};

const removeCustomLevel = (index: number) => {
  customLevels.value.splice(index, 1);
};

const resetSubtopicOption = () => {
  // 当所属五育选项改变后，置空当前的所属小类
  dataForm.subtopic = "";
};

defineExpose({
  init
});
</script>

<style scoped>
.custom-grade-input {
  margin-bottom: 10px;
  margin-right: 10px;
  display: flex;
  align-items: center;
}

.custom-button {
  margin-left: 10px;
  width: 20px; /* Adjust the width as needed */
  height: 20px; /* Adjust the height as needed */
  font-size: 16px; /* Adjust the font size as needed */
}
</style>
