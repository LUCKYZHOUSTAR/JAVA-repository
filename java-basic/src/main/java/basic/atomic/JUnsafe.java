package basic.atomic;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;


/**
 * @Author:chaoqiang.zhou
 * @Description:
 * 通常我们最好也不要使用Unsafe类，除非有明确的目的，并且也要对它有深入的了解才行。
 * 要想使用Unsafe类需要用一些比较tricky的办法。Unsafe类使用了单例模式，需要通过一个静态方法getUnsafe()来获取。但Unsafe类做了限制，
 * 如果是普通的调用的话，它会抛出一个SecurityException异常；
 * 只有由主类加载器加载的类才能调用这个方法。其源码如下：
 * @Date:Create in 19:23 2017/11/1
 */
public final class JUnsafe {

    private static final Unsafe UNSAFE;



    public static void main(String[] args) {
        System.out.println(System.getSecurityManager());
    }

    static {
        Unsafe unsafe;
        try {
            //只有通过反射的调用才不会绕过安全管理器的检查
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);

        } catch (Throwable t) {


            unsafe = null;
        }
        UNSAFE = unsafe;

    }

    /**
     * Returns the {@link Unsafe}'s instance.
     */
    public static Unsafe getUnsafe() {
        return UNSAFE;
    }

    /**
     * Returns the system {@link ClassLoader}.
     */
    public static ClassLoader getSystemClassLoader() {
        if (System.getSecurityManager() == null) {
            return ClassLoader.getSystemClassLoader();
        } else {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {

                @Override
                public ClassLoader run() {
                    return ClassLoader.getSystemClassLoader();
                }
            });
        }
    }

    private JUnsafe() {
    }

}
