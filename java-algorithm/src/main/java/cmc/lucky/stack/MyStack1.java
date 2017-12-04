package cmc.lucky.stack;

import java.util.Stack;

/**
 * @Author:chaoqiang.zhou
 * @Description:栈是先进后出，向里面进行放置 把另一端称为栈底。向一个栈插入新元素又称作进栈、入栈或压栈，它是把新元素放到栈顶元素的上面，使之成为新的栈顶元素；
 * 从一个栈删除元素又称作出栈或退栈，它是把栈顶元素删除掉，使其相邻的元素成为新的栈顶元素。
 * @Date:Create in 18:54 2017/12/1
 */
public class MyStack1 {
    private Stack<Integer> stackData;
    //只记录最小的值信息，这样会节省很多空间
    private Stack<Integer> stackMin;


    public MyStack1() {
        this.stackData = new Stack<>();
        this.stackMin = new Stack<>();
    }

    public void push(int newNum) {
        if (this.stackMin.isEmpty()) {
            this.stackMin.push(newNum);
        } else if (newNum <= this.getMin()) {
            this.stackMin.push(newNum);
        }
        this.stackData.push(newNum);
    }


    public int pop() {
        if (this.stackData.isEmpty()) {
            throw new RuntimeException("your stack is empty");
        }
        int value = this.stackData.pop();
        //这一步骤可以采用，空间换时间
        if (value == this.getMin()) {
            this.stackMin.pop();
        }
        return value;
    }

    public int getMin() {
        if (this.stackMin.isEmpty()) {
            throw new RuntimeException("your stack is empty");
        }

        return this.stackMin.peek();
    }

}
