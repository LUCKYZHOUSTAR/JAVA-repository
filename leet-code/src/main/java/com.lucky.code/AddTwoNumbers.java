package com.lucky.code;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/25 15:23
 * @Description: 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * <p>
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * <p>
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * <p>
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/add-two-numbers
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class AddTwoNumbers {


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p1 = l1;
        ListNode p2 = l2;

        ListNode dummyHead = new ListNode(-1);
        ListNode cur = dummyHead;
        int carried = 0;

        while (p1 != null || p2 != null) {
            int a = p1 != null ? p1.val : 0;
            int b = p2 != null ? p2.val : 0;

            ListNode next = new ListNode((a + b + carried) % 10);
            carried = (a + b + carried) / 10;
            cur.next = next;
            p1 = p1 != null ? p1 = p1.next : null;
            p2 = p2 != null ? p2 = p2.next : null;
        }


        return null;

    }


    public static void main(String[] args) {
        ListNode a = new ListNode(2);
        ListNode b = new ListNode(4);
        ListNode c = new ListNode(3);
        a.next = b;
        b.next = c;


        ListNode v1 = new ListNode(2);
        ListNode v2 = new ListNode(4);
        ListNode v3 = new ListNode(3);

        v1.next = v2;
        v2.next = v3;


        ListNode bb = addTwoNumbers(a, v1);

        System.out.println("");
    }


}
