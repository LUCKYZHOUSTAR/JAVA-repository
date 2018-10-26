package structure;

import java.util.Arrays;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午1:49 2018/10/11
 */
public class Solution {


  /**
   * 题目大意
   *
   * 　　给定一个整数数组，找出其中两个数满足相加等于你指定的目标数字。 　　要求：这个函数twoSum必须要返回能够相加等于目标数字的两个数的索引，且index1必须要小于index2。请注意一点，你返回的结果（包括index1和index2）都不是基于0开始的。你可以假设每一个输入肯定只有一个结果。
   *
   *
   *
   *
   * 解题思路
   *
   * 　　创建一个辅助类数组，对辅助类进行排序，使用两个指针，开始时分别指向数组的两端，看这两个下标对应的值是否等于目标值，如果等于就从辅助类中找出记录的下标，构造好返回结果，返回。如果大于就让右边的下标向左移，进入下一次匹配，如果小于就让左边的下标向右移动，进入下一次匹配，直到所有的数据都处理完
   *
   *
   *
   *
   * 代码实现
   *
   * --------------------- 作者：derrantcm 来源：CSDN 原文：https://blog.csdn.net/derrantcm/article/details/46905233?utm_source=copy
   * 版权声明：本文为博主原创文章，转载请附上博文链接！
   */
  private static class Node implements Comparable<Node> {


    int val; //值
    int idx;//值对应的数组的下标

    @Override
    public int compareTo(Node o) {
      if (o == null) {
        return -1;
      }
      return this.val - o.val;
    }

    public Node() {
    }

    public Node(int val, int idx) {
      this.val = val;
      this.idx = idx;
    }
  }


  /***
   * 查找数组中满足两个数想加的等于target的坐标
   * @param nums
   * @param target
   * @return
   */
  public int[] twoSum(int[] nums, int target) {
    int[] result = {0, 0};

    //辅助的数组
    Node[] tmp = new Node[nums.length];

    for (int i = 0; i < nums.length; i++) {
      tmp[i] = new Node(nums[i], i);
    }

    //对元素进行排序
    Arrays.sort(tmp);

    int lo = 0;
    int hi = nums.length - 1;
    //通过两个指针来操作
    while (lo < hi) {
      if (tmp[lo].val + tmp[hi].val == target) {
        if (tmp[lo].idx > tmp[hi].idx) {
          result[0] = tmp[hi].idx + 1;
          result[1] = tmp[lo].idx + 1;
        } else {
          result[0] = tmp[lo].idx + 1;
          result[1] = tmp[hi].idx + 1;
        }
        break;
      } else if (tmp[lo].val < tmp[hi].val) {
        hi--;
      } else {
        lo++;
      }
    }

    return result;

  }


}
