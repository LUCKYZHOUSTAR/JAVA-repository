package structure.stack;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午3:43 2018/10/9
 */
public class LinkedLoop {


    private String userName;

    /**
     * 判断是否有环，复杂度o(1)
     */
    public boolean hasCycle(Node node) {

        Node slow, fast;

        slow = fast = node;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (fast == slow) {
                return true;
            }
        }

        return false;

    }


    /***
     * 寻找倒数第k个元素
     * 我们能够定义两个指针。第一个指针从链表的头指针開始遍历向前走k-1。第二个指针保持不动；从第k步開始
     * 第二个指针也开化寺从链表的头指针開始遍历。
     因为两个指针的距离保持在k-1。当第一个（走在前面的）指针到达链表的尾指结点时，第二个指针正好是倒数第k个结点。
     * @param head
     * @param k
     * @return
     */
    public Node FindKthToTail(Node head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        Node ANode = head;
        Node BNode = null;
        for (int i = 0; i < k - 1; i++) {
            if (ANode.next != null) {
                ANode = ANode.next;
            } else {
                return null;
            }
        }
        BNode = head;
        while (ANode.next != null) {
            ANode = ANode.next;
            BNode = BNode.next;
        }
        return BNode;
    }
}
