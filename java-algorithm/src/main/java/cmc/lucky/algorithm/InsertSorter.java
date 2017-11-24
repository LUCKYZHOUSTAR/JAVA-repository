package cmc.lucky.algorithm;

import java.util.ArrayList;

/**
 * @Author:chaoqiang.zhou
 * @Description:插入排序算法
 * @Date:Create in 18:14 2017/9/4
 */
public class InsertSorter {


    private ArrayList a = new ArrayList();

    /**
     * 3
     * 3 5==>   3 1 5==>1 3 5
     * 1 3 5
     * 1 3 5 7
     * 1 3 4 5 7
     * 1 3 4 5 6 7
     * 1 3 4 5 6 7 9
     * 做算法，后面的值操作，从1开始传递进去一个数字即可
     *
     * @param sorterArray
     */
    public static void InsertSort(int[] sorterArray) {
        //从数组第二位开始比较
        for (int i = 1; i < sorterArray.length; i++) {
            //埋点一,也可以放置到下面
            int temp = sorterArray[i];
            //用这个数字与其余的数字进行交互比较
            for (int j = i - 1; j >= 0; j--) {
                if (temp > sorterArray[j]) {
                    //已经排好序了，无须在排序了
                    break;
                }
                //如何进行交换，内部运用交换排序
                sorterArray[j + 1] = sorterArray[j];
                sorterArray[j] = temp;
            }
        }
    }


    public static void insertSorter(int[] sorterArray) {
        for (int i = 1; i < sorterArray.length; i++) {

            if (sorterArray[i] < sorterArray[i - 1]) {
                //需要进行排序,需要找位置的数字
                int temp = sorterArray[i];
                int j = i;
                while (j > 0 && sorterArray[j - 1] > temp) {
                    sorterArray[j] = sorterArray[j - 1];
                    j--;
                }
                //交换完了
                sorterArray[j] = temp;
            }

        }
    }


    public static void insertSorter2(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int j;
            for (j = i - 1; j >= 0 && a[j] > temp; j--) {
                //其实就是找位置信息
                a[j + 1] = a[j];
            }
            a[j + 1] = temp;
        }

    }


    /**
     * 插入排序
     *
     * @param sorterArray
     */
    public static void InsertSort2(int[] sorterArray) {
        //数组的坐标是从0开始的
        for (int i = 1; i < sorterArray.length; i++) {
            int temp = sorterArray[i];
            int j;
            for (j = i - 1; j >= 0 && sorterArray[j] > temp; j--) {
                sorterArray[j + 1] = sorterArray[j];
            }
            sorterArray[j] = 3;
        }
    }

    /**
     * 1.数组中每个元素距离它的最终位置都不远
     * 2.一个有序的大树组接一个小数组
     * 3.数组中只有几个元素的位置不正确
     *
     * @param args
     */
    public static void main(String[] args) {
        /**
         * 给定一个数组，从数组的第二位进行比对，插入操作
         */
        int[] a = new int[]{9, 3, 6, 1, 2, 8};
        System.out.println("排序之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

        System.out.println();
        System.out.println("排序之后：");
        InsertSort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

    }

}
