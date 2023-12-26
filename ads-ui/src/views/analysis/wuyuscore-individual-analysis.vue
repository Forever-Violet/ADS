<template>
  <el-dialog v-model="visible" title="" :close-on-click-modal="false" :close-on-press-escape="false" @opened="initChart" :key="dialogKey">
    <el-form :model="dataForm" v-if="!reloading" ref="dataFormRef" label-width="0px">
      <div id="pdfDom">
        <h3 style="text-align: center">{{ dataForm.studentNo }} - {{ dataForm.studentName }} - 个人诊断报告</h3>
        <div>
          <canvas ref="chartContainer" width="100%" height="500"></canvas>
        </div>
        <!--white-space 属性值及其效果：
        normal：合并空格，自动换行。
        nowrap：合并空格，不自动换行。
        pre：保留所有空格和换行符，不自动换行。
        pre-wrap：保留所有空格和换行符，自动换行。
        pre-line：合并空格，保留换行符，自动换行。-->
        <div v-if="loading" style="white-space: pre-wrap; font-size: 16px; text-indent: 2em">
          {{ dataForm.response }}
        </div>
        <el-input type="textarea" v-if="!loading" v-model="dataForm.response" placeholder="请输入内容" show-word-limit
                  :rows="6" style="font-size: 16px; text-indent: 2em" maxlength="3000" class="borderless-textarea"></el-input>
      </div>
      <div v-if="loading" style="text-align: center; font-size: 18px; color: deepskyblue">生成PDF中...</div>
    </el-form>
    <div v-if="dataForm.id == '' || reloading" style="text-align: center; font-size: 18px; color: deepskyblue">报告生成中...</div>
    <template v-slot:footer v-if="!loading">
      <el-button v-if="isNotStudent" type="primary" @click="dataFormSubmitHandle()">保存</el-button>
      <el-button v-if="isNotStudent" type="warning" @click="reCreateReportHandle()">重新生成</el-button>
      <el-button type="info" @click="exportPDFHandle()">导出为PDF</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref, toRefs, onMounted, watch, nextTick } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
import htmlToPdf from "@/utils/htmlToPDF";
import Chart from "chart.js/auto";
import useView from "@/hooks/useView";

const visible = ref(false);
const dataFormRef = ref();
const loading = ref(false); //缓冲动画flag
const reloading = ref(false); //缓冲动画flag
const chartContainer = ref<HTMLCanvasElement | null>(null); // 指向图表的引用 chartContainer 的类型为 HTMLCanvasElement
let myChart: Chart<"bar", number[], string> | null = null; // 用于保存图表实例
const dialogKey = ref();

const dataForm = reactive({
  id: "",
  classId: "",
  semesterId: "",
  scoreId: "",
  studentNo: "",
  studentName: "",
  response: ""
});
const state = reactive({ ...useView(dataForm), ...toRefs(dataForm) });
const isNotStudent = state.hasPermission("sys:grade:list"); //通过是否拥有年级列表的权限来判断是不是学生
const score = reactive({
  id: "",
  schoolId: "",
  studentNo: "",
  studentName: "",
  characterEthics: "",
  rewardsPunishments: "",
  moralEducationCourses: "",
  practicalActivities: "",
  onlineCulture: "",
  interpersonalRelationships: "",
  prepManagement: "",
  planManagement: "",
  classroomBehavior: "",
  classroomAttendance: "",
  homeworkManagement: "",
  reviewManagement: "",
  personalAbilities: "",
  academicPerformance: "",
  experimentalCompetitions: "",
  examinationMetrics: "",
  physicalFitnessScores: "",
  sportingSpecialties: "",
  healthyLiving: "",
  mentalQualities: "",
  physicalEducationCourses: "",
  artsCourses: "",
  artsAchievements: "",
  artsActivities: "",
  laborPractices: "",
  laborCourses: "",
  comprehensiveScore: "",
  academicLevel: ""
});

const init = async (id: string) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.response = "";
  reloading.value = false; //重置标志
  loading.value = false;

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  // 这个id是五育score id
  getInfo(id);
  getScoreInfo(id);
};

// 在 Dialog 的内容动态渲染之后创建图表。在Dialog的opened事件中初始化图表。此事件在 Dialog完全打开之后触发，此时所有的DOM元素都已渲染完毕。
const initChart = () => {
  nextTick(() => {
    createBarChart();
  });
};

// 在创建新图表之前，检查这个变量是否已经有一个图表实例。如果有，先销毁它。
const destroyChart = () => {
  if (myChart) {
    myChart.destroy();
    myChart = null;
  }
};

// 创建条形图的函数
const createBarChart = () => {
  // 销毁旧的图表实例
  destroyChart();

  if (chartContainer.value) {
    //是否已经指向了一个有效的DOM元素
    console.log(score);
    const ctx = chartContainer.value.getContext("2d");
    console.log(ctx);
    if (!ctx) return;

    const studentData: Record<string, number> = {
      品德评定: Number(score.characterEthics),
      奖惩记录: Number(score.rewardsPunishments),
      德育课程: Number(score.moralEducationCourses),
      实践活动: Number(score.practicalActivities),
      网络文化: Number(score.onlineCulture),
      人际关系: Number(score.interpersonalRelationships),
      预习管理: Number(score.prepManagement),
      计划管理: Number(score.planManagement),
      课堂行为: Number(score.classroomBehavior),
      课堂考勤: Number(score.classroomAttendance),
      作业管理: Number(score.homeworkManagement),
      复习管理: Number(score.reviewManagement),
      个人能力: Number(score.personalAbilities),
      学业成绩: Number(score.academicPerformance),
      实验竞赛: Number(score.experimentalCompetitions),
      体检指标: Number(score.examinationMetrics),
      体能成绩: Number(score.physicalFitnessScores),
      体育特长: Number(score.sportingSpecialties),
      健康生活: Number(score.healthyLiving),
      心理素质: Number(score.mentalQualities),
      体育课程: Number(score.physicalEducationCourses),
      美育课程: Number(score.artsCourses),
      美育成果: Number(score.artsAchievements),
      美育活动: Number(score.artsActivities),
      劳动实践: Number(score.laborPractices),
      劳动课程: Number(score.laborCourses),
      五育综合: Number(score.comprehensiveScore)
    };

    const labels = Object.keys(studentData);
    const data = Object.values(studentData);

    myChart = new Chart(ctx, {
      type: "bar",
      data: {
        labels: labels,
        datasets: [
          {
            label: "学生综合素质评价",
            data: data,
            backgroundColor: [
              "rgba(255, 99, 132, 0.2)",
              "rgba(54, 162, 235, 0.2)"
              // 其他颜色
            ],
            borderColor: [
              "rgba(255, 99, 132, 1)",
              "rgba(54, 162, 235, 1)"
              // 其他颜色
            ],
            borderWidth: 1
          }
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  } else {
    console.error("Canvas element is not found 图表元素未被引用!");
  }
};

// 根据五育score id 获取 诊断报告信息
const getInfo = (id: string) => {
  // 首次查询报告，如果无记录，会生成
  baseService.get(`/analysis/wuyuscore/individual/${id}`).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

const getScoreInfo = (id: string) => {
  baseService.get("/analysis/wuyuscore/" + id).then((res) => {
    Object.assign(score, res.data);
  });
};

const reCreateReportHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    //dataForm.id = ""; //先置空 报告的id，使前端显示正在生成报告
    reloading.value = true;
    baseService.put(`/analysis/wuyuscore/individual/re/${dataForm.scoreId}`).then(() => {
      // 重新生成后，调用查询函数
      getInfo(dataForm.scoreId); //不能用number，string转number会精度丢失（太长就会去掉超出长度的部分自动四舍五入），直接用string
      // 查询分数
      getScoreInfo(dataForm.scoreId);
      reloading.value = false;
      // 更新 key 来强制重新渲染弹窗
      dialogKey.value = new Date().getTime();
      // 重新创建图表
      initChart();
    });
  });
};

// 导出为PDF
const exportPDFHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    loading.value = true;
    setTimeout(() => {
      htmlToPdf.getPdf(dataForm.studentNo + "-" + dataForm.studentName + "-" + "个人诊断报告", loading.value, () => {
        loading.value = false; //结束的时候会调用这个回调函数，把缓冲动画设置为false
      });
    }, 1500); //延迟1.5秒再执行，太快会画不到转换的样式
  });
};

// 表单提交，保存报告 （修改）
const dataFormSubmitHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }

    baseService.put("/analysis/wuyuanalysisresult", dataForm).then((res) => {
      ElMessage.success({
        message: "成功",
        duration: 500,
        onClose: () => {
          visible.value = false;
        }
      });
    });
  });
};

defineExpose({
  init
});
</script>

<style scoped></style>
