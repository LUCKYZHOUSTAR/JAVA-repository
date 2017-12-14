package com.lucky.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:11 2017/12/14
 */
public class ThreadDaemon {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            try {
                System.out.println("我开始了");
                Server server = new Server(new ExecutorThreadPool());  // 非阻塞
                ServerConnector connector_one = new ServerConnector(server);
                connector_one.setPort(8090);
                ServerConnector connector_two = new ServerConnector(server);
                connector_one.setPort(8091);

                server.setConnectors(new Connector[]{connector_one, connector_two});
                ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
                context.setContextPath("/MyServer");   //这里是请求的上下文，比如http://localhost:8090/MyServer
                server.setHandler(context);
                context.addServlet(new ServletHolder(new HelloWorld()), "/helloWorld");   //添加servlet，第一是具体的servlet，后面是请求的别名，在http请求中的路径
                context.addServlet(new ServletHolder(new HelloWorld("chan")), "/HellworldWithParams");
                server.start();
                server.join();
            } catch (Exception e) {
                System.out.println("出现异常了");

            } finally {
                System.out.println("我关闭了");
            }
        });

        t.setDaemon(true);
        t.start();

        Thread.sleep(8000L);
        System.out.println("程序结束了");
        t.interrupt();
    }
}
