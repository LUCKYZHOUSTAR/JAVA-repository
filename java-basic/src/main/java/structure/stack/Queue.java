package structure.stack;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:55 2018/10/9 队列的顺序存储
 */
public class Queue<E> {


  private Object[] data = null;
  private int maxSize;//队列容量
  private int front;//队列头，允许删除
  private int rear;//队列尾，允许插入

  //构造函数
  public Queue() {
    this(10);
  }

  public Queue(int initialSize) {
    if (initialSize >= 0) {
      this.maxSize = initialSize;
      data = new Object[initialSize];
      front = rear = 0;
    } else {
      throw new RuntimeException("初始化大小不能小于0：" + initialSize);
    }
  }


  /**
   * 判断是否为空
   */
  public boolean empty() {

    return rear == front ? true : false;
  }


  /***
   * 插入
   * @param e
   * @return
   */
  public boolean add(E e) {

    if (rear == maxSize) {
      throw new RuntimeException("队列已经满了，无法插入新的元素");
    }

    data[rear++] = e;
    return true;
  }


  /**
   * 返回队首元素，但不删除
   */
  public E peek() {

    if (empty()) {
      throw new RuntimeException("空队列异常");
    }

    return (E) data[front];
  }


  /***
   * 出队
   * @return
   */
  public E poll() {

    if (empty()) {
      throw new RuntimeException("空队列异常");
    }

    E value = (E) data[front];
    data[front++] = null;//释放队列的front元素的值
    return value;

  }


  /**
   * 返回队列长度
   */
  public int length() {
    return rear - front;
  }


}
