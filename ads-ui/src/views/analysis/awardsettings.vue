<template>
  <div class="mod-analysis__awardsettings">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">

      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardsettings:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardsettings:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
      <el-form-item v-if="hasSchoolListPermission">
        <el-select v-model="state.dataForm.schoolId" placeholder="选择学校" clearable> <!--单选 去掉multiple-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-if="hasSchoolListPermission" @click="state.getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
              <el-table-column prop="topic" label="所属五育" header-align="center" align="center">
                <template v-slot="scope">
                  {{ state.getDictLabel("topic", scope.row.topic) }}
                </template>
              </el-table-column>
              <el-table-column prop="subtopic" label="所属小类" header-align="center" align="center">
                <template v-slot="scope">
                  {{ state.getDictLabel("subtopic", scope.row.subtopic) }}
                </template>
              </el-table-column>
              <el-table-column prop="level" label="级别" header-align="center" align="center">
                <template v-slot="scope">
                  {{ state.getDictLabel("award_level", scope.row.level) }}
                </template>
              </el-table-column>
              <el-table-column prop="awardName" label="奖项名称" header-align="center" align="center">

              </el-table-column>
              <el-table-column prop="grade" label="等级" header-align="center" align="center">

              </el-table-column>
              <el-table-column prop="remarks" label="备注" header-align="center" align="center">

              </el-table-column>
            <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('analysis:awardsettings:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('analysis:awardsettings:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
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
import AddOrUpdate from "./awardsettings-add-or-update.vue";
import baseService from "@/service/baseService";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/analysis/awardsettings/page",
  getDataListIsPage: true,
  exportURL: "/analysis/awardsettings/export",
  deleteURL: "/analysis/awardsettings",
  dataForm: {
    schoolId: ""
  }
});

const state = reactive({ ...useView(view), ...toRefs(view) });
const schoolList = ref<any[]>([]);
const hasSchoolListPermission = state.hasPermission("sys:school:list"); // 检查用户是否拥有展示学校列表权限

onMounted(() => {
  //有权限，才执行
  if (hasSchoolListPermission) {
    getSchoolList();
  }
});

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
  });
};

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
