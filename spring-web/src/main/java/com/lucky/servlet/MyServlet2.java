package com.lucky.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:56 2017/12/1
 */
public class MyServlet2 implements Servlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("servlet init...");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        doRequest(req);
        System.out.println(req);
        System.out.println("请求到我了");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("destroy...");
    }


    private void doRequest(ServletRequest request){
        System.out.println(request.getParameter("name"));
        System.out.println(request.getAttribute("name"));
        System.out.println(request.getParameterNames());
        System.out.println(request.getParameterValues("name"));
    }
}
