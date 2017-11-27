package cmc.lucky.basic.attributeMapConstant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:06 2017/11/27
 */
public class HelloWorld2ClientHandler extends ChannelInboundHandlerAdapter {

    public static final AttributeKey<HashSet<Integer>> NETTY_CHANNEL_KEY1 = AttributeKey.valueOf("netty.channel1");


    /**
     * ChannelHandlerContext的Channel已激活
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Attribute<HashSet<Integer>> attr = ctx.channel().attr(NETTY_CHANNEL_KEY1);
        HashSet<Integer> sets = attr.get();
        if (sets == null) {
            HashSet<Integer> newSet = new HashSet<Integer>();
            sets = attr.setIfAbsent(newSet);
            if (null == sets) {
                System.out.println("GGGGGGGGGGGGGGGGGGGGGGM,NUUUUUULLLLLLLLL");
                sets = newSet;
            }

            HashSet<Integer> sets2 = attr.get();
            System.out.println("RRRRRRRRRRRRRRRRR ===" + sets2.size());
            for (Integer i : sets2) {
                System.out.println("value is GGGGGGGGGGG====" + i);
            }
        }
        sets.add(1);
        HashSet<Integer> sets3 = attr.get();
        System.out.println("RRRRRRRRRRRRRRRRR2 ===" + sets3.size());
        System.out.println("HelloWorldC2ientHandler Active");
        ctx.fireChannelActive();
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Attribute<HashSet<Integer>> attr = ctx.channel().attr(NETTY_CHANNEL_KEY1);
        HashSet<Integer> sets = attr.get();
        if (sets == null) {
            System.out.println("没有值啊");
        } else {
            for (Integer i : sets) {
                System.out.println("value is ====" + i);
            }
        }
        System.out.println("HelloWorldClientHandler read Message:" + msg);
    }

    @Setter
    @Getter
    static class Student {
        String id;
        int age;
    }

}
