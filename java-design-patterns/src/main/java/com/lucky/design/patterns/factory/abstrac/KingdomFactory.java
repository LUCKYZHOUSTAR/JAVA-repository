package com.lucky.design.patterns.factory.abstrac;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/25 14:36
 * @Description:
 */
public interface KingdomFactory {


    Castle createCastle();

    King createKing();

    Army creatArmy();
}


