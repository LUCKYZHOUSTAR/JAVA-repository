package com.lucky.code;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/25 20:43
 * 题目来源于 LeetCode 上第 3 号问题：无重复字符的最长子串。题目难度为 Medium，目前通过率为 29.0% 。
 * <p>
 * 题目描述
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 题目解析
 * 建立一个256位大小的整型数组 freg ，用来建立字符和其出现位置之间的映射。
 * <p>
 * 维护一个滑动窗口，窗口内的都是没有重复的字符，去尽可能的扩大窗口的大小，窗口不停的向右滑动。
 * <p>
 * （1）如果当前遍历到的字符从未出现过，那么直接扩大右边界；
 * （2）如果当前遍历到的字符出现过，则缩小窗口（左边索引向右移动），然后继续观察当前遍历到的字符；
 * （3）重复（1）（2），直到左边索引无法再移动；
 * （4）维护一个结果res，每次用出现过的窗口大小来更新结果 res，最后返回 res 获取结果。
 * @Description:
 */
public class LengthOfLongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            //是否出现了该字符
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }


    //abcabcaa
    //保存目前的最长子串值
    //两个指针，右指针扩容，左指针保持不变，一次前进，分配每个数值的坐标，当发现有重复的数值的时候，计算这两个数值的坐标差即可，其实就是左指针移动到了这个位置
    //两个指针，左右指针
    public static int lengthOfLongestSubstringV1(String s) {

        int n = s.length(), ans = 0;
        int[] index = new int[128];
        //j:代表的是右边的指针  i：代表的是左边的指针
        for (int j = 0, i = 0; j < n; j++) {

            //最长子串的值，就是J-I+1,右边指针-左边指针+1,j和i，代表的就是每个数值的坐标，或者说是位置吧
            //先开始寻找左边的指针,找到目前数值的坐标，与目前的做指针的坐标进行比较，因为前进的坐标，不能再次回退了
            i = Math.max(index[s.charAt(j)], i);
            //j-i+1代表的目前最长子串的值，与ans比较，大于的话，就进行赋值操作
            ans = Math.max(ans, j - i + 1);
            //给当前的位置进行坐标赋值
            index[s.charAt(j)] = j + 1;
        }

        return ans;

    }


    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstringV1("dvdf"));
//        System.out.println(lengthOfLongestSubstringV1("abcd"));
    }


}
