package com.lucky.code.stack;

import java.util.Stack;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/10/3 14:30
 * 给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
 * <p>
 * 注意:
 * <p>
 * num 的长度小于 10002 且 ≥ k。
 * num 不会包含任何前导零。
 * 示例 1 :
 * <p>
 * 输入: num = "1432219", k = 3
 * 输出: "1219"
 * 解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
 * 示例 2 :
 * <p>
 * 输入: num = "10200", k = 1
 * 输出: "200"
 * 解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
 * 示例 3 :
 * <p>
 * 输入: num = "10", k = 2
 * 输出: "0"
 * 解释: 从原数字移除所有的数字，剩余为空就是0。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-k-digits
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @Description:
 */
public class RemoveKdigits {

    /**
     * 功能描述:
     *
     * @param: 使用栈，需要指明的是，在字符形式下，依然有 "1" < "2" ... < "9".
     * 我们依次把数字（字符格式）压入栈中；每次压入的时候都和栈顶的数做比较，如果栈顶的数比较大，就把栈顶的数 pop 出来（循环检查），直到要压入栈的数小于等于栈顶的数；统计从栈中 pop 出数的个数 cnt，如果cnt 超过了给定的 k；则不再比较，直接压入栈中；
     * 如果当前要压入的数为 "0"，并且栈为空，那么跳过次数，不压入栈中；
     * @return:
     * @auther: zhou
     * @date: 2019/10/3 下午2:31
     */
    public static String removeKdigits(String num, int k) {

        char[] chars = num.toCharArray();

        //如果弹出的数字比整个数组都打，直接为0
        if (k >= chars.length) {
            return "0";
        }


        Stack<Character> res = new Stack<>();
        int popNum = 0;
        //入栈，入栈的数据一定要比栈顶元素小
        for (int i = 0; i < chars.length; i++) {
            //循环遍历，入栈的数据一定要比栈顶的元素小
            while (!res.isEmpty() && res.peek() > chars[i]) {
                //如果已经弹出了k个数字，那么后续都可以入栈即可
                if (popNum == k) {
                    break;
                }
                res.pop();
                popNum++;
            }
            //栈为空的话，当前数字是0的话，就不入栈，放置出现0200的数字
            if (res.isEmpty() && chars[i] == '0') {
                continue;
            } else {
                res.push(chars[i]);
            }
        }


        //入栈完毕后，发现弹出的数字还是没有达到位数，直接从栈顶弹出即可
        for (; popNum < k; popNum++) {
            res.pop();
        }
        if (res.isEmpty()) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (char aChar : res) {
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //112 1
        System.out.println(removeKdigits("112", 1));
    }
}
