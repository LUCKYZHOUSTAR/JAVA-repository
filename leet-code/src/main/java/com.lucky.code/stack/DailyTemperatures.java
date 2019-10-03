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

        //外层遍历，遍历的时候
        /**
         *
         * 功能描述:
         * 1.外层遍历，每次遍历的时候
         * 2.如果栈是空的，那么就添加
         * 3.如果栈不是空的，那么就比较新添加的值是否大于栈中栈顶的值，如果是的话，就比较，放置结果值，栈顶出队，继续比较，直到比较到当前的值在栈中找不出大于的值
         * 4.这样保证了，栈中的值，是按照大小个头进行排列的
         *
         * @param: [T]
         * @return: int[]
         * @auther: zhou
         * @date: 2019/10/3 下午1:59
         */
        for (int i = 0; i < res.length; i++) {

            //当栈中不是空并且栈顶的元素小于放置的数据的时候，结果集中进行放置数据
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                System.out.println(T[stack.peek()] + "-" + T[i]);

                int n = stack.peek();
                //栈顶中的元素要弹出，并且设置结果集的数据
                res[stack.pop()] = i - n;
            }
            //当栈是空的时候，就向栈中添加元素
            stack.push(i);
        }
        return res;

    }


    public static int[] dailyTemperaturesV2(int[] T) {

        int[] res = new int[T.length];

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < res.length; i++) {
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {

                //栈顶元素的序号
                int n = stack.peek();
                //弹出栈顶，并计算元素的差异值
                res[stack.pop()] = i - n;

            }
            //放置的是元素在数组中的序号，用来进行加减操作
            stack.push(i);
        }

        return res;
    }


    public static void main(String[] args) {
        System.out.println();
        int[] result = dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73});
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
