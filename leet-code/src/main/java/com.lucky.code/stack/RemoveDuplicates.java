package com.lucky.code.stack;

import java.util.Stack;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/27 15:35
 * @Description: 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * <p>
 * 在 S 上反复执行重复项删除操作，直到无法继续删除。
 * <p>
 * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
 * <p>
 *  
 * <p>
 * 示例：
 * <p>
 * 输入："abbaca"
 * 输出："ca"
 * 解释：
 * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class RemoveDuplicates {


    /**
     * 功能描述:
     *
     * @param: 题目的关键在于 删除重复项，因此重点在于找到所有重复项，包含 因为删除而产生的重复项，因此可以使用栈实现。
     * 每次添加时比较是否与栈顶元素相同
     * <p>
     * 若相同则删除栈顶元素且不插入
     * 若不相同则插入新元素
     * <p>
     * 作者：Jiachen_Zhang
     * 链接：https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string/solution/hen-jian-dan-de-ti-mu-shi-yong-zhan-jiu-neng-shi-x/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @return:
     * @auther: zhou
     * @date: 2019/9/27 下午3:35
     */
    public String removeDuplicates(String S) {


        char[] s = S.toCharArray();
        int len = s.length;

        //就是栈，先进后出，正好符合顺序，在入栈的时候，判断是否重复重复的话，栈顶弹出
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            if (stack.isEmpty() || s[i] != stack.peek()) {
                //栈为空，或者不相等，就进行入栈操作
                stack.push(s[i]);
            } else {
                stack.pop();
            }
        }


        StringBuilder sb = new StringBuilder();
        for (Character c : stack) {
            sb.append(c);
        }

        return sb.toString();
    }

}
