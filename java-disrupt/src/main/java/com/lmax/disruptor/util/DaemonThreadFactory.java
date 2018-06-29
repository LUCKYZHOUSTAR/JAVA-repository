package com.lmax.disruptor.util;

import java.util.concurrent.ThreadFactory;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午2:58 2018/5/8
 */
public enum DaemonThreadFactory implements ThreadFactory {

  INSTANCE;
  @Override
  public Thread newThread(final Runnable r) {
    Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  }
}
