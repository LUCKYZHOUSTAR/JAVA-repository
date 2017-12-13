package com.lucky.task.client.data;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:57 2017/12/13
 */

@Getter
@Setter
public class ClientInfo {

    private String name;
    private String ip;

    private Status status;


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
