package basic.concurrent;

/**
 * @Author:chaoqiang.zhou
 * @Description:对java中原子类的底层代码理解
 * @Date:Create in 9:50 2018/1/19
 */
public class AtomicTest {

    /**
     * 像在java并发包中有一些原子的类，像atomicinteger、atomiclong等为何能够实现
     */

    /**
     *     public final int getAndIncrement() {return unsafe.getAndAddInt(this, valueOffset, 1);
     }
     这个方法，底层利用了unsafe来实现，下面来看一下unsafe的底层实现


     public final int getAndAddInt(Object var1, long var2, int var4) {
     int var5;
     do {
     var5 = this.getIntVolatile(var1, var2);
     } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

     return var5;
     }

     unsafe底层采用了cas原理，比较该内存地址的原始值，如果与期望的相同就更新，否则不做任何的操作
     所以在上述的代码中利用了一个do、、、while的循环，直到成功为止
     */


}
