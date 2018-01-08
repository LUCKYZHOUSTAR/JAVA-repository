package com.tps;

/**
 * @Author:chaoqiang.zhou
 * @Description:限流算法：http://xiaobaoqiu.github.io/blog/2015/07/02/ratelimiter/
 * @Date:Create in 11:51 2018/1/8
 */
public class TpsLimit {


    //限流算法：漏桶算法和令牌算法

    /**
     * 漏桶算法：
     * 漏桶(Leaky Bucket)算法思路很简单,水(请求)先进入到漏桶里,漏桶以一定的速度出水(接口有响应速率),
     * 当水流入速度过大会直接溢出(访问频率超过接口响应速率),
     * l 漏桶算法能够强行限制数据的传输速率。
     * 然后就拒绝请求,可以看出漏桶算法能强行限制数据的传输速率.示意图如下:
     */


    /**
     * 令牌算法：
     * 令牌桶算法(Token Bucket)和 Leaky Bucket 效果一样但方向相反的算法,更加容易理解.随着时间流逝,
     * 系统会按恒定1/QPS时间间隔(如果QPS=100,则间隔是10ms)往桶里加入Token(想象和漏洞漏水相反,有个水龙头在不断的加水),
     * 如果桶已经满了就不再加了.新请求来临时,会各自拿走一个Token,如果没有Token可拿了就阻塞或者拒绝服务.
     */

    public static void main(String[] args) {
//        AtomicInteger
    }

    public final int incrementAndGet() {
        for (; ; ) {
            int current = 0;
            int next = current + 1;
            //比较后进行赋值
        }
    }
}
