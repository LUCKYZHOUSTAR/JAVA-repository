package basic.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 11:02 2017/11/10
 */
public class GuidUtil {
    private static final char[] randomChar = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n'};
    private static AtomicInteger inc = new AtomicInteger(0);
    private static String ipHex;

    public static String getNextUid(String seed) {
        int curr = inc.getAndIncrement();
        if (curr >= 99999) {
            inc.getAndSet(curr % 99999);
        }
        String randomSeed = RandomStringUtils.random(2, randomChar);
        String prefix = seed == null ? "" : seed;
        String currentTmls = String.valueOf(System.currentTimeMillis());
        return prefix + currentTmls + randomSeed + ipHex + curr;
    }

    static {
        String ip = "";
        try {
            ip = IpUtil.getRealIp();
        } catch (Exception e) {
            ip = String.valueOf(new SecureRandom().nextInt(99999999));
        }
        String[] split = ip.split("\\.");
        ipHex = "";
        for (String string : split) {
            ipHex += Integer.toHexString(Integer.valueOf(string).intValue());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            System.out.println(getNextUid("workFlow"));
            System.out.println(getNextUid("workFlow"));
            System.out.println(getNextUid("workFlow"));
        }

    }

}
