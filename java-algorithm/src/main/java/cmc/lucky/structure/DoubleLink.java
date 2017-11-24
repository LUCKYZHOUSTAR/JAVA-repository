package cmc.lucky.structure;

/**
 * @Author:chaoqiang.zhou
 * @Description:双向链表
 * @Date:Create in 19:16 2017/9/25
 */
public class DoubleLink<Key, Value> {


    public static void main(String[] args) {
        DoubleLink<String, String> doubleLink = new DoubleLink<String, String>();
        doubleLink.addNode("12", "34");
        doubleLink.addNode("22", "34");
        doubleLink.addNode("32", "34");
        doubleLink.print();

        System.out.println(doubleLink.get("12").value);

        doubleLink.deleteNode("12");
        doubleLink.print();
    }

    private Node first;

    class Node {
        private Node head;
        private Node tail;
        private Key key;
        private Value value;

        public Node(Node head, Node tail, Key key, Value value) {
            this.head = head;
            this.tail = tail;
            this.key = key;
            this.value = value;
        }


        public Node getHead() {
            return head;
        }

        public void setHead(Node head) {
            this.head = head;
        }

        public Node getTail() {
            return tail;
        }

        public void setTail(Node tail) {
            this.tail = tail;
        }

        public Key getKey() {
            return key;
        }

        public void setKey(Key key) {
            this.key = key;
        }

        public Value getValue() {
            return value;
        }

        public void setValue(Value value) {
            this.value = value;
        }


    }


    public void addNode(Key key, Value value) {
        Node node = new Node(null, null, key, value);
        if (first == null) {
            first = node;
        } else {
            first = new Node(null, first, key, value);
            first.getTail().setHead(first);
        }
    }


    public void print() {
        for (Node node = first; node != null; node = node.tail) {
            System.out.println(node.key + "==" + node.value);
        }
    }


    /**
     * 删除节点操作
     *
     * @param key
     */
    public void deleteNode(Key key) {

        Node node = get(key);
        if (node != null) {
            //代表删除的是首节点
            if (node.head == null) {
                node.tail.head = null;
            } else if (node.tail == null) {
                //代表删除的是尾部的节点
                node.head.tail = null;
            } else {
                node.head.tail = node.tail;
                node.tail.head = node.head;
            }
            //释放空间
            node.head = null;
            node.tail = null;
        }
    }


    public Node get(Key key) {
        for (Node node = first; node != null; node = node.tail) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }


}
