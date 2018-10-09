package structure.stack;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:23 2018/10/9 基于数组来实现 先进后出 假设a=1,b=1; int c =a++;--》可以看成是int c=a;a=a+1;所以a=2;
 * int d =++b;--》可以看成是b=b+1=2;int d=b=2;就是这样的 你要了解那个i++跟++i这个，++如果在前面，则会先自加，然后赋值给其他变量，如果是在后边，则先把自己的值赋给其他变量，
 * 然后自己加1。这就是区别了。--跟++用法一样。
 */
public class Stack<E> {


  private Object[] data = null;

  private int maxSize = 0;

  private int top = -1;


  Stack() {
    this(10);
  }

  Stack(int initialSize) {
    if (initialSize >= 0) {
      this.maxSize = initialSize;
      data = new Object[initialSize];
      top = -1;
    } else {
      throw new IllegalArgumentException("初始化大小不能为0");
    }
  }


  public boolean empty() {
    return top == -1 ? true : false;
  }

  /***
   * 入栈的方法
   * @param e
   * @return
   */
  public boolean push(E e) {
    if (top == maxSize - 1) {
      throw new RuntimeException("栈已经满了，无法将元素放入栈");
    }

    data[++top] = e;
    return true;
  }


  /***
   * 查看栈顶元素但是不移除
   * @return
   */
  public E peek() {

    if (top == -1) {
      throw new RuntimeException("栈为空");
    }

    return (E) data[top];
  }


  /***
   * 弹出栈顶元素
   * @return
   */
  public E pop() {

    if (top == -1) {
      throw new RuntimeException("栈为空");
    }

    return (E) data[top--];
  }


  /***
   * 返回对象在堆栈中的位置，以1为基数
   * @param e
   * @return
   */
  public int search(E e) {

    int i = top;
    while (top != -1) {
      if (peek() != e) {
        top--;
      } else {
        break;

      }
    }

    int result = top + 1;
    top = i;
    return result;
  }


}
