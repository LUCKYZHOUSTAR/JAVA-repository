package com.lucky.design.patterns.factory.abstrac;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/25 14:37
 * @Description:
 */
public class ElfArmy implements Army {

    static final String DESCRIPTION = "this is the Elven army";
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
