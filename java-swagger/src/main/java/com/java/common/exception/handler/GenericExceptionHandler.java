package com.java.common.exception.handler;

import com.java.common.exception.BizException;
import com.java.common.exception.ErrorMsg;
import com.java.common.jersey.EnableJersey;
import com.java.common.util.ResponseUtil;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 在主类上加上EnableJersey注解才会生效
 * Throwable的异常处理
 */
@Component
@ConditionalOnBean(annotation = EnableJersey.class)
public class GenericExceptionHandler extends BaseExceptionHandler<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @Override
    public Response toResponse(Throwable ex) {
        logger.error(ex.getMessage(), ex);
        ErrorMsg errMsg = new ErrorMsg(400, ex.getMessage());
        // 被截获异常无有效信息时，使用通用错误的描述信息
        if (null == errMsg.getMessage()) {
            errMsg.setMessage("");
        }
        // 调试开关打开，返回详细的错误堆栈信息
        if (logger.isDebugEnabled()) {
            errMsg.setDetail(getErrorStackTrace(ex));
        }

        int status = 400;
        if (ex instanceof WebApplicationException) {
            status = ((WebApplicationException) ex).getResponse().getStatus();
        } else if (ex instanceof UndeclaredThrowableException) {
            try {
                BizException jyEx = BizException.class.cast(((UndeclaredThrowableException) ex).getUndeclaredThrowable());
                errMsg.setCode(jyEx.getCode());
                errMsg.setMessage(jyEx.getMessage());
            } catch (Exception e) {
                // 未定义异常不是JyBizException
                logger.debug("非JyBizException的未定义异常", e);
            }
        } else if (ex instanceof FeignException) {
            errMsg = ErrorMsg.parse(ex);
            status = FeignException.class.cast(ex).status();
        } else {
            status = 400;
            errMsg = ErrorMsg.parse(ex);
        }
        return ResponseUtil.getResponse(status, errMsg);
    }
}
