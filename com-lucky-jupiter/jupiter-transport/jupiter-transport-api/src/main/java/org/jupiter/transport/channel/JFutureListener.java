package org.jupiter.transport.channel;

import java.util.EventListener;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:18 2018/1/18
 */
public interface JFutureListener<C> extends EventListener {

    void operationSuccess(C c) throws Exception;

    void operationFailure(C c, Throwable cause) throws Exception;
}
