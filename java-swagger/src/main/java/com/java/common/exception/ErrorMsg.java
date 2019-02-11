package com.java.common.exception;

import com.java.common.exception.handler.BaseExceptionHandler;
import com.java.common.util.GsonUtil;
import feign.FeignException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * RESTful服务返回的错误信息
 */
public class ErrorMsg implements Serializable {
    /**
     * slf4j日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ErrorMsg.class);
    private static final long serialVersionUID = 2640926329092743174L;
    /**
     * 异常的index，后面的数据就是异常的详情
     */
    private static String errorIndex = "content:";
    /**
     * * 统一错误码
     **/
    private int code;
    /**
     * * 错误信息摘要
     **/
    private String message;
    /**
     * * 错误信息详情（主要用于调试）
     **/
    private String detail = "";

    public ErrorMsg() {
    }

    public ErrorMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorMsg(int code, String message, String detail) {
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public static ErrorMsg parse(Throwable e) {
        String err = e.getMessage();
        int status = -1;
        try {
            if (e instanceof FeignException) {
                err = e.getMessage();
                status = ((FeignException) e).status();
            } else if (e.getCause() != null && e.getCause().getClass() == FeignException.class) {
                err = e.getCause().getMessage();
                status = ((FeignException) e.getCause()).status();
            }
            if (StringUtils.isNotEmpty(err)) {
                if (!err.contains(errorIndex)) {
                    err = BaseExceptionHandler.getFullStackTrace(e);
                    if (!err.contains(errorIndex)) {
                        logger.error("其他错误", e);
                        return new ErrorMsg(400, e.getMessage(), BaseExceptionHandler.getFullStackTrace(e));
                    } else {
                        err = err.split(errorIndex)[1];
                        if (err.contains("{") && err.contains("}")) {
                            err = err.substring(err.indexOf("{"), err.indexOf("}") + 1);
                            return GsonUtil.json2Bean(err, ErrorMsg.class);
                        } else {
                            logger.error("其他错误", e);
                            return new ErrorMsg(400, e.getMessage(), BaseExceptionHandler.getFullStackTrace(e));
                        }
                    }
                } else {
                    return GsonUtil.json2Bean(err.split(errorIndex)[1], ErrorMsg.class);
                }
            } else {
                return new ErrorMsg(400, e.getClass().getName(), BaseExceptionHandler.getFullStackTrace(e));
            }
        } catch (Exception e1) {
            logger.error("从异常信息中解析ErrorMsg失败", e1);
            ErrorMsg msg = new ErrorMsg(400, err);
            if (StringUtils.isNotEmpty(err)) {
                try {
                    if (status > 0) {
                        msg.setCode(status);
                    } else {
                        msg.setCode(Integer.parseInt(err.split(" ")[1]));
                    }
                    msg.setMessage(err.split(errorIndex)[1].trim());
                    msg.setDetail(err);
                } catch (Exception ee) {
                    return msg;
                }
            }
            return msg;
        }
    }
}

