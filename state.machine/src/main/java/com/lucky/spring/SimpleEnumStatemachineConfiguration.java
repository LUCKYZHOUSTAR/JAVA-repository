package com.lucky.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/4/12 11:46
 * @Description:
 *
 * 执行的顺序：preStateChange--》stateEntry----》stateDo---》stateChanged（listener）
 */

@Slf4j
@Configuration
@EnableStateMachineFactory
public class SimpleEnumStatemachineConfiguration extends StateMachineConfigurerAdapter<OrderStates, OrderEvents> {


    //状态转变状态，A---》事件B----》C
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {

        transitions.withExternal().source(OrderStates.SUBMITTED).target(OrderStates.PAYIED).event(OrderEvents.PAY)
                .and()
                .withExternal().source(OrderStates.PAYIED).target(OrderStates.FUFILLED).event(OrderEvents.FUFILL)
                .and()
                .withExternal().source(OrderStates.SUBMITTED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrderStates.PAYIED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL);

    }


    //状态初始化器
    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {

        states.withStates()
                .initial(OrderStates.SUBMITTED)
                //进入该状态入口的时候，会出发该action操作
                .stateEntry(OrderStates.SUBMITTED, new Action<OrderStates, OrderEvents>() {
                    @Override
                    public void execute(StateContext<OrderStates, OrderEvents> stateContext) {
                        System.out.println("获取的变量是" + stateContext.getExtendedState().getVariables().get("a"));

                    }
                })
                .stateEntry(OrderStates.PAYIED, new Action<OrderStates, OrderEvents>() {
                    @Override
                    public void execute(StateContext<OrderStates, OrderEvents> stateContext) {
                        System.out.println("获取的变量是" + stateContext.getExtendedState().getVariables().get("a"));

                    }
                })
                .state(OrderStates.PAYIED)
                .stateDo(OrderStates.PAYIED, new Action<OrderStates, OrderEvents>() {
                    @Override
                    public void execute(StateContext<OrderStates, OrderEvents> stateContext) {
                        System.out.println("状态进入了" + stateContext.getExtendedState().getVariables());
                    }
                })
                .end(OrderStates.FUFILLED)
                .end(OrderStates.CANCELLED);

    }


    //配置状态迁移的监听器信息
    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {

        //状态监听器
        StateMachineListenerAdapter<OrderStates, OrderEvents> adapter = new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
            @Override
            public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
                System.out.println(from);
                System.out.println("state change ,from{},to{}" + to);

            }
        };


        config.withConfiguration()
                .autoStartup(false)
                .listener(adapter);
    }


}