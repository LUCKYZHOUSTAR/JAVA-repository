package structure.stack;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/5 15:50
 * @Description:
 */
public class LinkQueueTest<E> {
    private Node front;

    private Node rear;

    private int size;

    private class Node<E> {
        E data;

        private Node next;


        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }
    }


    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }


    public void add(E e) {

        if (empty()) {
            front = new Node(e, null);
            rear = front;

            size++;
        } else {
            Node node = new Node(e, null);
            rear.next = node;
            rear = node;

            size++;
        }
    }

    //返回队首元素，但不删除
    public Node<E> peek() {
        if (empty()) {
            throw new RuntimeException("空队列异常！");
        } else {
            return front;
        }
    }

    public Node<E> poll() {
        if (empty()) {
            throw new RuntimeException("空队列异常！");
        } else {

            Node<E> value = front;
            front = front.next;
            value.next = null;
            size--;
            return value;

        }
    }
}
