package structure.stack;

import java.util.LinkedList;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:53 2018/10/9
 */
public class StackList<E> {

  private LinkedList<E> ll = new LinkedList<>();

  //入栈
  public void push(E e) {
    ll.addFirst(e);
  }

  //查看栈顶元素但不移除
  public E peek() {
    return ll.getFirst();
  }

  //出栈
  public E pop() {
    return ll.removeFirst();
  }

  //判空
  public boolean empty() {
    return ll.isEmpty();
  }

  //打印栈元素
  public String toString() {
    return ll.toString();
  }

}
