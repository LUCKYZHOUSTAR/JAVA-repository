package com.atguigu.linkedlist;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/23 14:09
 * @Description:
 */
public class Node {


    public static void main(String[] args) {

        NodeTest head = new NodeTest();
        head.setData(0);
        head.setNext(null);

        NodeTest tail = head;


        NodeTest node = new NodeTest();
        node.setData(1);

        node.next = tail;

        tail=node;

        System.out.println("");

    }
    public static class NodeTest{

        int data;

        NodeTest next;


        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public NodeTest getNext() {
            return next;
        }

        public void setNext(NodeTest next) {
            this.next = next;
        }
    }
}
