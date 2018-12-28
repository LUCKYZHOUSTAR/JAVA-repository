package com.lucky.design.patterns.factory.abstrac;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/25 14:38
 * @Description:
 */
public class ElfCastle implements Castle {

    static final String DESCRIPTION = "this is the Elven castle";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
