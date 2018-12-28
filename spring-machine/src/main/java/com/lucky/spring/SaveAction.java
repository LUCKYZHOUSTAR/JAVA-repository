package com.lucky.spring;

import org.springframework.core.annotation.Order;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/28 13:59
 * @Description:
 */
@Component
public class SaveAction implements Action<States, Events> {
    @Override
    public void execute(StateContext<States, Events> stateContext) {
//        Order order  = (Order) context.getMessageHeader(OrderStatusContextKey.ORDER_ENTITY_KEY);
//        orderInfoService.save(order);
    }
}
