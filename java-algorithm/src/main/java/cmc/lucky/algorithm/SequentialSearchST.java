package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:40 2017/9/11
 */
public class SequentialSearchST<Key, Value> {


    private Node first;

    private class Node {


        //每每添加一个，都添加到首节点信息上
        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        Key key;
        Value value;
        //一个后面节点的指针操作
        Node next;


    }


    public void put(Key key, Value value) {
        //查找给定的键，找到则更新其值，否则在表中新建节点信息

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(key)) {
                x.value = value;
                return;
            }

        }
        //first传递进去，构成链表的操作
        first = new Node(key, value, first);
    }

    /**
     * 根据key查询value操作
     *
     * @param key
     * @return
     */
    public Value get(Key key) {

        //查找给定的键，返回相关联的值信息
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.value;
            }
        }

        return null;
    }

}
