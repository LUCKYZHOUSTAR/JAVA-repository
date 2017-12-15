package com.lucky.task.web.controller;

import com.lucky.task.web.core.HandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 17:06 2017/12/14
 */
@Controller
@RequestMapping("job")
public class JobController {

    @Autowired
    public HandlerService handlerService;

    @RequestMapping("info")
    public ModelAndView jobInfo() {

        return new ModelAndView("view/list");
    }


    @RequestMapping("add")
    public void add() {
        try {
            handlerService.addJob("ItemTask", "cmc.task.test", "*/1 * * * * ?");
        } catch (Exception e) {
        }
    }

    @RequestMapping("task/add")
    public ModelAndView addTask() {
        return new ModelAndView("view/addtask");
    }

    @RequestMapping("handler")
    public void handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handlerService.handler(request, response);
    }
}
