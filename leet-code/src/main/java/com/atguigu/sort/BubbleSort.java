package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 功能描述:
 *
 * @param: 第一次排序找到第一个最大的树，放置到末尾
 * @return: 第二次排序找到第二个最大的数，放置到第二个末尾
 * @auther: zhou
 * @date: 2019/10/3 下午9:45
 */
public class BubbleSort {

    public static void main(String[] args) {
//		int arr[] = {3, 9, -1, 10, 20};
//		
//		System.out.println("排序前");
//		System.out.println(Arrays.toString(arr));

        //为了容量理解，我们把冒泡排序的演变过程，给大家展示

        //测试一下冒泡排序的速度O(n^2), 给80000个数据，测试
        //创建要给80000个的随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000); //生成一个[0, 8000000) 数
        }

        Date data1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(data1);
        System.out.println("排序前的时间是=" + date1Str);

        //测试冒泡排序
        bubbleSort(arr);

        Date data2 = new Date();
        String date2Str = simpleDateFormat.format(data2);
        System.out.println("排序后的时间是=" + date2Str);

        //System.out.println("排序后");
        //System.out.println(Arrays.toString(arr));


		/*

		// 第二趟排序，就是将第二大的数排在倒数第二位

		for (int j = 0; j < arr.length - 1 - 1 ; j++) {
			// 如果前面的数比后面的数大，则交换
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}

		System.out.println("第二趟排序后的数组");
		System.out.println(Arrays.toString(arr));


		// 第三趟排序，就是将第三大的数排在倒数第三位

		for (int j = 0; j < arr.length - 1 - 2; j++) {
			// 如果前面的数比后面的数大，则交换
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}

		System.out.println("第三趟排序后的数组");
		System.out.println(Arrays.toString(arr));

		// 第四趟排序，就是将第4大的数排在倒数第4位

		for (int j = 0; j < arr.length - 1 - 3; j++) {
			// 如果前面的数比后面的数大，则交换
			if (arr[j] > arr[j + 1]) {
				temp = arr[j];
				arr[j] = arr[j + 1];
				arr[j + 1] = temp;
			}
		}

		System.out.println("第四趟排序后的数组");
		System.out.println(Arrays.toString(arr)); */

    }

    // 将前面额冒泡排序算法，封装成一个方法
    public static void bubbleSort(int[] arr) {
        // 冒泡排序 的时间复杂度 O(n^2), 自己写出
        int temp = 0; // 临时变量
        boolean flag = false; // 标识变量，表示是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {

            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 如果前面的数比后面的数大，则交换
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            //System.out.println("第" + (i + 1) + "趟排序后的数组");
            //System.out.println(Arrays.toString(arr));

            if (!flag) { // 在一趟排序中，一次交换都没有发生过
                break;
            } else {
                flag = false; // 重置flag!!!, 进行下次判断
            }
        }
    }


    public static void bubbleSort6(int[] arr) {
        int temp = 0;
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - j; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if (!flag) { // 在一趟排序中，一次交换都没有发生过
                break;
            } else {
                flag = false; // 重置flag!!!, 进行下次判断
            }
        }
    }


    public static void bubbleSortV2(int[] arr) {

        boolean flag = false;
        int temp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                //如果大的话，两个数就发生交换
                if (arr[j] > arr[j + 1]) {

                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }


                //如果一次都没有交换，则直接推出，冒泡排序的优化点
                if (!flag) {

                    break;

                } else {
                    flag = false;
                }
            }
        }
    }
}
