package com.lucky.disruptor.practiase.EventProcessor;

import com.lmax.disruptor.RingBuffer;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description:生产者信息
 * @Date:Create in 14:39 2017/12/7
 */
public class Producer {


  private static final AtomicLong index = new AtomicLong();
  private final RingBuffer<Request> ringBuffer;

  public Producer(RingBuffer<Request> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }


  /**
   * 用最原始的方法来发布消息
   */
  public void publish() {

    //获取下一个槽的序号
    long sequence = ringBuffer.next();
    try {
      //开始赋值内容消息，这样每次都不要创建新的对象，减少了内存的开销
      Request request1 = ringBuffer.get(sequence);
      request1.setBody("啦啦");
      request1.setId(index.getAndIncrement());
    } finally {
      //发布消息
      //最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
      //Disruptor 还提供另外一种形式的调用来简化以上操作，并确保 publish 总是得到调用。
      ringBuffer.publish(sequence);
    }
  }
}
