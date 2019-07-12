package com.lucky.web;

import com.lucky.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:28 2017/12/1
 */
/*@*/
//@RestController:相当于下面用了ResponseBody
@Controller
public class TestController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(String name) {
        return "hello world!";
    }


    @RequestMapping("/user")
    @ResponseBody
    public String hello(@RequestBody User user) {
        return "hello" + user.getUserName();
    }


}


