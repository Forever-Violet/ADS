package io.ads.modules.job.task;

import cn.hutool.json.JSONObject;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时清除数据库备份文件任务
 *
 * @author Roxy
 */
@Component("databaseBackupCleanUpTask")
public class DatabaseBackupCleanUpTask implements ITask{

    // daysToKeep保留的天数
    public static void backupFileCleanUp(int daysToKeep) throws Exception {
        String backupFolderPath;
        String os = System.getProperty("os.name").toLowerCase();

        // 根据操作系统设置备份文件夹路径
        if (os.contains("win")) {
            // Windows系统，默认备份路径
            backupFolderPath = "D:\\Backup";
        } else if (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0) {
            // Unix/Linux系统，默认备份路径
            backupFolderPath = "/home/Backup";
        } else {
            // 其他系统
            throw new Exception("暂不支持该操作系统！");
        }

        File backupFolder = new File(backupFolderPath);
        // 文件列表
        File[] backupFiles = backupFolder.listFiles();

        if (backupFiles != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

            // 获取14天之前的日期
            // 返回当前系统时间与格林威治时间（1970 年 1 月 1 日 00:00:00 UTC）之间的时间差，以毫秒为单位的值。它表示了自从格林威治时间开始到当前时间经过的毫秒数。
            long currentTimeMillis = System.currentTimeMillis();
            // daysToKeep 天的毫秒数
            long fourteenDaysInMillis = daysToKeep * 24 * 60 * 60 * 1000L;
            // daysToKeep 天前的时间戳
            long thresholdTime = currentTimeMillis - fourteenDaysInMillis;

            // 删除早于14天之前的备份文件
            for (File file : backupFiles) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.startsWith("backup_")) { // 确认文件名格式
                        String datePart = fileName.substring(7, 21); // 提取日期部分
                        try {
                            // 解析日期
                            Date backupDate = dateFormat.parse(datePart);
                            // 指定日期的时间戳
                            long backupTime = backupDate.getTime();
                            if (backupTime < thresholdTime) {
                                // 删除过期备份文件
                                boolean isDeleted = file.delete();
                                if (isDeleted) {
                                    System.out.println("成功删除数据库备份文件: " + fileName);
                                } else {
                                    System.out.println("数据库备份文件删除失败: " + fileName);
                                }
                            }
                        } catch (Exception e) {
                            throw new Exception("无法解析备份文件日期: "+ fileName, e);
                        }
                    }
                }
            }
        } else {
            throw new Exception("备份文件夹为空");
        }
    }


    @Override
    public void run(String params) throws Exception {
        // 将字符串转换为JSONObject
        JSONObject jsonObject = new JSONObject(params);
        // 获取属性值
        int daysToKeep = jsonObject.get("daysToKeep", int.class);
        backupFileCleanUp(daysToKeep);
    }
}
