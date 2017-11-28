package basic.netty.server;

import basic.netty.InvokerResult;
import basic.netty.data.Request;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:52 2017/11/28
 */
public class DefaultConsumereProcessor implements ConsumerProcessor {
    @Override
    public void handleRequest(Request function) throws Exception {
        //同步的情况下没问题，但是异步的情况下就会有问题了
        InvokerResult future = InvokerResult.future.remove(function.getId());
        if (future == null) {
            return;
        }
        future.set(function);
    }

    @Override
    public void handleException(Request function, Throwable cause) {

        System.out.println("出现异常了，异常信息为" + function + "异常信息为" + cause);
    }
}
