package cmc.lucky.stack;

import java.util.Stack;

/**
 * @Author:chaoqiang.zhou
 * @Description:用两个栈来实现一个队列
 * @Date:Create in 12:35 2017/12/4
 */
public class TwoStacksQueue {

    public Stack<Integer> stackPush;
    public Stack<Integer> stackPop;

    public TwoStacksQueue() {
        stackPop = new Stack<>();
        stackPush = new Stack<>();
    }

    public void add(int pushInt) {
        stackPush.push(pushInt);
    }


    public int poll() {
        if (stackPop.empty() && stackPush.empty()) {
            throw new RuntimeException("QUEUE is empty");
        } else if (stackPop.empty()) {
            while (!stackPush.empty()) {
                stackPop.push(stackPush.pop());
            }
        }
        return stackPop.pop();
    }
}
