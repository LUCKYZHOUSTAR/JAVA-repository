package com.lucky.junit.timeout;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 14:59 2017/11/30
 */
public class MessageUtil {
    private String message;

    //Constructor
    //@param message to be printed
    public MessageUtil(String message){
        this.message = message;
    }

    // prints the message
    public void printMessage(){
        System.out.println(message);
        while(true);
    }

    // add "Hi!" to the message
    public String salutationMessage(){
        message = "Hi!" + message;
        System.out.println(message);
        return message;
    }
}
