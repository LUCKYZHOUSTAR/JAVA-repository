package basic.lock;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author:chaoqiang.zhou
 * @Description:HashTable容器在竞争激烈的并发环境下表现出效率低下的原因是所有访问HashTable的 线程都必须竞争同一把锁，假如容器里有多把锁，每一把锁用于锁容器其中一部分数据，那么
 * 当多线程访问容器里不同数据段的数据时，线程间就不会存在锁竞争，从而可以有效提高并
 * 发访问效率，这就是ConcurrentHashMap所使用的锁分段技术。首先将数据分成一段一段地存
 * 储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数
 * 据也能被其他线程访问。
 * <p>
 * 主要是采用了分段的技术，类似于每一部分的数据都有一把锁
 * @Date:Create in 18:55 2017/11/28
 */
public class ConcurrentHashMapTest {

  public static void main(String[] args) {
    int[] a = new int[2];
    a[0] = 1;
    //b和a共享一份区域
    int[] b = a;
    //此时a也会跟着变动，改变的是引用的对象的内存地址
    b[1] = 5;
    //这种方法是产生新的对象
    int[] c = Arrays.copyOf(b, b.length);
    c[1] = 9;
    for(int i=0;i<a.length;i++){
      System.out.println("a" + i + "=" + a[i]);
      System.out.println("b" + i + "=" + b[i]);

    }
  }

}
