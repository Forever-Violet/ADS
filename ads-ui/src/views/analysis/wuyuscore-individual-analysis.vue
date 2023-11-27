<template>
  <el-dialog v-model="visible" title="" :close-on-click-modal="false" :close-on-press-escape="false">
    <el-form :model="dataForm" v-if="dataForm.id != ''" :rules="rules" ref="dataFormRef" @keyup.enter="dataFormSubmitHandle()" label-width="0px">
      <div id="pdfDom">
        <h3 style="text-align: center">个人诊断报告</h3>
        <div v-if="loading" style="font-size: 16px; text-indent: 2em;">
          {{ dataForm.response }}
        </div>
        <el-input
          type="textarea"
          v-if="!loading"
          v-model="dataForm.response"
          placeholder="请输入内容"
          :rows="4"
          style="font-size: 16px; text-indent: 2em;"
          maxlength="1000"
          class="borderless-textarea">
        </el-input>
      </div>
      <div v-if="loading" style="text-align: center; font-size: 18px; color: deepskyblue">生成PDF中...</div>
    </el-form>
    <div v-if="dataForm.id == ''" style="text-align: center; font-size: 18px; color: deepskyblue">报告生成中...</div>
    <template v-slot:footer>
      <el-button type="primary" @click="dataFormSubmitHandle()">保存</el-button>
      <el-button type="warning" @click="reCreateReportHandle()">重新生成</el-button>
      <el-button type="info" @click="exportPDFHandle()">导出为PDF</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import baseService from "@/service/baseService";
import { ElMessage } from "element-plus";
import htmlToPdf from "@/utils/htmlToPDF";

const visible = ref(false);
const dataFormRef = ref();
const loading = ref(false); //缓冲动画flag

const dataForm = reactive({
  id: "",
  classId: "",
  scoreId: "",
  studentNo: "",
  studentName: "",
  response: ""
});

const rules = ref({

});

const init = (id: number) => {
  visible.value = true;
  dataForm.id = "";
  dataForm.response = "";

  // 重置表单数据
  if (dataFormRef.value) {
    dataFormRef.value.resetFields();
  }

  // 这个id是五育score id
  getInfo(id);
};

// 根据五育score id 获取 诊断报告信息
const getInfo = (id: number) => {
  // 首次查询报告，如果无记录，会生成
  baseService.get(`/analysis/wuyuscore/individual/${id}`).then((res) => {
    Object.assign(dataForm, res.data);
  });
};


const reCreateReportHandle = () => {
  dataFormRef.value.validate((valid: boolean) => {
    if (!valid) {
      return false;
    }
    dataForm.id = ""; //先置空 报告的id，使前端显示正在生成报告
    baseService.put(`/analysis/wuyuscore/individual/re/${dataForm.scoreId}`).then(() => {
      // 重新生成后，调用查询函数
      getInfo(Number(dataForm.scoreId));
    });
  });
}

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

<style scoped>

</style>
