package com.lucky.spring;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/4/12 11:48
 * @Description:
 */
@Log
@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private StateMachineFactory<OrderStates, OrderEvents> factory;


    @Autowired
    private OrderService orderService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

//        StateMachine<OrderStates, OrderEvents> machine = this.factory.getStateMachine("234234");
        StateMachine<OrderStates, OrderEvents> machine = orderService.build("234234");

        machine.getExtendedState().getVariables().put("a", "b");
//        machine.start();

        System.out.println("当前状态机+！1" + machine.getState().getId().name());

        //listenre是同步的状态，不是异步的
        machine.sendEvent(OrderEvents.PAY);

        System.out.println("当前状态机+234" + machine.getState().getId().name());

        //这样可以在statecontext中获取的到变量的信息
        machine.getExtendedState().getVariables().put("a", "asdfa");
        Message<OrderEvents> orderEvent = MessageBuilder.withPayload(OrderEvents.PAY)
                .setHeader("a", "b234").build();

        machine.sendEvent(orderEvent);

    }
}
