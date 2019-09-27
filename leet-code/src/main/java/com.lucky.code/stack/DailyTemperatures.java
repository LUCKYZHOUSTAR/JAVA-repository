package com.lucky.code.stack;

import java.util.Stack;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/27 17:06
 * @Description: 根据每日 气温 列表，请重新生成一个列表，对应位置的输入是你需要再等待多久温度才会升高超过该日的天数。如果之后都不会升高，请在该位置用 0 来代替。
 * <p>
 * 例如，给定一个列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的输出应该是 [1, 1, 4, 2, 1, 1, 0, 0]。
 * <p>
 * 提示：气温 列表长度的范围是 [1, 30000]。每个气温的值的均为华氏度，都是在 [30, 100] 范围内的整数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/daily-temperatures
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class DailyTemperatures {

    public static int[] dailyTemperatures(int[] T) {

        int[] res = new int[T.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < res.length; i++) {

            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                System.out.println(T[stack.peek()]+"-"+T[i]);

                int n = stack.peek();
                res[stack.pop()] = i - n;
            }
            //该元素的坐标，初始化是0
            stack.push(i);
        }
        return res;

    }


    public static void main(String[] args) {
        System.out.println(dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73}));
    }
}
