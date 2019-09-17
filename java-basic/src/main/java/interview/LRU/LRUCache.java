package interview.LRU;

import java.util.Hashtable;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/17 15:11
 * @Description:LRU的算法，通过一个链表，每次更新的时候，都把该节点放到head的后面，当容量小于count的时候，就从尾部去掉一个
 */
public class LRUCache {

    private Hashtable<String, DLinkedNode> cache = new Hashtable<>();
    private int count;

    private int capacity;


    private DLinkedNode head, tail;


    public LRUCache(int capacity) {
        this.count = 0;
        this.capacity = capacity;
        head = new DLinkedNode();
        head.pre = null;

        tail = new DLinkedNode();
        tail.post = null;


        head.post = tail;
        tail.pre = head;

    }


    public int get(String key) {

        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;//should throw exception here
        }

        // move the accessed node to the head;
        this.moveToHead(node);
        return node.value;


    }

    public void set(String key, int value) {
        DLinkedNode node = cache.get(key);

        if (node == null) {

            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            this.cache.put(key, newNode);
            this.addNode(newNode);

            ++count;

            if (count > capacity) {
                // pop the tail
                DLinkedNode tail = this.popTail();
                this.cache.remove(tail.key);
                --count;
            }
        } else {
            // update the value.
            node.value = value;
            this.moveToHead(node);
        }
    }

    /**
     * Always add the new node right after head;
     */
    private void addNode(DLinkedNode node) {
        node.pre = head;
        node.post = head.post;
        head.post.pre = node;

        head.post = node;
    }

    /**
     * Remove an existing node from the linked list.
     */
    private void removeNode(DLinkedNode node) {
        DLinkedNode pre = node.pre;
        DLinkedNode post = node.post;
        pre.post = post;
        post.pre = pre;
    }

    /**
     * Move certain node in between to the head.
     */
    private void moveToHead(DLinkedNode node) {
        this.removeNode(node);
        this.addNode(node);
    }


    // pop the current tail.
    private DLinkedNode popTail() {

        DLinkedNode res = tail.pre;
        this.removeNode(res);
        return res;
    }


}
