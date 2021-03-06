package com.atguigu.tree;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HeapSort {

    public static void main(String[] args) {

        int[] arr1 = new int[]{4, 6, 8, 5, 9};

        heapSort(arr1);


        //要求将数组进行升序排序
        //int arr[] = {4, 6, 8, 5, 9};
        // 创建要给80000个的随机的数组
        int[] arr = new int[8000000];


        for (int i = 0; i < 8000000; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
        }

        System.out.println("排序前");
        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        heapSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序前的时间是=" + date2Str);
        //System.out.println("排序后=" + Arrays.toString(arr));
    }


    public static void heapSort2(int arr[]) {

        int temp = 0;
        System.out.println("堆排序");

        //先从lengt/2-1的地方找到第一个非叶子结点进行调整，从而完成第一个流程的大顶堆的流程
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }


        for (int j = arr.length - 1; j > 0; j--) {

            //上面第一波已经找到了最大的数值，与底部的数据进行交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;

            //交换完成后，节点的数据量减少，进行第二次循环
            adjustHeap(arr, 0, j);

        }

    }


    public static void adjustHeap2(int arr[], int i, int lenght) {

        int temp = arr[i];//先取出当前元素的值，保存在临时变量
        //k=i*2+1，代表的是k是i结点的左子节点，继续向下左子节点进行调整，因为每次调整完后，树还需要调整
        for (int k = i * 2 + 1; k < lenght; k = k * 2 + 1) {

            //代表的是左子节点的值晓宇右子结点的值
            if (k + 1 < lenght && arr[k] < arr[k + 1]) {

                k++;//k指向右子节点
            }

            //如果子节点大于父节点
            if (arr[k] > temp) {
                arr[i] = arr[k];//发生交换
                i = k;//k指向发生变更
            } else {
                break; //由于是从左至右，从下至上进行调整，所以这块就可以停止了

            }
        }

        //将当前指向的节点，赋值为temp，中间的过程都已经赋值了
        arr[i] = temp;
    }

    //编写一个堆排序的方法
    public static void heapSort(int arr[]) {
        int temp = 0;
        System.out.println("堆排序!!");

//		//分步完成
//		adjustHeap(arr, 1, arr.length);
//		System.out.println("第一次" + Arrays.toString(arr)); // 4, 9, 8, 5, 6
//
//		adjustHeap(arr, 0, arr.length);
//		System.out.println("第2次" + Arrays.toString(arr)); // 9,6,8,5,4

        //完成我们最终代码
        //将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

		/*
		 * 2).将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
　　			3).重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序。
		 */
        for (int j = arr.length - 1; j > 0; j--) {
            //交换
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr, 0, j);
        }

        //System.out.println("数组=" + Arrays.toString(arr));

    }

    //将一个数组(二叉树), 调整成一个大顶堆

    /**
     * 功能： 完成 将 以 i 对应的非叶子结点的树调整成大顶堆
     * 举例  int arr[] = {4, 6, 8, 5, 9}; => i = 1 => adjustHeap => 得到 {4, 9, 8, 5, 6}
     * 如果我们再次调用  adjustHeap 传入的是 i = 0 => 得到 {4, 9, 8, 5, 6} => {9,6,8,5, 4}
     *
     * @param arr    待调整的数组
     * @param i      表示非叶子结点在数组中索引
     * @param lenght 表示对多少个元素继续调整， length 是在逐渐的减少
     */
    public static void adjustHeap(int arr[], int i, int lenght) {

        int temp = arr[i];//先取出当前元素的值，保存在临时变量
        //开始调整
        //说明
        //1. k = i * 2 + 1 k 是 i结点的左子结点
        for (int k = i * 2 + 1; k < lenght; k = k * 2 + 1) {
            if (k + 1 < lenght && arr[k] < arr[k + 1]) { //说明左子结点的值小于右子结点的值
                k++; // k 指向右子结点
            }
            if (arr[k] > temp) { //如果子结点大于父结点
                arr[i] = arr[k]; //把较大的值赋给当前结点
                i = k; //!!! i 指向 k,继续循环比较
            } else {
                break;//!
            }
        }
        //当for 循环结束后，我们已经将以i 为父结点的树的最大值，放在了 最顶(局部)
        arr[i] = temp;//将temp值放到调整后的位置
    }


    //调整以i为节点的大顶堆
    public static void adjustHeapV4(int arr[], int i, int length) {

        //先取出当前元素，保存在临时变量中
        int temp = arr[i];

        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {

            //在有右子节点，并且右边节点大于左边节点的时候
            if (k + 1 < length & arr[k] < arr[k + 1]) {

                k++;
            }

            if (arr[k] > temp) {

                arr[i] = arr[k];//把较大的值，赋给当前节点
                i = k;//i指向k，继续循环比较,继续从k开始调整大小
            } else {
                break;

            }
        }

        //最后把该位置的值，调换为temp
        arr[i] = temp;
    }

}
