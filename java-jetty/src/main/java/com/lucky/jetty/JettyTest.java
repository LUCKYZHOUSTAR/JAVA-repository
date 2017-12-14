package com.lucky.jetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:17 2017/12/14
 */
public class JettyTest extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // 通知Jettyrequest使用此处理器处理请求
        baseRequest.setHandled(true);
        // 声明response的编码和文件类型
        response.setContentType("text/html; charset=utf-8");

        // 声明返回状态码
        response.setStatus(HttpServletResponse.SC_OK);

        // 请求的返回值
        response.getWriter().println("<h1>Hello World</h1>");


    }

    public static void main(String[] args) throws Exception {
        //创建一个应用服务监听8080端口
        Server server = new Server(8080);
        server.setHandler(new JettyTest());

        //启动应用服务并等待请求
        server.start();
        server.join();
    }
}
