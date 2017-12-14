package com.lucky.task.core.net.server;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

/**
 * @Author:chaoqiang.zhou
 * @Description:rpc的server端，这样做不好，最好能与dubbo做兼容最好，这样开启两个端口
 * @Date:Create in 15:43 2017/12/13
 */
@Slf4j
public class JettyServer {

    private Server server;
    private Thread thread;

    @Setter
    private HandlerCollection handlerCollection;

    public void start(final int port) throws Exception {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                server = new Server(new ExecutorThreadPool());  // 非阻塞
                ServerConnector connector = new ServerConnector(server);
                connector.setPort(port);
                server.setConnectors(new Connector[]{connector});
                server.setHandler(handlerCollection);
                try {
                    server.start();
                    log.info("jetty server start success at port:{}.", port);
                    server.join();    // block until thread stopped
                    log.info("server join success, netcon={}, port={}", JettyServer.class.getName(), port);
                } catch (Exception e) {
                    log.error("启动server，出现异常信息", e);
                } finally {
                    log.error("守护线程结束，开始关闭jettyserver");
                    destroy();
                    log.error("守护线程结束，成功关闭jettyserver");
                }
            }
        });
        thread.setDaemon(true);    // 采用守护线程开始jetty，这样用户线程结束的时候，自动结束，在catcher里面自动关闭server，保证server能够正常结束
        thread.start();
    }

    public void destroy() {
        if (server != null) {
            try {
                server.stop();
                server.destroy();
            } catch (Exception e) {
                log.error("server关闭出现异常信息", e);
            }
        }
        if (thread.isAlive()) {
            //再次中断该线程,在自动的关闭jetty的server线程信息
            thread.interrupt();
        }
        log.info("server destroy success, netcon={}", JettyServer.class.getName());
    }

}
