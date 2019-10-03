//package com.lucky.code.stack;
//
//import javax.swing.tree.TreeNode;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Stack;
//
///**
// * @Auther: chaoqiang.zhou
// * @Date: 2019/10/3 16:06
// * @Description: 给定一个二叉树，返回它的中序 遍历。
// * <p>
// * 示例:
// * <p>
// * 输入: [1,null,2,3]
// * 1
// * \
// * 2
// * /
// * 3
// * <p>
// * 输出: [1,3,2]
// * 进阶: 递归算法很简单，你可以通过迭代算法完成吗？
// * <p>
// * 来源：力扣（LeetCode）
// * 链接：https://leetcode-cn.com/problems/binary-tree-inorder-traversal
// * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
// */
//public class InorderTraversal {
//    public List<Integer> inorderTraversal(TreeNode root) {
//        LinkedList<Integer> res = new LinkedList<Integer>();
//
//        if (root == null)
//            return res;
//
//        Stack<TreeNode> aux = new Stack<TreeNode>();
//        TreeNode node = root;//node指向待处理节点
//
//        while (node != null || !aux.isEmpty()) {
//            while (node != null) {
//                //当前节点不为null，将当前节点入栈等到该节点的左子树全部处理完后在处理当前节点
//                aux.add(node);
//                node = node.left;//先处理左孩子节点
//            }
//            TreeNode temp = aux.pop();
//            res.add(temp.val);//node没有左孩子，则输出当前node节点
//            node = temp.right;//处理node的右子树
//        }
//        return res;
//
//    }
//}
