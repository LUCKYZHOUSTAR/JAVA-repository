package com.lucky.task.core.net.interceptor;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:53 2017/12/13
 */
public interface ServerStartInterceptor {

    public void BeforedoSomeThing() throws Exception;

    public void AfterdoSomeThing() throws Exception;
}
