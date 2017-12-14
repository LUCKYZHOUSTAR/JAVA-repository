package com.lucky.task.core.config;

import com.lucky.task.core.util.IpUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author:chaoqiang.zhou
 * @Description:server的配置类信息
 * @Date:Create in 10:37 2017/12/14
 */
@Getter
@Setter
public class ServerOptions implements Serializable {

    private String name;
    private String ip;
    private int port;
    private String description;
    private String registerCenterAdd;

    private Status status = Status.Start;

    public ServerOptions() {
        //默认获取本机的ip信息
        this.ip = IpUtil.getIp();
    }

    public enum Status {
        Start(0), Destory(1);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

}
