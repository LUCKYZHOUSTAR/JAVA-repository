package com.lucky.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/28 11:57
 * @Description:
 */
@RestController
public class TestController {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @RequestMapping("/testMachine")
    @ResponseBody
    public String testMachine() {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
//        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
        return "success";
    }


    @RequestMapping("/testMachine2")
    @ResponseBody
    public void testMachine2() {
        stateMachine.start();
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.PAY);
        stateMachine.sendEvent(Events.RECEIVE);
    }
}