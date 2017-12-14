package com.lucky.task.core.net.codec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:23 2017/12/14
 */

@Getter
@Setter
@AllArgsConstructor
public class RpcRequest implements Serializable {
    private int type;
    private Object body;
}
