package com.lucky.jedis.lock;

import com.lucky.jedis.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Collections;

/**
 * @Author:chaoqiang.zhou
 * @Description:http://mp.weixin.qq.com/s/qJK61ew0kCExvXrqb7-RSg
 * @Date:Create in 11:19 2017/12/4
 */
public class Distributedlock {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    public static final String ip = "192.168.54.101";
    public static final int port = 6379;


    @Test
    public void test1() {
        Jedis jedis = RedisUtil.getJedis(ip, port);
        try {
            boolean result = tryGetDistributedLock(jedis, "haha", "123", 3000000);
            releaseDistributedLock(jedis,"haha","123");
        } finally {
            RedisUtil.getPool(ip, port).returnResource(jedis);

        }
    }

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        //NX，已经存在的话，就返回就是null
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }


    /**
     * 第一个为key，我们使用key来当锁，因为key是唯一的。
     * 第二个为value，我们传的是requestId，很多童鞋可能不明白，有key作为锁不就够了吗，为什么还要用到value？原因就是我们在上面讲到可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给value赋值为requestId，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().toString()方法生成。
     * 第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
     * 第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
     * 第五个为time，与第四个参数相呼应，代表key的过期时间。
     */


    private static final Long RELEASE_SUCCESS = 1L;

    /**
     * 释放分布式锁,采用lua脚本，就是一个原子性的操作
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
        //判断该客户端能够解锁请求，可以的话，就删除该key，释放，否则的话，就返回0
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }

    /**
     * 1.减少网络开销：本来5次网络请求的操作，可以用一个请求完成，原先5次请求的逻辑放在redis服务器上完成。使用脚本，减少了网络往返时延。
     2.原子操作：Redis会将整个脚本作为一个整体执行，中间不会被其他命令插入。
     3.复用：客户端发送的脚本会永久存储在Redis中，意味着其他客户端可以复用这一脚本而不需要使用代码完成同样的逻辑
     */
}
