package com.java.common.exception.handler;

import javax.ws.rs.ext.ExceptionMapper;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 在主类上加上EnableJersey注解才会生效
 * 基类的异常处理
 */
public abstract class BaseExceptionHandler<E extends Throwable> implements ExceptionMapper<E> {
    /**
     * 获取全部的异常堆栈，转换成字符串
     *
     * @param ex 异常
     * @return 字符创
     */
    protected String getErrorStackTrace(Throwable ex) {
        return getFullStackTrace(ex);
    }

    public static String getFullStackTrace(Throwable ex) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        ex.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
