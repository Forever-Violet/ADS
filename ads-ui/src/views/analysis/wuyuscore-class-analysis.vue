<template>
  <el-dialog v-model="visible" title="" :close-on-click-modal="false" :close-on-press-escape="false" @opened="initChart" :key="dialogKey">
    <el-form :model="dataForm" v-if="!reloading" ref="dataFormRef" label-width="0px">
      <div id="pdfDom">
        <h3 style="text-align: center">{{ dataForm.gradeName }} - {{ dataForm.className }} - 班级诊断报告</h3>
        <div>
          <div>
            <canvas ref="barChartContainer" width="300px" height="300px"></canvas><!--条形图容器-->
          </div>
          <div style="text-align: center; margin-top: 5px; margin-bottom: 3px; font-size: 16px; color: #6d6c6c;">
            学生五育综合等级分布
          </div>
          <div>
            <canvas ref="pieChartContainer" width="300px" height="300px"></canvas><!--饼状图容器-->
          </div>
        </div>
        <div v-if="loading" style="white-space: pre-wrap; font-size: 16px;">
          {{ dataForm.response }}
        </div>
        <el-input type="textarea" v-if="!loading" v-model="dataForm.response" placeholder="请确保该学期该班级有数据导入" show-word-limit :rows="6" style="font-size: 16px; text-indent: 2em" maxlength="3000" class="borderless-textarea"></el-input>
      </div>
      <div v-if="loading" style="text-align: center; font-size: 18px; color: deepskyblue">生成PDF中...</div>
    </el-form>

    <div v-if="dataForm.id == '' || reloading" style="white-space: pre-wrap; text-align: center; font-size: 18px; color: deepskyblue">报告生成中...</div>
    <template v-slot:footer v-if="!loading">
      <el-button type="primary" @click="dataFormSubmitHandle()">保存</el-button>
      <el-button type="warning" @click="reCreateReportHandle()">重新生成</el-button>
      <el-button type="info" @click="exportPDFHandle()">导出为PDF</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref, onMounted, watch, nextTick } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
import htmlToPdf from "@/utils/htmlToPDF";
import Chart from "chart.js/auto";

const visible = ref(false);
const dataFormRef = ref();
const loading = ref(false); //缓冲动画flag
const reloading = ref(false);
const barChartContainer = ref<HTMLCanvasElement | null>(null); // 指向图表的引用 chartContainer 的类型为 HTMLCanvasElement
const pieChartContainer = ref<HTMLCanvasElement | null>(null); //指向饼状图的引用
let myBarChart: Chart<"bar", number[], string> | null = null; // 用于保存图表实例
let myPieChart: Chart<"pie", number[], string> | null = null; // 用于保存图表实例
const dialogKey = ref();

const dataForm = reactive({
  id: "",
  semesterId: "",
  classId: "",
  className: "",
  gradeName: "",
  studentNum: "",
  response: "",
  moralScore: "",
  intellectualScore: "",
  physicalScore: "",
  artisticScore: "",
  laborScore: "",
  lowLevelNum: "",
  middleLevelNum: "",
  highLevelNum: ""
});

const init = (classId: string, semesterId: string) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.response = "";
  dataForm.moralScore = "";
  dataForm.intellectualScore = "";
  dataForm.physicalScore = "";
  dataForm.artisticScore = "";
  dataForm.laborScore = "";
  dataForm.lowLevelNum = "";
  dataForm.middleLevelNum = "";
  dataForm.highLevelNum = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }
  getInfo(classId, semesterId);
};

// 在 Dialog 的内容动态渲染之后创建图表。在Dialog的opened事件中初始化图表。此事件在 Dialog完全打开之后触发，此时所有的DOM元素都已渲染完毕。
const initChart = () => {
  nextTick(() => {
    createBarChart(); //条形图
    createPieChart(); //饼状图
  });
};

// 在创建新图表之前，检查这个变量是否已经有一个图表实例。如果有，先销毁它。
/*
const destroyChart = () => {
  if (myBarChart) {
    myBarChart.destroy();
    myBarChart = null;
  }
};
*/

// 创建条形图的函数
const createBarChart = () => {
  // 在创建新图表之前，检查这个变量是否已经有一个图表实例。如果有，先销毁它。
  if (myBarChart) {
    myBarChart.destroy();
    myBarChart = null;
  }

  if (barChartContainer.value) {
    //是否已经指向了一个有效的DOM元素
    const ctx = barChartContainer.value.getContext("2d");
    console.log(ctx);
    if (!ctx) return;

    const classData: Record<string, number> = {
      德育: Number(dataForm.moralScore),
      智育: Number(dataForm.intellectualScore),
      体育: Number(dataForm.physicalScore),
      美育: Number(dataForm.artisticScore),
      劳育: Number(dataForm.laborScore)
    };

    const labels = Object.keys(classData);
    const data = Object.values(classData);

    myBarChart = new Chart(ctx, {
      type: "bar",
      data: {
        labels: labels,
        datasets: [
          {
            label: "班级五育平均分",
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
        maintainAspectRatio: false, // 不保持宽高比
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
// 创建饼状图
const createPieChart = () => {
  // 销毁旧的图表实例
  if (myPieChart) {
    myPieChart.destroy();
    myPieChart = null;
  }
  if (pieChartContainer.value) {
    const ctx = pieChartContainer.value.getContext("2d");
    if (!ctx) return;

    const total = parseInt(dataForm.studentNum);
    const lowLevel = (parseInt(dataForm.lowLevelNum) / total) * 100;
    const middleLevel = (parseInt(dataForm.middleLevelNum) / total) * 100;
    const highLevel = (parseInt(dataForm.highLevelNum) / total) * 100;

    myPieChart = new Chart(ctx, {
      type: "pie",

      data: {
        labels: ["差", "中", "优"],
        datasets: [
          {
            label: "学生五育综合等级分布",
            data: [lowLevel, middleLevel, highLevel],
            backgroundColor: ["rgba(255, 99, 132, 0.5)", "rgba(54, 162, 235, 0.5)", "rgba(255, 206, 86, 0.5)"],
            borderColor: ["rgba(255, 99, 132, 1)", "rgba(54, 162, 235, 1)", "rgba(255, 206, 86, 1)"],
            borderWidth: 1
          }
        ]
      },
      options: {
        maintainAspectRatio: false, // 不保持宽高比
        //aspectRatio: 2, // 设置宽高比，2 表示宽度是高度的两倍
      },
    });
  }
};

// 根据五育class id 和 semesterId 获取 班级诊断报告信息
const getInfo = (classId: string, semesterId: string) => {
  let url = `/analysis/wuyuscore/class?classId=${classId}`;
  // 如果选择了学期
  if (semesterId) {
    url += `&semesterId=${semesterId}`;
  }
  // 首次查询报告，如果无记录，会生成
  baseService.get(url).then((res) => {
    Object.assign(dataForm, res.data);
  });
};

const reCreateReportHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    let url = `/analysis/wuyuscore/class/re?classId=${dataForm.classId}`;
    reloading.value = true;
    // 如果选择了学期
    if (dataForm.semesterId) {
      url += `&semesterId=${dataForm.semesterId}`;
    }
    reloading.value = true;
    baseService.put(url).then(() => {
      // 重新生成后，调用查询函数
      getInfo(dataForm.classId, dataForm.semesterId);

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
      htmlToPdf.getPdf(dataForm.gradeName + "-" + dataForm.className + "-" + "班级诊断报告", loading.value, () => {
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
