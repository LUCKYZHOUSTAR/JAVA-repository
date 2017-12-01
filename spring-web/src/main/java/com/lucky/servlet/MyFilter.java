package com.lucky.servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:23 2017/12/1
 */
public class MyFilter implements Filter {

    /**
     * web 应用程序启动时，web 服务器将创建Filter 的实例对象，并调用其init方法，读取web.xml配置，完成对象的初始化功能，从而为后续的用户请求作好拦截的准备工作（filter对象只会创建一次，init方法也只会执行一次）。开发人员通过init方法的参数，可获得代表当前filter配置信息的FilterConfig对象。
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init....");

    }


    /**
     * web 应用程序启动时，web 服务器将创建Filter 的实例对象，并调用其init方法，读取web.xml配置，
     * 完成对象的初始化功能，从而为后续的用户请求作好拦截的准备工作（filter对象只会创建一次，init方法也只会执行一次）。
     * 开发人员通过init方法的参数，可获得代表当前filter配置信息的FilterConfig对象。
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter...");
        chain.doFilter(request, response);
    }


    /**
     * Servlet容器在销毁过滤器实例前调用该方法，在该方法中释放Servlet过滤器占用的资源。
     */
    @Override
    public void destroy() {

        System.out.println("filter destroy....");
    }
}
