package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou
 * @Description: 1.运行的时间和输入无关，及时是一个有序的数组，排序的时间与无序的数组一样长
 * 2.数据移动是最少的，每次交换都会改变两个数组元素的值，因此选择排序用了N次交换，交换次数和数组的大小是线性关系。
 * @Date:Create in 14:52 2017/9/5
 */
public class Selection {


    public static void main(String[] args) {
        int[] a = new int[]{9, 3, 6, 1, 2, 8};
        System.out.println("排序之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

        System.out.println();
        System.out.println("排序之后：");
        select4(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    /**
     * 选择排序
     * 1.找到数组中最小的元素的索引，与0位置交互
     * 2.接着找其余部分的索引的数字，与1位置交换
     *
     * @param a
     */
    public static void select(int[] a) {

        for (int i = 0; i < a.length; i++) {
            //内部开始寻找最小的元素的索引
            //假设起始值就是最小值
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                //从i+1开始寻找,如果比i小的话，那么min的索引就是j
                if (a[j] < a[min]) min = j;
            }
            //最后进行交换
            int temp = a[i];
            a[i] = a[min];
            a[min] = temp;
        }
    }


    public static void select2(int[] a) {
        for (int i = 0; i < a.length; i++) {
            //设置最小的标签
            int mid = i;
            for (int j = i + 1; j < a.length; j++) {
                //这块有问题，每次应该与最小值做比较
                if (a[j] < a[mid]) mid = j;
            }
//            int temp = a[mid];
//            a[mid] = a[i];
//            a[i] = temp;
            //最后进行交换
            int temp = a[i];
            a[i] = a[mid];
            a[mid] = temp;
        }
    }


    public static void select3(int[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) min = j;
            }

            int temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
    }


    ////////////////////////////////////练习/////////////////////////////////////////////////////////////////////////////////
    public static void select4(int[] a) {
        //选择排序
        //找到最小的一个，与第一个交换，继续找下一个，与第二个交换


        for (int i = 0; i < a.length; i++) {
            //比对的起始值信息a
            int min = i;
            //遍历后面的数组信息
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[min]) {
                    min = j;
                }

            }
            //找到最小的值后，进行交换
            int temp = a[min];
            a[min] = a[i];
            a[i] = temp;
        }
    }
}
