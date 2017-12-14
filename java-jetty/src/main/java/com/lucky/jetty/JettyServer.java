package com.lucky.jetty;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.HandlerWrapper;
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
        connector_one.setPort(8080);
        HandlerList handlerList=new HandlerList();
        handlerList.setHandlers(new Handler[]{new MyHandler2(),new MyHandler()});
//        HandlerWrapper handlerWrapper=new HandlerWrapper();
//        handlerWrapper.setHandler(new MyHandler());
//        handlerWrapper.setHandler(new MyHandler2());
        server.setConnectors(new Connector[]{connector_one});
//        server.setHandler(new MyHandler2());
        server.setHandler(handlerList);
        server.start();
        server.join();


    }


    static class MyHandler extends AbstractHandler {


        @Override
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

            System.out.println("我是老1");
            System.out.println(request.getRequestURL().toString());

            // 通知Jettyrequest使用此处理器处理请求
//            request.setHandled(true);
//            httpServletResponse.setCharacterEncoding("utf-8");
//
//            httpServletResponse.setContentType("text/html;charset=utf-8");
//
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//
//            httpServletResponse.getWriter().write("<b>yangtengfei</b>" + (new Date()).toLocaleString());
//
//            httpServletResponse.flushBuffer();
        }
    }

    static class MyHandler2 extends AbstractHandler {


        @Override
        public void handle(String s, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {

            System.out.println("我是老2");

            System.out.println(request.getRequestURL().toString());


            httpServletResponse.setCharacterEncoding("utf-8");

            httpServletResponse.setContentType("text/html;charset=utf-8");

            httpServletResponse.setStatus(HttpServletResponse.SC_OK);

            httpServletResponse.getWriter().write("<b>yangtengfei</b>" + (new Date()).toLocaleString());

            httpServletResponse.flushBuffer();
            request.setHandled(true);

        }
    }
}
