package structure.tree;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import javax.swing.tree.TreeNode;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午2:35 2018/10/9
 * 平衡二叉树它或者是一棵空树，或者是具有一下性质的二叉查找树--

它的结点左子树和右子树的深度之差不超过1,而且该结点的左子树和右子树都是一棵
 */
public class BinaryTree {


  /***
   * 根节点
   */
  private Node root;

  /***
   *　①、查找值比当前节点值大，则搜索右子树；

   　　②、查找值等于当前节点值，停止搜索（终止条件）；

   　　③、查找值小于当前节点值，则搜索左子树；

   用变量current来保存当前查找的节点，参数key是要查找的值，刚开始查找将根节点赋值到current。接在在while循环中，将要查找的值和current保存的节点进行对比。如果key小于当前节点，则搜索当前节点的左子节点，如果大于，则搜索右子节点，如果等于，则直接返回节点信息。当整个树遍历完全，即current == null，那么说明没找到查找值，返回null。

   　　树的效率：查找节点的时间取决于这个节点所在的层数，每一层最多有2n-1个节点，总共N层共有2n-1个节点，那么时间复杂度为O(logn),底数为2。
   * @param key
   * @return
   */
  public Node find(int key) {

    Node current = root;
    while (current != null) {
      if (current.getData() > key) {

        current = current.getLeftNode();
      } else if (current.getData() < key) {
        current = current.getRightNode();
      } else {
        return current;
      }
    }

    return null;
  }


  /**
   * 　 要插入节点，必须先找到插入的位置。与查找操作相似，由于二叉搜索树的特殊性，待插入的节点也需要从根节点开始进行比较 ，小于根节点则与根节点左子树比较，反之则与右子树比较，直到左子树为空或右子树为空，则插入到相应为空的位置
   * ，在比较的过程中要注意保存父节点的信息 及 待插入的位置是父节点的左子树还是右子树，才能插入到正确的位置。
   */
  public boolean insert(int key) {

    Node newNode = new Node(key, null, null);

    if (root == null) {
      root = newNode;
      return true;
    }

    Node current = root;
    Node parentNode = null;

    //层层遍历节点信息
    while (current != null) {
      //当前值比插入的值打，搜索左子节点，保留查找到的当前的节点信息
      parentNode = current;
      //如果找到大于的key
      if (current.getData() > key) {

        //判断是否有左子节点，如果没有直接放入
        current = current.getLeftNode();

        if (current == null) {
          parentNode.leftNode = newNode;
          return true;
        }
      } else {
        current = current.getRightNode();
        if (current == null) {
          parentNode.rightNode = newNode;
          return true;
        }
      }
    }

    return false;
  }

  //删除节点,太复杂，可以增加一个删除的标记，是否删除
  public boolean delete(Object key) {
    return false;
  }


  //前序遍历
  public void preOrder(Node current) {

    if (current != null) {
      System.out.println(current.getData() + "");
      preOrder(current.leftNode);
      preOrder(current.rightNode);

    }
  }


  public void middleOrder(Node current) {
    if (current != null) {
      middleOrder(current.leftNode);
      System.out.println(current.getData() + "");
      middleOrder(current.rightNode);
    }
  }


  public void postOrder(Node current) {
    if (current != null) {
      postOrder(current.leftNode);
      postOrder(current.rightNode);
      System.out.println(current.getData() + "");
    }
  }


  /***
   * 查找最大值
   * @return
   */
  public Node findMax() {

    Node current = root;
    Node maxNode = current;

    while (current != null) {
      maxNode = current;
      current = current.rightNode;
    }

    return maxNode;
  }


  //找到最小值
  public Node findMin() {
    Node current = root;
    Node minNode = current;
    while (current != null) {
      minNode = current;
      current = current.leftNode;
    }
    return minNode;
  }


  // 获取最大深度
  public static int getMaxDepth(Node root) {
    if (root == null) {
      return 0;
    } else {
      int left = getMaxDepth(root.leftNode) + 1;
      int right = getMaxDepth(root.rightNode) + 1;
      return Math.max(left, right);
    }
  }


  // 获取最大宽度
  public static int getMaxWidth(Node root) {
    if (root == null) {
      return 0;
    }

    Queue<Node> queue = new ArrayDeque<Node>();
    int maxWitdth = 1; // 最大宽度
    queue.add(root); // 入队

    while (true) {
      int len = queue.size(); // 当前层的节点个数
      if (len == 0) {
        break;
      }
      while (len > 0) {// 如果当前层，还有节点
        Node t = queue.poll();
        len--;
        if (t.leftNode != null) {
          queue.add(t.leftNode); // 下一层节点入队
        }
        if (t.rightNode != null) {
          queue.add(t.rightNode);// 下一层节点入队
        }
      }
      maxWitdth = Math.max(maxWitdth, queue.size());
    }
    return maxWitdth;
  }

}
