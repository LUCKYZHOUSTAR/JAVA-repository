package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou 前提：数组是有序的数组
 * 参考该博客信息
 * http://www.cnblogs.com/luoxn28/p/5767571.html
 * @Description:二分查找
 * @Date:Create in 11:17 2017/9/5
 */
public class BinarySearch {


    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7};
        System.out.println(binarySearch2(array, 7));
    }

    /**
     * 前提条件是有序的数组，进行查找该值的位置
     * 7
     * [1,2,3,4,5,6,7,8]
     * [1,2,3,4]-[5,6,7,8]
     * [6,7,8]
     * [6,7]-[8]
     * [1,2,3,4]
     * [1,2] [3]
     *
     * @param key
     * @param a
     * @return
     */
    public static int rank(int key, int[] a) {
        //二分查询，进行不断的拆分该数组
        //数组的长度
        int length = a.length - 1;
        int mid = length / 2;
        //判断的标志信息,这样写有问题，不是二分查找，
        while (mid >= 0 && mid < a.length) {
            //这样写有问题，不是二分查询，只进行了一次拆分后面并没有进行查分
            if (key < a[mid]) mid = mid - 1;
            else if (key > a[mid]) mid = mid + 1;
            else return mid;
        }
        return -1;
    }


    //正确的写法，采取基准点，引入变量操作
    public static int rank4(int key, int[] a) {
        //二分查询，进行不断的拆分该数组
        //数组的长度
        int length = a.length - 1;
        //基准点
        int ho = 0;
        //判断的标志信息,这样写有问题，不是二分查找，
        while (ho <= length) {
            //这样写有问题，不是二分查询，只进行了一次拆分后面并没有进行查分
            int mid = ho + (length - ho) / 2;
            if (key < a[mid]) {
                length = mid - 1;

            } else if (key > a[mid]) {
                ho = mid + 1;

            } else return mid;
        }
        return -1;
    }


    public static int rank3(int key, int[] a) {
        //二分查询，进行不断的拆分该数组
        //数组的长度
        int length = a.length - 1;

        int ho = 0;
        int hi = a.length - 1;
        //判断的标志信息,这样写有问题，不是二分查找，
        while (ho <= hi) {
            //需要在循环里面每次进行拆分,这样写也有问题，得加上一个标志位
            int mid = ho + (hi - ho) / 2;
            //这样写有问题，不是二分查询，只进行了一次拆分后面并没有进行查分
            if (key < a[mid]) hi = hi - 1;
            else if (key > a[mid]) ho = mid + 1;
            else return mid;
        }
        return -1;
    }


    public static int rank2(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }


    /////////////////////////////////////////练习//////////////////////////////
    public static int binarySearch(int key, int[] a) {

        //前提是一个排好序的数组
        //每次都是折半，一半的折半进行查询
        //思路很重要，逻辑思维很重要
        //先折半，比较中间的值，然后看在哪一个区域，继续把剩下的区域进行折半,一直折半到1为终结，所以这就是一个变化的常量
        int node = 0;
        int temp = a.length - 1;
        for (int mid = a.length / 2; mid > 0; mid /= 2) {
            //内部进行判断
            //如果是左半部分，起始+(length-mid)/
            //找到中间的值
            int ho = node + (temp - node) / 2;
            if (a[ho] > key) {
                //在左半部分
                temp = temp - 1;
            } else if (a[ho] < key) {
                //在有部分
                node = ho + 1;
            } else return mid;
        }
        return -1;
    }


    public static int binarSearch(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (array[mid] > key) right = mid - 1;
            else if (array[mid] < key) left = mid + 1;
            else return mid;
        }

        return -1;
    }


    public static int binarySearch2(int[] a, int key) {
        //进行折半模拟，通过坐标来进行位移操作
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (a[mid] > key) {
                //数据在左表，因此右边的指针变为mid向前移动一个点
                right = mid - 1;
            } else if (a[mid] < key) {
                //数据在右表，因此坐标的指针变为mid向后移动一个点
                left = mid + 1;
            } else {
                return mid;
            }

        }

        return -1;
    }

    public static int largest_prime_factor(int n) {
        if (n < 1) {//
            return -1;
        }
        if (n == 1) {//判断边界条件
            return 1;
        }
        while (n > 1) {
            for (int i = 2; i <= n; i++) {
                if (n == i) {//到达n了，就没有继续的必要了，已经最大
                    return n;
                }
                if (n % i == 0) {//
                    n = n / i;
                    break;
                }
            }
        }

        return n;
    }
}
