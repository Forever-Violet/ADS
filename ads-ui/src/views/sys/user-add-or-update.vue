<template>
  <el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="120px">
      <el-form-item prop="username" label="用户名">
        <el-input v-model="dataForm.username" placeholder="用户名"></el-input>
      </el-form-item>
      <el-form-item v-if="hasSchoolListPermission" prop="schoolName" label="所属学校">
        <el-select v-model="dataForm.schoolId" placeholder="选择学校" clearable @change="resetClassList"> <!--单选 去掉multiple , clearable重置-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="password" label="密码" :class="{ 'is-required': !dataForm.id }">
        <el-input v-model="dataForm.password" type="password" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword" label="确认密码" :class="{ 'is-required': !dataForm.id }">
        <el-input v-model="dataForm.confirmPassword" type="password" placeholder="确认密码"></el-input>
      </el-form-item>
      <el-form-item prop="realName" label="真实姓名">
        <el-input v-model="dataForm.realName" placeholder="真实姓名"></el-input>
      </el-form-item>
      <el-form-item prop="gender" label="性别">
        <ren-radio-group v-model="dataForm.gender" dict-type="gender"></ren-radio-group>
      </el-form-item>
      <el-form-item prop="email" label="邮箱">
        <el-input v-model="dataForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="mobile" label="手机号">
        <el-input v-model="dataForm.mobile" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item prop="roleIdList" label="角色配置" class="role-list">  <!--多选-->
        <el-select v-model="dataForm.roleIdList" multiple placeholder="角色配置">
          <el-option v-for="role in roleList" :key="role.id" :label="role.name" :value="role.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="classIdList" label="班级配置" class="role-list">
        <el-select v-model="dataForm.classIdList" multiple placeholder="班级配置">
          <el-option v-for="clazz in classList" :key="clazz.id" :label="clazz.gradeName + '-' + clazz.className" :value="clazz.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="status" label="状态">
        <el-radio-group v-model="dataForm.status">
          <el-radio :label="0">停用</el-radio>
          <el-radio :label="1">正常</el-radio>
        </el-radio-group>
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
import { isEmail, isMobile } from "@/utils/utils";
import { IObject } from "@/types/interface";
import { ElMessage } from "element-plus";
import useView from "@/hooks/useView";
const emit = defineEmits(["refreshDataList"]);

const dataForm = reactive({
  id: "",
  username: "",
  schoolId: "",
  schoolName: "",
  password: "",
  confirmPassword: "",
  realName: "",
  gender: 0,
  email: "",
  mobile: "",
  roleIdList: [] as IObject[],
  classIdList: [] as IObject[],
  status: 1
});

const visible = ref(false);
const roleList = ref<any[]>([]);
const classList = ref<any[]>([]);
const schoolList = ref<any[]>([]);
const dataFormRef = ref();
const state = reactive({ ...useView(dataForm), ...toRefs(dataForm) });
const hasSchoolListPermission = state.hasPermission("sys:school:list");

const validatePassword = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (!dataForm.id && !/\S/.test(value)) {
    return callback(new Error("必填项不能为空"));
  }
  callback();
};
const validateConfirmPassword = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (!dataForm.id && !/\S/.test(value)) {
    return callback(new Error("必填项不能为空"));
  }
  if (dataForm.password !== value) {
    return callback(new Error("确认密码与密码输入不一致"));
  }
  callback();
};
const validateEmail = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (value && !isEmail(value)) {
    return callback(new Error("邮箱格式错误"));
  }
  callback();
};
const validateMobile = (rule: any, value: string, callback: (e?: Error) => any): any => {
  if (value && !isMobile(value)) {
    return callback(new Error("手机格式错误"));
  }
  callback();
};
const rules = ref({
  username: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  schoolId: [{ required: true, message: "学校不能为空", trigger: "change" }],
  password: [{ validator: validatePassword, trigger: "blur" }],
  confirmPassword: [{ validator: validateConfirmPassword, trigger: "blur" }],
  realName: [{ required: true, message: "必填项不能为空", trigger: "blur" }],
  email: [{ validator: validateEmail, trigger: "blur" }],
  mobile: [{ validator: validateMobile, trigger: "blur" }]
});

const init = (id?: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.schoolId = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }
  getCurrentUserInfo();
  getClassList();
  getRoleList();
  if (id) {
    getInfo(id);
  }
  if (hasSchoolListPermission) {
    getSchoolList();
  }
};

// 获取角色列表
const getRoleList = () => {
  return baseService.get("/sys/role/list").then((res) => {
    roleList.value = res.data;
  });
};

// 获取班级列表
const getClassList = () => {
  let schoolId = "";
  if (dataForm.schoolId != "") {
    schoolId = dataForm.schoolId;
  }
  return baseService.get("/sys/class/list", { schoolId }).then((res) => {
    classList.value = res.data;
  });
};
// 当学校选项改变后，重新查询班级列表，并重置当前的班级选项
const resetClassList = () => {
  dataForm.classIdList = [];
  getClassList();
};

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};

// 获取信息
const getInfo = (id: number) => {
  baseService.get(`/sys/user/${id}`).then((res) => {
    Object.assign(dataForm, res.data);
    getClassList(); // 在这里重新获取一下，因为修改页面需要用到schoolId去获取班级列表，而在init中，getInfo还没执行完就执行了getClassList
  });
};

// 获取当前登录用户的学校id
const getCurrentUserInfo = () => {
  baseService.get(`/sys/user/info`).then((res) => {
    dataForm.schoolId = res.data.schoolId; //填充到表单
  });
};

// 表单提交
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    (!dataForm.id ? baseService.post : baseService.put)("/sys/user", {
      ...dataForm,
      roleIdList: [...dataForm.roleIdList]
    }).then((res) => {
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

<style lang="less" scoped>
.mod-sys__user {
  .role-list {
    .el-select {
      width: 100%;
    }
  }
}
</style>
