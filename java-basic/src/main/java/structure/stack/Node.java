package structure.stack;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午11:23 2018/10/9
 */
public class Node {


  //数据域
  public int data;


  public Node next;

  public Node() {

  }


  public Node(int data) {
    this.data = data;
  }


  public Node(int data, Node next) {
    this.data = data;
    this.next = next;
  }
}
