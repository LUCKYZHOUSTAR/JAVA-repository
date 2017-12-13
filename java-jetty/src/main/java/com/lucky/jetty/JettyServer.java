package com.lucky.jetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.thread.ExecutorThreadPool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:39 2017/12/13
 */
public class JettyServer {


    public static void main(String[] args) throws Throwable {
        Server server = new Server(new ExecutorThreadPool());  // 非阻塞
        ServerConnector connector_one = new ServerConnector(server);
        ServerConnector connector_two = new ServerConnector(server);
        connector_one.setPort(8080);
        connector_two.setPort(8001);
        server.setHandler(new MyHandler());
        server.start();


    }


    static class MyHandler extends AbstractHandler {


        @Override
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

            System.out.println(request.getRequestURL().toString());


            httpServletResponse.setCharacterEncoding("utf-8");

            httpServletResponse.setContentType("text/html;charset=utf-8");

            httpServletResponse.setStatus(HttpServletResponse.SC_OK);

            httpServletResponse.getWriter().write("<b>yangtengfei</b>" + (new Date()).toLocaleString());

            httpServletResponse.flushBuffer();
        }
    }
}
