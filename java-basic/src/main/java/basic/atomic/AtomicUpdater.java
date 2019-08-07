//package basic.atomic;
//
//
//import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
//
///**
// * @Author:chaoqiang.zhou
// * @Description::final修饰的类不能被继承
// * @Date:Create in 19:17 2017/11/1
// */
//public final class AtomicUpdater {
//
//
//    public static <U, W> AtomicReferenceFieldUpdater<U, W> newAtomicReferenceFieldUpdater(Class<U> tClass, Class<W> vClass, String fieldName) {
//        try {
//            return new UnsafeAtomicReferenceFieldUpdater<>(JUnsafe.getUnsafe(), tClass, fieldName);
//        } catch (Throwable t) {
//            //失败的话，还是用原生的来控制
//            return AtomicReferenceFieldUpdater.newUpdater(tClass, vClass, fieldName);
//        }
//
//    }
//
//    private AtomicUpdater() {
//    }
//}
