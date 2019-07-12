package com.lucky.model;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/4/17 12:10
 * @Description:
 */

public class User {

    public static void main(String[] args) {
        User user = new User();
        user.setPassword(null);
        user.setPwd("22323");
        user.setUserName("sdfsd");
        System.out.println(JSONObject.toJSONString(user));

    }

    private String userName;

    private String pwd;

    private Password password;

    public String getUserName() {
        return userName;
    }


    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
