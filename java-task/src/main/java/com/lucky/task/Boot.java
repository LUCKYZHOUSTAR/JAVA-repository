package com.lucky.task;

import com.lucky.task.client.TaskApplication;
import com.lucky.task.core.config.ServerOptions;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:chaoqiang.zhou
 * @Description:springboot启动一个ioc的容器，后台利用jettyserver来接收消息
 * @Date:Create in 12:25 2017/12/14
 */
@SpringBootApplication
public class Boot {
    public static void main(String[] args) {
        ServerOptions options = new ServerOptions();
        // app.conf app_name配置一致
        options.setName("cmc.card.trade.task");
        options.setPort(8080);
        options.setRegisterCenterAdd("http://192.168.9.216:8090/");
        options.setDescription("卡使用服务的定时Task服务");
        TaskApplication app = new TaskApplication(Boot.class,
                args, options);
        app.run();
    }
}
