package com.lucky.lombok;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:17 2017/12/13
 */
public class LangUtils {
    public static final int HASH_SEED = 17;
    public static final int HASH_OFFSET = 37;

    private LangUtils() {
        super();
    }

    public static int hashCode(final int seed, final int hashcode) {
        return seed * HASH_OFFSET + hashcode;
    }

    public static int hashCode(final int seed, final Object obj) {
        return hashCode(seed, obj != null ? obj.hashCode() : 0);
    }

    public static int hashCode(final int seed, final boolean b) {
        return hashCode(seed, b ? 1 : 0);
    }

    public static boolean equals(final Object obj1, final Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

}
