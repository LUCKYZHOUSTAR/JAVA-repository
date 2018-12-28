package com.lucky.spring.statemachine;


import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.function.Consumer;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/28 15:13
 * @Description:
 */
public interface Transaction extends Action {

    final String key = "aa";
    @Override
    default void execute(StateContext stateContext) {

    }


    void doService(Consumer action);
}
