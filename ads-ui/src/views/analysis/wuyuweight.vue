<template>
  <div class="mod-analysis__wuyuweight">
    <el-form :inline="true" :model="state.dataForm" @keyup.enter="state.getDataList()">
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuweight:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-button v-if="state.hasPermission('analysis:wuyuweight:delete')" type="danger" @click="state.deleteHandle()">删除</el-button>
      </el-form-item>
    </el-form>
    <el-table v-loading="state.dataListLoading" :data="state.dataList" border @selection-change="state.dataListSelectionChangeHandle" style="width: 100%">
      <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
              <el-table-column prop="id" label="主键" header-align="center" align="center"></el-table-column>
              <el-table-column prop="schoolId" label="学校ID" header-align="center" align="center"></el-table-column>
              <el-table-column prop="moral" label="德育" header-align="center" align="center"></el-table-column>
              <el-table-column prop="intellectual" label="智育" header-align="center" align="center"></el-table-column>
              <el-table-column prop="physical" label="体育" header-align="center" align="center"></el-table-column>
              <el-table-column prop="artistic" label="美育" header-align="center" align="center"></el-table-column>
              <el-table-column prop="labor" label="劳育" header-align="center" align="center"></el-table-column>
              <el-table-column prop="characterEthics" label="品德评定" header-align="center" align="center"></el-table-column>
              <el-table-column prop="rewardsPunishments" label="奖惩记录" header-align="center" align="center"></el-table-column>
              <el-table-column prop="moralEducationCourses" label="德育课程" header-align="center" align="center"></el-table-column>
              <el-table-column prop="practicalActivities" label="实践活动" header-align="center" align="center"></el-table-column>
              <el-table-column prop="onlineCulture" label="网络文化" header-align="center" align="center"></el-table-column>
              <el-table-column prop="interpersonalRelationships" label="人际关系" header-align="center" align="center"></el-table-column>
              <el-table-column prop="prepManagement" label="预习管理" header-align="center" align="center"></el-table-column>
              <el-table-column prop="planManagement" label="计划管理" header-align="center" align="center"></el-table-column>
              <el-table-column prop="classroomBehavior" label="课堂行为" header-align="center" align="center"></el-table-column>
              <el-table-column prop="classroomAttendance" label="课堂考勤" header-align="center" align="center"></el-table-column>
              <el-table-column prop="homeworkManagement" label="作业管理" header-align="center" align="center"></el-table-column>
              <el-table-column prop="reviewManagement" label="复习管理" header-align="center" align="center"></el-table-column>
              <el-table-column prop="personalAbilities" label="个人能力" header-align="center" align="center"></el-table-column>
              <el-table-column prop="academicPerformance" label="学业成绩" header-align="center" align="center"></el-table-column>
              <el-table-column prop="experimentalCompetitions" label="实验竞赛" header-align="center" align="center"></el-table-column>
              <el-table-column prop="examinationMetrics" label="体检指标" header-align="center" align="center"></el-table-column>
              <el-table-column prop="physicalFitnessScores" label="体能成绩" header-align="center" align="center"></el-table-column>
              <el-table-column prop="sportingSpecialties" label="体育特长" header-align="center" align="center"></el-table-column>
              <el-table-column prop="healthyLiving" label="健康生活" header-align="center" align="center"></el-table-column>
              <el-table-column prop="mentalQualities" label="心理素质" header-align="center" align="center"></el-table-column>
              <el-table-column prop="physicalEducationCourses" label="体育课程" header-align="center" align="center"></el-table-column>
              <el-table-column prop="artsCourses" label="美育课程" header-align="center" align="center"></el-table-column>
              <el-table-column prop="artsAchievements" label="美育成果" header-align="center" align="center"></el-table-column>
              <el-table-column prop="artsActivities" label="美育活动" header-align="center" align="center"></el-table-column>
              <el-table-column prop="laborPractices" label="劳动实践" header-align="center" align="center"></el-table-column>
              <el-table-column prop="laborCourses" label="劳动课程" header-align="center" align="center"></el-table-column>
            <el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
        <template v-slot="scope">
          <el-button v-if="state.hasPermission('analysis:wuyuweight:update')" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
          <el-button v-if="state.hasPermission('analysis:wuyuweight:delete')" type="primary" link @click="state.deleteHandle(scope.row.id)">删除</el-button>
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
import AddOrUpdate from "./wuyuweight-add-or-update.vue";

const view = reactive({
  deleteIsBatch: true,
  getDataListURL: "/analysis/wuyuweight/page",
  getDataListIsPage: true,
  exportURL: "/analysis/wuyuweight/export",
  deleteURL: "/analysis/wuyuweight"
});

const state = reactive({ ...useView(view), ...toRefs(view) });

const addOrUpdateRef = ref();
const addOrUpdateHandle = (id?: number) => {
  addOrUpdateRef.value.init(id);
};
</script>
