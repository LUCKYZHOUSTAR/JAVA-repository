package com.lucky.code.heap;

import java.util.*;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/10/3 16:39
 * @Description: 1、求第 K 大元素都可以通过维护容量为 K 的小顶堆来实现
 * <p>
 * 2、先建立大小固定的小顶堆，每次往堆中添加数据前先判断堆是否已满，未满则放至堆底，再进行「自底向上」的堆化。
 * <p>
 * 3、若堆空间已满，则判断新元素是否大于堆顶，否则无需对堆进行操作。
 * <p>
 * 4、若新元素大于堆顶，则先移除堆顶元素，对堆进行「自顶向下」的 堆化。再将新元素插入执行步骤 2.
 */
public class KthLargest703 {
    //数组维护前K大的元素
    static class KthLargest {

        //将K个数按从小到大放在集合中
        private List<Integer> list;
        private int indexK;

        public KthLargest(int k, int[] nums) {


            list = new ArrayList<Integer>(k);
            indexK = k;

            Arrays.sort(nums);

            int i = 0;
            for (; i < k && i < nums.length; i++) {
                list.add(nums[nums.length - 1 - i]);
            }

            while (i++ < k) {
                list.add(Integer.MIN_VALUE);
            }

            //对集合中的数从小到大排序
            Collections.sort(list);

        }

        public int add(int val) {

            //如果插入的值比最小的值小,就直接返回第K个数(即第一个数)
            if (val < list.get(0)) {
                return list.get(0);
            } else {
                list.remove(0);
                list.add(val);
            }

            Collections.sort(list);

            return list.get(list.size() - indexK);
        }
    }

    //使用小顶堆维护前K大的元素
    static class KthLargest1 {

        //最小堆
        private int[] minHeap;
        private int k;

        public KthLargest1(int k, int[] nums) {

            this.k = k;
            minHeap = new int[k];
            Arrays.sort(nums);

            int i = 0;
            for (; i < k && i < nums.length; i++) {
                minHeap[i] = nums[nums.length - 1 - i];
            }

            //当数组不足以填充K个元素时,使用int的最小数填充满
            while (i < k) {
                minHeap[i++] = Integer.MIN_VALUE;
            }

            //生成堆
            generaterMinHeap();
        }

        //生成堆
        private void generaterMinHeap() {

            //从最后一个非叶子节点依次往前调整数
            for (int i = k / 2 - 1; i >= 0; i--) {
                autoAdjustMinHeap(i, k);
            }
        }

        private void autoAdjustMinHeap(int node, int lenth) {

            int left = node * 2 + 1;
            int right = left + 1;

            if (left > lenth - 1) {
                return;
            }

            int min = left;
            if (right > lenth - 1) {
                min = left;
            } else {
                if (minHeap[min] > minHeap[right]) {
                    min = right;
                }
            }

            if (minHeap[min] > minHeap[node]) {
                return;
            }

            int t = minHeap[node];
            minHeap[node] = minHeap[min];
            minHeap[min] = t;

            autoAdjustMinHeap(min, lenth);
        }


        public int add(int val) {

            if (val < minHeap[0]) {
                return minHeap[0];
            }

            minHeap[0] = val;
            autoAdjustMinHeap(0, k);
            return minHeap[0];

        }
    }

    //用Java自带的优先队列实现
    static class KthLargest2 {

        private PriorityQueue<Integer> minHeap;
        private int k;

        public KthLargest2(int k, int[] nums) {

            this.k = k;
            minHeap = new PriorityQueue<Integer>(k);

            for (int i = 0; i < nums.length; i++) {
                add(nums[i]);
            }

        }

        public int add(int val) {

            if (minHeap.size() < k) {
                minHeap.offer(val);
            } else if (minHeap.peek() < val) {
                minHeap.poll();
                minHeap.offer(val);
            }

            return minHeap.peek();

        }

    }

    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest(2, new int[]{0});

        System.out.println(kthLargest.add(-1));
        System.out.println(kthLargest.add(5));
        System.out.println(kthLargest.add(10));
        System.out.println(kthLargest.add(9));
        System.out.println(kthLargest.add(4));
    }

}
