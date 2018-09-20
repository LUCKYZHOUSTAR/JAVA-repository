package com.log.agent;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午3:56 2018/8/9
 */
public class LogReader implements Runnable {

  private File logFile = null;
  private long lastTimeFileSize = 0; // 上次文件大小
  private static SimpleDateFormat dateFormat = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");

  public LogReader(File logFile) {
    this.logFile = logFile;
    lastTimeFileSize = logFile.length();
  }

  /**
   * 实时输出日志信息
   */
  public void run() {
    while (true) {
      try {
        long len = logFile.length();
        if (len < lastTimeFileSize) {
          System.out.println("Log file was reset. Restarting logging from start of file.");
          lastTimeFileSize = len;
        } else if (len > lastTimeFileSize) {
          RandomAccessFile randomFile = new RandomAccessFile(logFile, "r");
          randomFile.seek(lastTimeFileSize);
          String tmp = null;
          while ((tmp = randomFile.readLine()) != null) {
            System.out.println(dateFormat.format(new Date()) + "\t"
                + tmp);
          }
          lastTimeFileSize = randomFile.length();
          randomFile.close();
        }
      } catch (IOException e) {

      }

      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}

