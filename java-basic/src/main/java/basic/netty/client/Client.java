package basic.netty.client;

import basic.netty.data.Request;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:26 2017/11/28
 */
public interface Client {

    Object invoke(Request request) throws Throwable;

    Object AsyncInvoke(Request request);

}
