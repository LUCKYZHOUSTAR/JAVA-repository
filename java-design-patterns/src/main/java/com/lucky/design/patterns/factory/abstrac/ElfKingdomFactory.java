package com.lucky.design.patterns.factory.abstrac;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/25 14:37
 * @Description:
 */
public class ElfKingdomFactory implements KingdomFactory {
    @Override
    public Castle createCastle() {
        return new ElfCastle();
    }

    @Override
    public King createKing() {
        return new ElfKing();
    }

    @Override
    public Army creatArmy() {
        return new ElfArmy();
    }

}
