package cmc.lucky.algorithm;

/**
 * for(a;b;c)
 * 执行顺序
 * 先执行a
 * 在判断b是否为真，若为真
 * 执行循环体，
 * 执行c
 * 然后再次判断b是否为真，若为真
 * 执行循环体
 * 执行c
 * 。。。
 * 直到b为假，跳出循环
 *
 * @Author:chaoqiang.zhou
 * @Description:冒泡排序算法
 * @Date:Create in 15:57 2017/9/21
 */
public class BubbleSort {


    /**
     * 基本原理：
     * 原理是临近的数字两两进行比较,按照从小到大或者从大到小的顺序进行交换,
     * <p>
     * 这样一趟过去后,最大或最小的数字被交换到了最后一位,
     * <p>
     * 然后再从头开始进行两两比较交换,直到倒数第二位时结束,其余类似看例子
     */


    public static void bubbleSort(int[] a) {

        for (int i = 0; i < a.length - 1; i++) { //最多做n-1趟排序
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] < a[j + 1]) { //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                    //第一趟就把最小的元素交换到了最后面，所以里面的范围是一直在做减少的操作
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 例如数组的元素长度是1，元素有两个个分别是0和1
     *
     * @param a
     */
    public static void bubbleSort2(int[] a) {
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1 - i; j++) {//里面的范围是在一直的减少操作，因为每一次的交换，都可以得到一个数字放置到最后
                //内部从0开始一直的做比较的操作
                int temp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = temp;
            }
        }
    }


    public static void main(String[] args) {
        /**
         * 给定一个数组，从数组的第二位进行比对，插入操作
         */
        int[] a = new int[]{9, 3, 3, 6, 6, 1, 2, 8};
        System.out.println("排序之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

        System.out.println();
        System.out.println("排序之后：");
        bubbleSort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

    }

}


