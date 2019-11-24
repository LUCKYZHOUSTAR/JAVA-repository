package com.lucky.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/11/24 22:04
 * @Description:合并区间算法
 */
public class MergeTest {


    //给出一个区间集合，请合并所有的区间信息


    //探针算法，先进行排序，然后遍历，如何前面的与后面有重叠，更新千面区间的结束时间
    int[][] merage(int[][] intervals) {
        Arrays.sort(intervals, (v1, v2) -> Integer.compare(v1[0], v2[0]));

        int[] previous = null;

        List<int[]> result = new ArrayList<>();

        for (int[] current : intervals) {
            if (previous == null || current[0] > previous[1]) {

                result.add(previous = current);
            } else {
                previous[1] = Math.max(previous[1], current[1]);
            }
        }

        return result.toArray(new int[result.size()][]);
    }

    //火星字典---》有向图--》拓扑排序

    //计算器


    //正则表达式
}
