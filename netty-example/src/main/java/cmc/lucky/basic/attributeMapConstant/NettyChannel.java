package cmc.lucky.basic.attributeMapConstant;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:00 2017/11/27
 */

@Getter
@Setter
public class NettyChannel {
    private String name;
    private Date createDate;

    public NettyChannel(String name, Date createDate) {
        this.name = name;
        this.createDate = createDate;
    }
}
