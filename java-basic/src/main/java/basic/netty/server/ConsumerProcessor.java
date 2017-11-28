package basic.netty.server;

import basic.netty.data.Request;
import com.sun.org.apache.regexp.internal.RE;

import java.util.function.Function;

/**
 * @Author:chaoqiang.zhou
 * @Description:消费端处理信息的接口
 * @Date:Create in 14:10 2017/11/28
 */
public interface ConsumerProcessor {
    void handleRequest(Request function) throws Exception;

    void handleException(Request function, Throwable cause);

}
