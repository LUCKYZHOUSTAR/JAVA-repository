package com.lucky.code.stack;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/12/29 19:01
 * @Description:
 */
public class Fenzhi {

    public static int f(int n) {
        if (n < 1) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }

        return f(n - 1) + f(n - 2);
    }


    public static void dfs(int count, int target) {
// 边界条件
        if (target <= 2) {
            count += target; // 当剩余一个台阶是即累加一种方案，剩余两个台阶时累加两种方案
            return;
        }
// 下面是两个基本点选择一步和选择两步
// 选择一步
        dfs(count, target - 1);
// 选择两步
        dfs(count, target - 2);

        System.out.println(count);
    }


    public static void main(String[] args) {
        System.out.println(f(4));


        dfs(0,4);
    }
}
