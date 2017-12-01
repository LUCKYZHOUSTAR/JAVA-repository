package com.lucky.jedis;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 18:14 2017/12/1
 */
@Getter
@Setter
public class RedisConfig {

    public static  int timeOut=2000;
    public static int retryNum=3;
}
