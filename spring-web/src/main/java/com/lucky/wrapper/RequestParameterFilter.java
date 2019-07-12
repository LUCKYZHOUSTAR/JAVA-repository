package com.lucky.wrapper;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/4/17 12:09
 * @Description:
 */

@Component
public class RequestParameterFilter extends OncePerRequestFilter {
    /**
     * 过滤路径
     */
    static final String AUTH_PATH = "/user";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*如果请求路径是为app,进行过滤对参数parameter内容解密，放入request.parameter中*/
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());

        System.out.println(request.getParameterMap());
        if (request.getRequestURI().indexOf(AUTH_PATH) != -1) {
            /*1.获取加密串,进行解密*/

            /*2.解密出加密串，我和前台约定的是JSON,获取到JSON我将其转换为map，这里我直接用手动封装map代替*/
            Map paramter = new HashMap(16);
            paramter.put("username", "admin");
            paramter.put("password", "password");
            ParameterRequestWrapper wrapper = new ParameterRequestWrapper(request, paramter);
            filterChain.doFilter(wrapper, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}