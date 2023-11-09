<template>
  <div class="mod-analysis__awardrecords">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardrecords:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardrecords:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
              <el-table-column prop="studentNo" label="学号" header-align="center" align="center"></el-table-column>
              <el-table-column prop="topic" label="所属五育" header-align="center" align="center"></el-table-column>
              <el-table-column prop="subtopic" label="所属小类" header-align="center" align="center"></el-table-column>
              <el-table-column prop="level" label="级别" header-align="center" align="center"></el-table-column>
              <el-table-column prop="awardName" label="奖项名称" header-align="center" align="center"></el-table-column>
              <el-table-column prop="grade" label="登记" header-align="center" align="center"></el-table-column>
              <el-table-column prop="awardDate" label="获奖日期" header-align="center" align="center"></el-table-column>
              <el-table-column prop="remarks" label="备注" header-align="center" align="center"></el-table-column>
            <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('analysis:awardrecords:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('analysis:awardrecords:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
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
import AddOrUpdate from "./awardrecords-add-or-update.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/analysis/awardrecords/page",
  getDataListIsPage: true,
  exportURL: "/analysis/awardrecords/export",
  deleteURL: "/analysis/awardrecords"
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
