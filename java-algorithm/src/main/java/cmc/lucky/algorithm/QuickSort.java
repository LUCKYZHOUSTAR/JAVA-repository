package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou
 * @Description:快速排序算法 参考：http://developer.51cto.com/art/201403/430986.htm
 * http://www.jianshu.com/p/3bf70aac0bef
 * 10.68.79.154:8080
 * 因此快速排序的最差时间复杂度和冒泡排序是一样的都是O(N2)，它的平均时间复杂度为O(NlogN)。
 * @Date:Create in 15:20 2017/9/21
 */
public class QuickSort {


    public static void quickSort(int[] a, int left, int right) {
        if (left > right) {
            return;
        }

        int i = left;
        int j = right;
        int temp = a[left];//基准位数，将最左端的数字设置为基准位数

        //开始寻找
        while (i != j) {

            //由于是左端作为基准位数，所以要先从高位开始寻找，因为基准位数调整的时候，要找到比temp更小的数字
            while(i<j&&a[j]>=temp){
                j--;
            }

            while(i<j&&a[i]<=temp){
                i++;
            }

            if(i<j){
                //交换他们的值信息
                //交换彼此的数据
                int tt = a[i];
                a[i] = a[j];
                a[j] = tt;
            }
        }

        //交换基位数据
        int kk = a[i];
        a[i] = temp;
        a[left] = kk;

        //下一次迭代
        quickSort(a, left, i - 1);//左半边
        quickSort(a, j + 1, right);//右半边
    }

    public static void quick_sort(int[] a, int left, int right) {
        //结束迭代
        if (left > right) {
            return;
        }
        int i = left;
        int j = right;

        int temp = a[left];//设置基准值，将最左端元素作为基准值

        while (i != j) {
            //往左移位，直到大于temp
            while (i < j && a[j] >= temp) {
                j--;
            }
            //往右移位，直到小于temp
            while (i < j && a[i] <= temp) {
                i++;
            }
            if (i < j) {
                //交换彼此的数据
                int tt = a[i];
                a[i] = a[j];
                a[j] = tt;
            }

        }

        //交换基位数据
        int kk = a[i];
        a[i] = temp;
        a[left] = kk;

        //下一次迭代
        quick_sort(a, left, i - 1);//左半边
        quick_sort(a, j + 1, right);//右半边


    }


    public static void main(String[] args) {
        /**
         * 给定一个数组，从数组的第二位进行比对，插入操作
         */
        int[] a = new int[]{9, 3, 3, 6, 1, 2, 8, 10};
        System.out.println("排序之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

        System.out.println();
        System.out.println("排序之后：");
        quickSort(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

    }


    /**
     * 对于第二点，需要注意元素查找的先后顺序，以从小到大排序为例：

     如果 基准数选取的为arr[low] ， 那么必须先从高位high查找到小于pivot的数，然后再从低位low寻找大于pivot的数，交换；
     如果 基准数选取的为arr[high] ， 那么必须先从低位low查找到大于pivot的数，然后再从高位high寻找小于pivot的数，交换；
     原因是， 当两侧的查找相遇时候，需要将基准数pivot 和相遇点元素的值交换以正确归位基准数pivot； 也就是pivot = arr[low] 这种情况 相遇点的元素值必须要小于pivot的值，如果先从低位low查找大于pivot的元素，最终停止的元素大于pivot的话 就会导致归位失败；

     作者：holysu
     链接：http://www.jianshu.com/p/3bf70aac0bef
     來源：简书
     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}
