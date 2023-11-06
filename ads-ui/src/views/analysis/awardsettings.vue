<template>
  <div class="mod-analysis__awardsettings">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardsettings:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardsettings:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
              <el-table-column prop="id" label="主键" header-align="center" align="center"></el-table-column>
              <el-table-column prop="topic" label="所属五育 0德育, 1智育, 2体育, 3美育, 4劳育" header-align="center" align="center"></el-table-column>
              <el-table-column prop="subtopic" label="所属小类 0奖惩记录, 1实验与竞赛, 2学业成绩, 3体育特长, 4美育成果, 5劳动实践" header-align="center" align="center"></el-table-column>
              <el-table-column prop="level" label="级别 0国家级, 1省级, 2市厅级, 3区级, 4校级" header-align="center" align="center"></el-table-column>
              <el-table-column prop="awardName" label="奖项名称" header-align="center" align="center"></el-table-column>
              <el-table-column prop="grade" label="等级, 自定义" header-align="center" align="center"></el-table-column>
              <el-table-column prop="remarks" label="备注" header-align="center" align="center"></el-table-column>
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
import { reactive, ref, toRefs } from "vue";
import AddOrUpdate from "./awardsettings-add-or-update.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/analysis/awardsettings/page",
  getDataListIsPage: true,
  exportURL: "/analysis/awardsettings/export",
  deleteURL: "/analysis/awardsettings"
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
