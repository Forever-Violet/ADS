<template>
  <div class="mod-sys__user">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-input v-model="state.dataForm.username" placeholder="用户名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <ren-select v-model="state.dataForm.gender" dict-type="gender" placeholder="性别"></ren-select>
      </el-form-item>
      <el-form-item v-if="hasSchoolListPermission">
        <el-select v-model="state.dataForm.schoolId" placeholder="选择学校" clearable> <!--单选 去掉multiple-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:user:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:user:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:user:export')" type="info" @click="state.exportHandle()">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="schoolName" label="所属学校" header-align="center" align="center"></el-table-column>
      <el-table-column prop="realName" label="姓名" header-align="center" align="center"></el-table-column>
      <el-table-column prop="email" label="邮箱" header-align="center" align="center"></el-table-column>
      <el-table-column prop="mobile" label="手机号" sortable="custom" header-align="center" align="center"></el-table-column>
      <el-table-column prop="gender" label="性别" sortable="custom" header-align="center" align="center">
        <template v-slot="scope">
          {{ state.getDictLabel("gender", scope.row.gender) }}
        </template>
      </el-table-column>
      <el-table-column prop="roleNameList" label="角色" header-align="center" align="center">
        <template v-slot="scope">
          <span>{{ scope.row.roleNameList.join(', ') }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" sortable="custom" header-align="center" align="center">
        <template v-slot="scope">
          <el-tag v-if="scope.row.status === 0" size="small" type="danger">停用</el-tag>
          <el-tag v-else size="small" type="success">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createDate" label="创建时间" sortable="custom" header-align="center" align="center" width="180"></el-table-column>
      <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('sys:user:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('sys:user:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="state.page" :page-sizes="[10, 20, 50, 100]" :page-size="state.limit" :total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="state.pageSizeChangeHandle" @current-change="state.pageCurrentChangeHandle"> </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update ref="addOrUpdateRef" @refreshDataList="state.getDataList"></add-or-update>
  </div>
</template>

<script lang="ts" setup>
import useView from "@/hooks/useView";
import { reactive, ref, toRefs, onMounted } from "vue";
import AddOrUpdate from "./user-add-or-update.vue";
import baseService from "@/service/baseService";


const view = reactive({
  getDataListURL: "/sys/user/page",
  getDataListIsPage: true,
  deleteURL: "/sys/user",
  deleteIsBatch: true,
  exportURL: "/sys/user/export",
  dataForm: {
    username: "",
    schoolId: "",
    postId: "",
    gender: ""
  }
});
const schoolList = ref<any[]>([]);

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
});

// 获取信息
/*const getInfo = (id: number) => {
  baseService.get(`/sys/user/${id}`).then((res) => {
    Object.assign(view.dataForm, res.data);
  });
};*/

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};
</script>
