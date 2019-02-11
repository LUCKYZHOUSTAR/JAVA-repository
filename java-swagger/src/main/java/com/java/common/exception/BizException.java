package com.java.common.exception;

/**
 * 自定义的根异常
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -2954708683056459053L;

    /**
     * 统一错误代码
     */
    protected final int code;

    public BizException(int code) {
        super("Apollo Business Exception");
        this.code = code;
    }

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(int code, Throwable t) {
        super(t);
        this.code = code;
    }

    public BizException(int code, String msg, Throwable t) {
        super(msg, t);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return super.toString() + "\nError Code: " + this.code;
    }

}
