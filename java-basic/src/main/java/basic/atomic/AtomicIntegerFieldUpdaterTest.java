//package basic.atomic;
//
//import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
//
///**
// * @Author:chaoqiang.zhou
// * @Description:
// * @Date:Create in 19:13 2017/11/28
// */
//public class AtomicIntegerFieldUpdaterTest {
//
//    // 创建原子更新器，并设置需要更新的对象类和对象的属性
//    private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.
//            newUpdater(User.class, "old");
//
//    public static void main(String[] args) {
//        // 设置柯南的年龄是10岁
//        User conan = new User("conan", 10);
//        // 柯南长了一岁，但是仍然会输出旧的年龄
//        System.out.println(a.getAndIncrement(conan));
//        // 输出柯南现在的年龄
//        System.out.println(a.get(conan));
//    }
//
//    public static class User {
//        private String name;
//        public volatile int old;
//
//        public User(String name, int old) {
//            this.name = name;
//            this.old = old;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public int getOld() {
//            return old;
//        }
//    }
//}
