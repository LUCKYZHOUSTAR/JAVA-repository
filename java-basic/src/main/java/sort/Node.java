package sort;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午9:05 2018/10/19
 */
public class Node {


  int val;
  Node next;

  Node(int x) {
    val = x;
  }

  //交换一个链表中，连续的两个节点的位置。比如：1->2->3->4 返回2->1->4->3.

  /**
   * 交换一个链表中，连续的两个节点的位置。比如：1->2->3->4 返回2->1->4->3.
   */
  public static Node swapPairs(Node head) {

    if (head == null || head.next == null) {
      return head;
    }
    Node dummy = new Node(0);
    Node l = head.next;
    dummy.next = head;
    while (dummy.next != null && dummy.next.next != null) {
      Node first = dummy.next;
      Node second = dummy.next.next;
      first.next = second.next;
      second.next = first;
      dummy.next = second;
      dummy = dummy.next.next;

    }
    return l;
  }

  public static void main(String[] args) {
    Node node = new Node(0);

    Node node1 = new Node(1);

    Node node2 = new Node(2);

    Node node3 = new Node(3);

    Node node4 = new Node(4);

    Node node5 = new Node(5);
    node.next = node1;
    node1.next = node2;
    node2.next = node3;
    node3.next = node4;
    node4.next = node5;
    node5.next = null;

    swapPairs(node);

    while (node.next != null) {

      System.out.println(node.val);
     node= node.next.next;
    }
  }


}
