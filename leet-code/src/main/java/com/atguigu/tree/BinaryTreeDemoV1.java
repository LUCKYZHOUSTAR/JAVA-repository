package com.atguigu.tree;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/10/17 13:42
 * @Description:
 */
public class BinaryTreeDemoV1 {


}


class BinaryTreeV1 {

    private Node root;
    private void preOrder() {
        if (root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }


    private Node preSearch(int no) {

        if (root != null) {

            return this.root.preSearch(no);
        } else {

            return null;
        }
    }
}


class Node {
    private int no;

    private Node left;

    private Node right;


    public Node preSearch(int no) {
        if (this.no == no) {
            return this;
        }

        Node result = null;


        if (this.left != null) {
            result = this.left.preSearch(no);
        }

        if (result != null) {
            return result;
        }

        if (this.right != null) {
            result = this.right.preSearch(no);
        }

        return result;
    }

    public void preOrder() {
        System.out.println(this);

        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();

        }
    }
}