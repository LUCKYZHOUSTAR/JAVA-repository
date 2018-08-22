package cmc.lucky.interview;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午3:41 2018/8/22
 */
public class Node {

  private int data;
  private Node leftNode;
  private Node rightNode;

  public Node(int data, Node leftNode, Node rightNode) {
    this.data = data;
    this.leftNode = leftNode;
    this.rightNode = rightNode;
  }

  public int getData() {
    return data;
  }

  public void setData(int data) {
    this.data = data;
  }

  public Node getLeftNode() {
    return leftNode;
  }

  public void setLeftNode(Node leftNode) {
    this.leftNode = leftNode;
  }

  public Node getRightNode() {
    return rightNode;
  }

  public void setRightNode(Node rightNode) {
    this.rightNode = rightNode;
  }
}
