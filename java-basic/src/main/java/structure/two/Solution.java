package structure.two;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午1:59 2018/10/11
 */
public class Solution {

  /**
   * <pre>
   * 原题
   * You are given two linked lists representing two non-negative numbers.
   * The digits are stored in reverse order and each of their nodes contain
   * a single digit. Add the two numbers and return it as a linked list.
   * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
   * Output: 7 -> 0 -> 8
   *
   * 题目大意
   * 有两个单链表，代表两个非负数，每一个节点代表一个数位，数字是反向存储的，
   * 即第一个结点表示最低位，最后一个结点表示最高位。求两个数的相加和，并且以链表形式返回。
   *
   * 解题思路
   * 对两个链表都从第一个开始处理，进行相加，结果再除以10求商，作为下一位相加的进位，
   * 同时记录余数，作为本位的结果，一直处理，直到所有的结点都处理完。
   * </pre>
   *
   * @param l1 第一个数
   * @param l2 第二个数
   * @return 结果
   */
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

    if (l1 == null) {
      return l2;
    }

    if (l2 == null) {
      return l1;
    }

    ListNode p1 = l1;
    ListNode p2 = l2;
    ListNode root = new ListNode(0); // 头结点
    ListNode r = root;
    root.next = l1;

    int carry = 0; // 初始进位
    int sum;
    while (p1 != null && p2 != null) {
      sum = p1.val + p2.val + carry;
      p1.val = sum % 10; // 本位的结果
      carry = sum / 10; // 本次进位

      r.next = p1;
      r = p1; // 指向最后一个相加的结点
      p1 = p1.next;
      p2 = p2.next;

    }

    if (p1 == null) {
      r.next = p2;
    } else {
      r.next = p1;
    }

    // 最后一次相加还有进位
    if (carry == 1) {
      // 开始时r.next是第一个要相加的结点
      while (r.next != null) {
        sum = r.next.val + carry;
        r.next.val = sum % 10;
        carry = sum / 10;
        r = r.next;
      }

      // 都加完了还有进位，就要创建一个新的结点
      if (carry == 1) {
        r.next = new ListNode(1);
      }
    }

    return root.next;
  }


  /***
   * 两个单链表，反向存储，求和值
   * @param l1
   * @param l2
   * @return
   */
  public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
    ListNode l3 = new ListNode(0);
    ListNode res = l3;
    int value = 0;
    int flag = 0;
    while (l1 != null || l2 != null || flag == 1) {
      int sum = flag;
      sum += (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0);
      l1 = (l1 != null ? l1.next : null);
      l2 = (l2 != null ? l2.next : null);
      l3.next = new ListNode(sum % 10);
      flag = sum / 10;
      l3 = l3.next;
    }
    return res.next;
  }


  public ListNode addTwoNumber3(ListNode l1, ListNode l2) {
    ListNode l3 = new ListNode(0);
    ListNode res = l3;

    int flag = 0;

    while (l1 != null | l2 != null | flag == 1) {
      //上一次的进位
      int sum = flag;
      sum += (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0);
      //结果对l3进行赋值
      l3.next = new ListNode(sum % 10);
      //下一次的进位
      flag = sum / 10;
      //结果从前向后进行排
      l3 = l3.next;
    }

    return res.next;
  }

  public static void main(String[] args) {
    System.out.println(12 % 10);
    System.out.println(12 / 10);
  }

}
