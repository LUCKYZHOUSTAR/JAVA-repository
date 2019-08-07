//package basic.concurrent;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:
// * @Date:Create in 17:46 2017/12/19
// */
//public class UnsafeTest {
//    private static final Unsafe UNSAFE;
//
//    static {
//        Unsafe unsafe;
//        try {
//            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
//            unsafeField.setAccessible(true);
//            unsafe = (Unsafe) unsafeField.get(null);
//        } catch (Throwable t) {
//            unsafe = null;
//        }
//
//        UNSAFE = unsafe;
//    }
//
//    public static void main(String[] args) {
//        System.out.println(UNSAFE==null);
//    }
//}
