<template>
  <div class="mod-analysis__awardrecords">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item v-if="hasSchoolListPermission">
        <el-select v-model="state.dataForm.schoolId" placeholder="选择学校" clearable> <!--单选 去掉multiple-->
          <el-option v-for="school in schoolList" :key="school.schoolId" :label="school.schoolName" :value="school.schoolId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <ren-select v-model="state.dataForm.topic" dict-type="topic" placeholder="所属五育"></ren-select>
      </el-form-item>
      <el-form-item>
        <ren-select v-model="state.dataForm.level" dict-type="award_level" placeholder="所属类型"></ren-select>
      </el-form-item>
      <!-- 合并的起止日期选框 -->
      <el-form-item>
        <el-config-provider :locale="locale">
          <el-date-picker
          v-model="dateRange"
          type="daterange"
          range-separator="至"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="handleDateRangeChange"
          :picker-options="{ isRange: false, showWeekNumber: false }"
          />
        </el-config-provider>
      </el-form-item>
      <el-form-item>
        <el-input v-model="state.dataForm.studentNo" placeholder="学号或姓名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="state.getDataList()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardrecords:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:awardrecords:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('sys:user:export')" type="info" @click="state.exportHandle()">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" @sort-change="state.dataListSortChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
      <el-table-column prop="studentNo" label="学号" header-align="center" align="center"></el-table-column>
      <el-table-column prop="studentName" label="姓名" header-align="center" align="center"></el-table-column>
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
      <el-table-column prop="awardName" label="奖项名称" header-align="center" align="center"></el-table-column>
      <el-table-column prop="grade" label="等级" header-align="center" align="center"></el-table-column>
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
import { onMounted, reactive, ref, toRefs } from "vue";
import AddOrUpdate from "./awardrecords-add-or-update.vue";
import baseService from "@/service/baseService";
import { ElConfigProvider } from "element-plus";
import locale from "element-plus/es/locale/lang/zh-cn";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/analysis/awardrecords/page",
  getDataListIsPage: true,
  // 在自动选择学校后才调用getDataList，页面刚创建不调用，如果不是超级管理员，那么还是页面创建时调用
  createdIsNeed: false,
  exportURL: "/analysis/awardrecords/export",
  deleteURL: "/analysis/awardrecords",
  dataForm: {
    schoolId: "",
    topic: "",
    level: "",
    studentNo: "",
    startDate: "",
    endDate: ""
  }
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const dateRange = ref<any[]>([]);

const hasSchoolListPermission = state.hasPermission("sys:school:list"); // 检查用户是否拥有展示学校列表权限

const schoolList = ref<any[]>([]);

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};

// 日期选择后分离起止日期到各自的字段
const handleDateRangeChange = () => {
  state.dataForm.startDate = dateRange.value[0];
  state.dataForm.endDate = dateRange.value[1];
};

onMounted(() => {
  //有权限，才执行
  if (hasSchoolListPermission) {
    getSchoolList();
  } else {
    // 没有学校列表权限的话，直接获取数据
    state.getDataList();
  }
});

// 获取学校列表
const getSchoolList = () => {
  return baseService.get("/sys/school/list").then((res) => {
    schoolList.value = res.data;
    // 检查返回的列表是否非空
    if (schoolList.value && schoolList.value.length > 0) {
      // 设置默认选中第一个学校
      state.dataForm.schoolId = schoolList.value[0].schoolId;
    }
    // 获取数据
    state.getDataList();
  });
};
</script>
