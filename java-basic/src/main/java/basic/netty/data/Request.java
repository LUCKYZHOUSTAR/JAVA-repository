package basic.netty.data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:54 2017/11/28
 */


public class Request {

    private static final AtomicLong idIndex=new AtomicLong();

    public Request(){
        this.id=idIndex.getAndIncrement();
    }
    private Long id;


    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = idIndex.getAndIncrement();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
