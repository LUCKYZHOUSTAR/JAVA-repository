package basic.netty.client;

import basic.netty.InvokerResult;
import basic.netty.data.Request;
import basic.netty.server.Server;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:28 2017/11/28
 */
public class DefaultClient implements Client {

    private Server server;


    public DefaultClient(Server server) {
        this.server = server;
    }

    @Override
    public Object invoke(Request request) throws Throwable {
        InvokerResult result = new InvokerResult();
        InvokerResult future = InvokerResult.future.putIfAbsent(request.getId(), result);
        server.execute(request);
        if (future != null) {
            result = future;
        }
        return result.getResult();
    }

    @Override
    public Object AsyncInvoke(Request request) {
        InvokerResult result = new InvokerResult();
        InvokerResult.future.putIfAbsent(request.getId(), result);
        System.out.println(InvokerResult.future.hashCode());
        server.execute(request);
        return null;
    }
}
