package com.lucky.code;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/9/25 14:59
 * @Description: 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * <p>
 * 示例:
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * <p>
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

public class TwoSumCode {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> numMap = new HashMap(nums.length);

        for (int i = 0; i < nums.length; i++) {
            numMap.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int source = target - nums[i];
            if (numMap.containsKey(source)&& numMap.get(source)!=i) {
                return new int[]{i, (int) numMap.get(source)};
            }
        }

        throw new IllegalArgumentException("No two sum solution");

    }

    public static void main(String[] args) {
        int[] target = new int[]{3, 2, 4};
        System.out.println(twoSum(target,6));
    }
}


