package com.lucky.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.access.StateMachineAccess;
import org.springframework.statemachine.access.StateMachineFunction;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/4/12 13:29
 * @Description:
 */

@Service
public class OrderService {


    @Autowired
    private StateMachineFactory<OrderStates, OrderEvents> factory;


    public StateMachine<OrderStates, OrderEvents> build(String orderId) {

        //获取该订单状态机的id
        StateMachine<OrderStates, OrderEvents> sm = this.factory.getStateMachine("orderId");
        sm.stop();
        sm.getStateMachineAccessor().doWithAllRegions(new StateMachineFunction<StateMachineAccess<OrderStates, OrderEvents>>() {
            @Override
            public void apply(StateMachineAccess<OrderStates, OrderEvents> sma) {

                sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<OrderStates, OrderEvents>() {
                    @Override
                    public void preStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message, Transition<OrderStates, OrderEvents> transition, StateMachine<OrderStates, OrderEvents> stateMachine) {
//                        //DO BUSINESS 做业务逻辑操作,持久化操作
//                        Optional.ofNullable(message).ifPresent(msg->{
//                            Optional.ofNullable(msg.getHeaders())
//                        });


                        System.out.println("状态发生变化了");
                    }

                });


                //每次初始化的时候，应该从订单中获取当前的状态信息
                sma.resetStateMachine(new DefaultStateMachineContext<OrderStates, OrderEvents>(OrderStates.SUBMITTED, null, null, null));
            }

        });


        //启动
        sm.start();
        return sm;

    }
}
