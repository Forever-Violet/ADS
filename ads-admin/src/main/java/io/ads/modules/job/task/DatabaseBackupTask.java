package io.ads.modules.job.task;

import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 数据库定时备份任务
 *
 * databaseBackupTask为spring bean的名称
 *
 * @author Roxy
 */
@Component("databaseBackupTask")
public class DatabaseBackupTask implements ITask{

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;


    public static void backup(String host, String port, String database, String user, String password, String backupPath, String backupFile) throws IOException, InterruptedException {
        // 检测操作系统类型
        String os = System.getProperty("os.name").toLowerCase();
        // 根据操作系统设置备份文件的路径
        String folderPath = backupPath + (os.contains("win") ? "\\" : "/");
        File folder = new File(folderPath);
        if (!folder.exists()) { //不存在则创建文件夹
            folder.mkdirs();
        }
        // 设置备份文件名为当前日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = backupFile + "_" + dateFormat.format(new Date()) + ".sql";
        List<String> command = new ArrayList<>();
        command.add("mysqldump");
        command.add("-h" + host);
        command.add("-P" + port);
        command.add("-u" + user);
        command.add("-p" + password);
        command.add("--add-drop-database");
        command.add("--databases");
        command.add(database);
        command.add("-r");
        command.add(folderPath + fileName);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        //设置超时一分钟
        process.waitFor(60000, TimeUnit.MILLISECONDS);
        if (process.waitFor() == 0) {
            System.out.println("数据库备份成功，备份路径为：" + folderPath + fileName);
        } else {
            System.out.println("数据库备份失败");
        }
    }
    @Override
    public void run(String params) throws Exception {
        // 将字符串转换为JSONObject
        JSONObject jsonObject = new JSONObject(params);
        // 获取属性值
        String host = jsonObject.get("host", String.class);
        String port = jsonObject.get("port", String.class);
        String database = jsonObject.get("database", String.class);
        String os = System.getProperty("os.name").toLowerCase();
        String backupPath;
        if (os.contains("win")) {
            // Windows系统，默认备份路径
            backupPath = "D:\\Backup";
        } else if (os.contains("nix") || os.contains("nux") || os.indexOf("aix") > 0) {
            // Unix/Linux系统，默认备份路径
            backupPath = "/home/Backup";
        } else {
            // 其他系统，默认备份路径
            throw new Exception("暂不支持该操作系统进行数据库备份或还原！");
        }
        // 备份
        backup(host, port, database, username, password, backupPath, "backup");
    }
}
