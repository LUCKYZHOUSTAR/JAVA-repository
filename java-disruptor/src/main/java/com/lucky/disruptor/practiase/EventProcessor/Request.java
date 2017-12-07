package com.lucky.disruptor.practiase.EventProcessor;

/**
 * @Author:chaoqiang.zhou
 * @Description:生产者的事件消息
 * @Date:Create in 14:35 2017/12/7
 */
public class Request {

    private String body;
    private long id;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Request{" +
                "body='" + body + '\'' +
                ", id=" + id +
                '}';
    }
}
