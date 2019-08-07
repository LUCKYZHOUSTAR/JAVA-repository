//package basic.atomic;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:AtomicReferenceFieldUpdater的内部也是unsafe来实现的
// * @Date:Create in 19:08 2017/11/1
// */
//public class UnsafeAtomicReferenceFieldUpdater<U, W> extends AtomicReferenceFieldUpdater<U, W> {
//
//
//    private final long offset;
//    private final Unsafe unsafe;
//
//    UnsafeAtomicReferenceFieldUpdater(Unsafe unsafe, Class<U> tClass, String fieldName) throws NoSuchFieldException {
//        Field field = tClass.getDeclaredField(fieldName);
//        if (!Modifier.isVolatile(field.getModifiers())) {
//            throw new IllegalArgumentException("must be volatile");
//        }
//        if (unsafe == null) {
//            throw new NullPointerException("unsafe");
//        }
//        this.unsafe = unsafe;
//        //获取该字段的偏移量
//        offset = unsafe.objectFieldOffset(field);
//    }
//
//    @Override
//    public boolean compareAndSet(U obj, W expect, W update) {
//        return unsafe.compareAndSwapObject(obj, offset, expect, update);
//    }
//
//    @Override
//    public boolean weakCompareAndSet(U obj, W expect, W update) {
//        return unsafe.compareAndSwapObject(obj, offset, expect, update);
//    }
//
//    @Override
//    public void set(U obj, W newValue) {
//        unsafe.putObjectVolatile(obj, offset, newValue);
//    }
//
//    @Override
//    public void lazySet(U obj, W newValue) {
//        unsafe.putOrderedObject(obj, offset, newValue);
//    }
//
//    @Override
//    public W get(U obj) {
//        return (W) unsafe.getObjectVolatile(obj, offset);
//    }
//}
