import html2canvas from "html2canvas";
import jsPDF from "jspdf";

const htmlToPdf = {
  getPdf(title: string, loading: boolean, onCompleted: () => void): void {
    // loading = true;
    console.log(loading);
    html2canvas(document.querySelector("#pdfDom") as HTMLElement, {
      useCORS: true,
      allowTaint: true,
      logging: false,
      scale: 2 // 按比例增加分辨率
    }).then((canvas) => {
      const pdf = new jsPDF("p", "mm", "a4"); // A4纸，纵向
      const ctx = canvas.getContext("2d") as CanvasRenderingContext2D;
      const a4w = 190;
      const a4h = 272; // A4大小，210mm x 297mm，四边各保留10mm的边距，显示区域190x277
      const imgHeight = Math.floor((a4h * canvas.width) / a4w); // 按A4显示比例换算一页图像的像素高度
      let renderedHeight = 0;

      while (renderedHeight < canvas.height) {
        const page = document.createElement("canvas");
        page.width = canvas.width;
        page.height = Math.min(imgHeight, canvas.height - renderedHeight); // 可能内容不足一页

        // 用getImageData剪裁指定区域，并画到前面创建的canvas对象中
        const pageCtx = page.getContext("2d") as CanvasRenderingContext2D;
        pageCtx.putImageData(
          ctx.getImageData(
            0,
            renderedHeight,
            canvas.width,
            Math.min(imgHeight, canvas.height - renderedHeight)
          ),
          0,
          0
        );
        pdf.addImage(
          page.toDataURL("image/png", 1.0),
          "PNG",
          10,
          10,
          a4w,
          Math.min(a4h, (a4w * page.height) / page.width),
          "",
          "FAST"
        ); // 添加图像到页面，保留10mm边距

        renderedHeight += imgHeight;
        if (renderedHeight < canvas.height) {
          pdf.addPage(); // 如果后面还有内容，添加一个空页
        }
      }
      // 保存文件
      pdf.save(`${title}.pdf`);
      onCompleted(); // 调用回调函数
    });
  }
};

export default htmlToPdf;
