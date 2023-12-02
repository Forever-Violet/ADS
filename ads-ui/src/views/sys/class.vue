<template>
  <div class="mod-sys__class">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:class:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:class:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
      <el-form-item v-if="hasSchoolListPermission">
        <el-select v-model="state.dataForm.schoolId" placeholder="选择学校" clearable @change="resetGradeList"> <!--单选 去掉multiple-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="state.dataForm.gradeId" placeholder="选择年级" clearable>
          <el-option v-for="grade in gradeList" :key="grade.id" :label="grade.gradeName" :value="grade.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>


    </el-form>

    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
              <el-table-column prop="schoolName" label="学校名称" header-align="center" align="center"></el-table-column>
              <el-table-column prop="gradeName" label="年级名称" header-align="center" align="center"></el-table-column>
              <el-table-column prop="className" label="班级名称" header-align="center" align="center"></el-table-column>
              <el-table-column prop="remark" label="备注" header-align="center" align="center"></el-table-column>
              <el-table-column prop="createDate" label="创建时间" header-align="center" align="center"></el-table-column>
            <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('sys:class:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('sys:class:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"> </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList">确定</add-or-update>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs, onMounted } from "vue";
import AddOrUpdate from "./class-add-or-update.vue";
import baseService from "@/service/baseService";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/sys/class/page",
  getDataListIsPage: true,
  exportURL: "/sys/class/export",
  deleteURL: "/sys/class",
  dataForm: {
    schoolId: "",
    gradeId: ""
  }
});
const schoolList = ref<any[]>([]);
const gradeList = ref<any[]>([]);
const state = reactive({ ...useView(view), ...toRefs(view) });
const hasSchoolListPermission = state.hasPermission("sys:school:list"); // 检查用户是否拥有展示学校列表权限

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};

onMounted(() => {
  //超级管理员才拥有该权限
  if (hasSchoolListPermission) {
    getSchoolList();
  }
  getGradeList();
});

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};

// 获取年级列表
const getGradeList = () => {
  let schoolId = "";
  if (state.dataForm.schoolId != "") {
    schoolId = state.dataForm.schoolId;
  }
  return baseService.get("/sys/grade/list", { schoolId }).then((res) => {
    gradeList.value = res.data;
  });
};
// 当选择的学校改变后，重置gradeList, 并重置年级选项
const resetGradeList = () => {
  state.dataForm.gradeId = "";
  getGradeList();
};
</script>
