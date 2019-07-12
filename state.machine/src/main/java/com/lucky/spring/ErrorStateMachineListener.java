package com.lucky.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/4/12 17:24
 * @Description:
 */

@Component
@Configuration
public class ErrorStateMachineListener
        extends StateMachineListenerAdapter<String, String> {

    @Override
    public void stateMachineError(StateMachine<String, String> stateMachine, Exception exception) {
        // do something with error
        System.out.println("出现异常了怎么办");
    }
}