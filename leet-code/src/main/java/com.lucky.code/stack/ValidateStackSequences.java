package com.lucky.code.stack;

import java.util.Stack;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/27 16:56
 * @Description: 给定 pushed 和 popped 两个序列，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
 * <p>
 *  
 * <p>
 * 示例 1：
 * <p>
 * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * 输出：true
 * 解释：我们可以按以下顺序执行：
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 * 示例 2：
 * <p>
 * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * 输出：false
 * 解释：1 不能在 2 之前弹出。
 *  
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-stack-sequences
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ValidateStackSequences {

    public boolean validateStackSequences(int[] pushed, int[] popped) {

        int N = pushed.length;
        //保存变量
        Stack<Integer> stack = new Stack<>();

        int j = 0;
        for (int x : pushed) {

            //放置元素
            stack.push(x);
            //找到第一个等于pop队列的元素
            while (!stack.isEmpty() && j < N && stack.peek() == popped[j]) {

                //直接出栈
                //继续放置
                stack.pop();
                j++;
            }
        }


        //当j==N的时候代表所有元素都出栈了
        return j == N;

    }
}
