package com.lucky.code.stack;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/27 15:48
 * @Description: 给定两个没有重复元素的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。找到 nums1 中每个元素在 nums2 中的下一个比其大的值。
 * <p>
 * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出-1。
 * <p>
 * 示例 1:
 * <p>
 * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出: [-1,3,-1]
 * 解释:
 * 对于num1中的数字4，你无法在第二个数组中找到下一个更大的数字，因此输出 -1。
 * 对于num1中的数字1，第二个数组中数字1右边的下一个较大数字是 3。
 * 对于num1中的数字2，第二个数组中没有下一个更大的数字，因此输出 -1。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/next-greater-element-i
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class NextGreaterElement {


    //由于数组1是数组2的子集，其实只要保存好顺序即可
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] result = new int[nums1.length];
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        //遍历父子集合
        for (int num : nums2) {
            //如果为空的话，直接入栈，如果小于当前值的话，保存关系
            while (!stack.isEmpty() && stack.peek() < num) {
                hashMap.put(stack.pop(), num);
            }
            stack.push(num);
        }
        //遍历子集合，进行输出序号即可
        for (int i = 0; i < result.length; i++) {
            result[i] = hashMap.getOrDefault(nums1[i], -1);
        }
        return result;


    }


    public static void main(String[] args) {
        int[] a = new int[]{4, 1, 2};
        int[] b = new int[]{1, 3, 4, 2};

        System.out.println(nextGreaterElement(a, b));

    }
}
