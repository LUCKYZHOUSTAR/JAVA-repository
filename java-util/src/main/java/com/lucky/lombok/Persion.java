package com.lucky.lombok;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:02 2017/12/12
 */


@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"id"})
public class Persion {

    private long id;
    private String name;


    public static void main(String[] args) {
        Persion persion=new Persion(23,"asdf");
        System.out.println(persion);
    }

}
