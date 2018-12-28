package com.lucky.spring.statemachine;

import com.lucky.spring.Events;
import com.lucky.spring.States;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/28 11:49
 * @Description:状态机的配置类信息
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {


    /***
     * 通过上面的入门示例以及最后的小结，我们可以看到使用Spring StateMachine来实现状态机的时候，
     * 代码逻辑变得非常简单并且具有层次化。整个状态的调度逻辑主要依靠配置方式的定义，而所有的业务逻辑操作都被定义在了状态监听器中，其实状态监听器可以实现的功能远不止上面我们所述的内容，它还有更多的事件捕获，我们可以通过查看StateMachineListener接口来了解它所有的事件定义：
     * @param config
     * @throws Exception
     */
    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config
                .withConfiguration()
                .listener(listener());
    }


    /**
     * 功能描述:
     *
     * @param: configure(StateMachineTransitionConfigurer < States, Events > transitions)方法用来初始化当前状态机有哪些状态迁移动作
     * ，其中命名中我们很容易理解每一个迁移动作，都有来源状态source，目标状态target以及触发事件event。s
     * @return:
     * @auther: zhou
     * @date: 2018/12/28 下午12:01
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions
                .withExternal()
                .source(States.UNPAID).target(States.WAITING_FOR_RECEIVE)
                .action(action(), errorAction())
                .event(Events.PAY)
                .and()
                .withExternal()
                .source(States.WAITING_FOR_RECEIVE).target(States.DONE)
                .event(Events.RECEIVE);

    }


    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {

        //后期的action对应的就是具体的业务信息
        states
                .withStates()
                .initial(States.UNPAID)
                .stateEntry(States.UNPAID, action(), errorAction())
                .stateDo(States.UNPAID, action())
                .states(EnumSet.allOf(States.class));
    }

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void transition(Transition<States, Events> transition) {
                if (transition.getTarget().getId() == States.UNPAID) {
                    System.out.println("订单创建，待支付");
                    return;
                }

                if (transition.getSource().getId() == States.UNPAID
                        && transition.getTarget().getId() == States.WAITING_FOR_RECEIVE) {
                    System.out.println("用户完成支付，待收货");
                    return;
                }

                if (transition.getSource().getId() == States.WAITING_FOR_RECEIVE
                        && transition.getTarget().getId() == States.DONE) {
                    System.out.println("用户已收货，订单完成");
                    return;
                }
            }

        };
    }


    @Bean
    public Action<States, Events> action() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                System.out.println("开始执行了" + context);
                // do something
                int i = 3 / 0;
            }
        };
    }


    /***
     * 异常不错的错误信息
     * @return
     */
    @Bean
    public Action<States, Events> errorAction() {
        return new Action<States, Events>() {

            @Override
            public void execute(StateContext<States, Events> context) {
                // RuntimeException("MyError") added to context
                Exception exception = context.getException();
                exception.getMessage();
                System.out.println("捕获到错误了" + context);
            }
        };
    }

}
