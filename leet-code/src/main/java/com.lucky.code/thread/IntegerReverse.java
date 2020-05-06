package com.lucky.code.thread;

/**
 * @description:详情见：https://leetcode-cn.com/problems/reverse-integer/
 * @author: lucky
 * @created: 2020/05/05 21:58
 */
public class IntegerReverse {

    public static  int reverse(int x) {

        int rev = 0;
        while (x != 0) {
            int newRev = rev * 10 + x % 10;
            //判断是否over flow
            if((newRev-x%10)/10!=rev){
                return 0;
            }
            rev = newRev;
            x /= 10;
        }

        return rev;
    }

    public static void main(String[] args) {
        System.out.println(324 % 10);
        System.out.println(324/10);
        System.out.println(reverse(1534236469));
    }
}
