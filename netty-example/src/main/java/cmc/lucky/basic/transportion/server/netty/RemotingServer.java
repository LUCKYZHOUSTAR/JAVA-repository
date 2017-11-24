package cmc.lucky.basic.transportion.server.netty;

import java.util.concurrent.ExecutorService;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:16 2017/10/26
 */
public interface RemotingServer {

    public void registerProcessor(final NettyRequestProcessor processor, final ExecutorService executorService);

    public void start();


    public void shutDown();

    public int localListenPort();
}
