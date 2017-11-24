package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:46 2017/9/25
 */
public class LinkTest {
    public static void main(String[] args) {
        Link l=new Link();
        l.addNode("A");
        l.addNode("B");
        l.addNode("C");
        l.addNode("D");
        l.addNode("E");
        System.out.println("==========增加之后的内容==========");
        l.printNode();
        System.out.println("\n包含D:"+l.contains("D"));
        System.out.println("==========删除之前的内容==========");
        l.deleteNode("A");
        System.out.println("==========删除之后的内容==========");
        l.printNode();
    }
}

class Link{//链表的完成类
    class Node{//保存每个节点
        private String data;//节点内容
        private Node next;//下一个节点
        public Node(String data){
            this.data=data;
        }
        public void add(Node newNode) {//将节点加入到合适的位置
            if(this.next==null){
                this.next=newNode;
            }else{
                this.next.add(newNode);
            }
        }
        public void print() {//输出节点的内容
            System.out.print(this.data+"\t");
            if(this.next!=null){
                this.next.print();//递归调用输出
            }
        }
        public boolean search(String data){//内部搜索的方法
            if(data.equals(this.data)){
                return true;
            }else{
                if(this.next!=null){//向下继续判断
                    return this.next.search(data);
                }else{
                    return false;
                }
            }
        }
        public void delete(Node previous, String data) {
            if(data.equals(this.data)){//找到了匹配的节点
                previous.next=this.next;//空出当前的节点
            }else{
                if(this.next!=null){
                    this.next.delete(this, data);//继续查找
                }
            }
        }
    }
    private Node root;//链表中的根节点
    public void addNode(String data){//增加节点
        Node newNode=new Node(data);
        if(root==null){
            root=newNode;
        }else{
            root.add(newNode);
        }
    }
    public void printNode(){//链表的输出
        if(root!=null){
            root.print();
        }
    }
    public boolean contains(String name){//判断元素是否存在
        return this.root.search(name);
    }
    public void deleteNode(String data){//链表删除节点
        if(this.contains(data)){
            if(this.root.data.equals(data)){//如果是根节点
                this.root=this.root.next;//修改根节点
            }else{
                this.root.next.delete(root,data);//把下一个节点的前节点和要删除的节点内容一起传入
            }
        }
    }
}
