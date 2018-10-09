package structure.stack;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:43 2018/10/9 链式存储结构 栈都是先进后出
 */
public class LinkStack<E> {


  /**
   * 链式的节点信息
   */
  private class Node<E> {

    E e;
    Node<E> next;

    public Node(E e, Node<E> next) {
      this.e = e;
      this.next = next;
    }


    public Node() {
    }
  }


  private Node<E> top;//栈顶元素

  private int size;//当前栈大小


  public LinkStack() {
    top = null;
  }


  //当前栈的大小
  public int length() {
    return size;
  }


  /**
   * 判断栈是否为空
   */
  public boolean empty() {
    return size == 0 ? true : false;
  }


  /**
   * 放置元素,入栈操作
   */
  public boolean push(E e) {

//    if (top == null) {
//      Node top = new Node(e, null);
//      this.top = top;
//      size++;
//    } else {
//      Node top = new Node(e, this.top);
//      this.top = top;
//      size++;
//    }

    top = new Node<>(e, top);
    size++;

    return true;
  }


  /***
   * 查看栈顶元素但是不删除
   * @return
   */
  public Node<E> peek() {

    if (empty()) {
      throw new RuntimeException("空栈异常");
    }

    return top;
  }


  /**
   * 出栈操作
   */
  public Node<E> pop() {

    if (empty()) {
      throw new RuntimeException("空栈异常");
    }

    Node result = top;
    top = top.next;
    result.next = null;//释放原栈顶元素的next引用
    size--;
    return result;
  }



}
