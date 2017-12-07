package com.lucky.boot;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author:chaoqiang.zhou
 * @Description:启动的时候加载数据信息
 * @Date:Create in 10:37 2017/12/7
 */
@Component
public class ItemDataStartupLoader extends DataStartupLoader {
    @Override
    protected void doLoad() {
        System.out.println("开始加载大量的数据信息");
    }

    @Override
    public int getPhase() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
