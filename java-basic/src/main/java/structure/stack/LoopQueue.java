package structure.stack;

import java.util.Arrays;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午2:11 2018/10/9
 */
public class LoopQueue<E> {


  public Object[] data = null;

  private int maxSize;//队列容量

  private int rear;//队列尾，允许插入

  private int front;//队列头，允许删除
  private int size;//队列当前长度


  public LoopQueue() {
    this(10);
  }

  public LoopQueue(int initialSize) {
    if (initialSize >= 0) {
      this.maxSize = initialSize;
      data = new Object[initialSize];
      front = rear = 0;
    } else {
      throw new RuntimeException("初始化大小不能小于0：" + initialSize);
    }
  }

  // 判空
  public boolean empty() {
    return size == 0;
  }


  /***
   * 添加
   * @param e
   * @return
   */
  public boolean add(E e) {

    if (size == maxSize) {
      throw new RuntimeException("队列已经满了，无法插入插入新的运算");
    }

    data[rear] = e;
    //环形的队列
    rear = (rear + 1) % maxSize;
    size++;
    return true;
  }


  /***
   * 返回队首元素，但是不删除
   * @return
   */
  public E peek() {

    if (empty()) {
      throw new RuntimeException();
    }

    return (E) data[front];
  }


  public E poll() {
    if (empty()) {
      throw new RuntimeException();
    }

    E value = (E) data[front];//保留队列的front端的元素的值
    data[front] = null;//释放队首指针
    front = (front + 1) % maxSize;
    size--;
    return value;
  }


  public int length() {
    return size;
  }


  public void clear() {
    Arrays.fill(data, null);
    size = 0;
    front = 0;
    rear = 0;
  }
}
