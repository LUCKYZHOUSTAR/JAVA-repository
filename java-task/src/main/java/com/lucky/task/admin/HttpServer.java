package com.lucky.task.admin;

import com.lucky.task.core.net.server.ServerHandler;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:37 2017/12/14
 */
public class HttpServer {

    public static void main(String[] args) throws Exception {
        Server server = new Server(new ExecutorThreadPool());  // 非阻塞
        ServerConnector connector_one = new ServerConnector(server);
        connector_one.setPort(8090);
        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{new ServerHandler()});
        server.setConnectors(new Connector[]{connector_one});
        server.setHandler(handlerList);
        server.start();
        server.join();
    }
}
