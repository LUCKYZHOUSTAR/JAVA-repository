package cmc.lucky.stack;

import java.util.Stack;

/**
 * @Author:chaoqiang.zhou
 * @Description:利用递归操作，实现一个栈的逆序
 * @Date:Create in 17:23 2017/12/7
 */
public class ReverseStack {


    public static int getAndRemoveLastElement(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.isEmpty()) {
            return result;
        } else {
            int last = getAndRemoveLastElement(stack);
            stack.push(result);
            return result;
        }
    }
}
