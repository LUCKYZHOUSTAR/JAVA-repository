package com.lucky.disruptor.practiase.exceptionhandler;

import com.lmax.disruptor.ExceptionHandler;

/**
 * @Author:chaoqiang.zhou
 * @Description:异常的处理器信息
 * @Date:Create in 15:01 2017/12/7
 */
public class ExceptionHandlerTest implements ExceptionHandler<Request> {

    @Override
    public void handleEventException(Throwable throwable, long l, Request request) {

        System.out.println("1");
        System.out.println("执行到序列号" + 1 + "发生过错误了" + request);
    }


    /**
     * 启动的时候报错
     *
     * @param throwable
     */
    @Override
    public void handleOnStartException(Throwable throwable) {

        System.out.println(2);
        System.out.println(throwable.getMessage());
    }


    /**
     * 关闭的时候报错
     *
     * @param throwable
     */
    @Override
    public void handleOnShutdownException(Throwable throwable) {
        System.out.println(3);
        System.out.println(throwable.getMessage());

    }
}
