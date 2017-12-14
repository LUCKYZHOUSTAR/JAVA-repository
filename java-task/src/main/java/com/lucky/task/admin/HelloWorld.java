package com.lucky.task.admin;

import com.alibaba.fastjson.JSON;
import com.lucky.task.core.net.codec.Response;
import com.lucky.task.core.config.ServerOptions;
import com.lucky.task.core.serialize.SerializerFactory;
import com.lucky.task.core.util.HttpClientUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:50 2017/12/13
 */
public class HelloWorld extends HttpServlet {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2271797150647771294L;

    private String msg = "hello world~~~~~~";

    public HelloWorld() {
    }

    public HelloWorld(String msg) {
        this.msg = msg;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Response rpcResponse = doInvoke(req);
        byte[] responseBytes = SerializerFactory.getSerializer().writeObject(rpcResponse);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        OutputStream out = resp.getOutputStream();
        out.write(responseBytes);
        out.flush();
    }

    private Response doInvoke(HttpServletRequest request) {
        try {
            // deserialize request
            byte[] requestBytes = HttpClientUtil.readBytes(request);
            if (requestBytes == null || requestBytes.length == 0) {
                Response rpcResponse = new Response(false, "RpcRequest byte[] is null");
                return rpcResponse;
            }
            ServerOptions rpcRequest =  SerializerFactory.getSerializer().readObject(requestBytes, ServerOptions.class);
            //执行器工厂获取到对应的执行器task，执行即可，无需再次的反射

            System.out.println("获取到了执行器的相关的信息操作"+ JSON.toJSONString(rpcRequest));
            return new Response(true,"");

        } catch (Exception e) {
            Response rpcResponse = new Response(false, "Server-error:" + e.getMessage());
            return rpcResponse;
        }
    }
}