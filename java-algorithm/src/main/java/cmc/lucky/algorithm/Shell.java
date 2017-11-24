package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou
 * @Description:希尔排序 http://blog.csdn.net/morewindows/article/details/6668714
 * 分组插入排序，缩小增量排序
 * 直接插入排序，每次只能将数据移动一位，而希尔是首先进行分组，然后利用插入排序进行排序
 * 该方法的基本思想是：先将整个待排元素序列分割成若干个子序列（由相隔某个“增量”的元素组成的）分别进行直接插入排序，
 * 然后依次缩减增量再进行排序，待整个序列中的元素基本有序（增量足够小）时，
 * 再对全体元素进行一次直接插入排序。因为直接插入排序在元素基本有序的情况下（接近最好情况），效率是很高的，
 * 因此希尔排序在时间效率上比前两种方法有较大提高。
 * <p>
 * 希尔适合于，数组逆序很乱，这样的话，直接插入排序，只能一个一个交换，一点一点的位移而此时用希尔排序的话，可以一大截的，就就行到逆序的位置上
 * 尤其是当最小的一个数字，在最后的时候
 * @Date:Create in 15:13 2017/9/5
 */
public class Shell {


    /**
     * 以n=10的一个数组49, 38, 65, 97, 26, 13, 27, 49, 55, 4为例
     * <p>
     * 第一次 gap = 10 / 2 = 5
     * <p>
     * 49   38   65   97   26   13   27   49   55   4
     * <p>
     * 1A                                        1B
     * <p>
     * 2A                                         2B
     * <p>
     * 3A                                         3B
     * <p>
     * 4A                                          4B
     * <p>
     * 5A                                         5B
     * <p>
     * 1A,1B，2A,2B等为分组标记，数字相同的表示在同一组，大写字母表示是该组的第几个元素， 每次对同一组的数据进行直接插入排序。即分成了五组(49, 13) (38, 27) (65, 49)  (97, 55)  (26, 4)这样每组排序后就变成了(13, 49)  (27, 38)  (49, 65)  (55, 97)  (4, 26)，下同。
     * <p>
     * 第二次 gap = 5 / 2 = 2
     * <p>
     * 排序后
     * <p>
     * 13   27   49   55   4    49   38   65   97   26
     * <p>
     * 1A             1B             1C              1D            1E
     * <p>
     * 2A               2B             2C             2D              2E
     * <p>
     * 第三次 gap = 2 / 2 = 1
     * <p>
     * 4   26   13   27   38    49   49   55   97   65
     * <p>
     * 1A   1B     1C    1D    1E      1F     1G    1H     1I     1J
     * <p>
     * 第四次 gap = 1 / 2 = 0 排序完成得到数组：
     * <p>
     * 4   13   26   27   38    49   49   55   65   97
     */
    /**
     * 这样是错误的写法，因为每组里面可以大于2个元素，所以要进行直接插入排序算法
     *
     * @param a 数组元素
     * @param n 代表的是步长
     */
    public static void shell1(int[] a, int n) {
        //步长每次缩短一半
        while (n > 0) {
            n = n / 2;
            for (int j = 0; j < n; j++) {
                //进行交换排序
                if (a[j] > a[j + n]) {
                    int temp = a[j];
                    a[j] = a[j + n];
                    a[j + n] = temp;
                }
            }
        }
    }


    public static void shell(int[] a, int n) {
        //步长每次缩短一半
        int length = a.length;

        int gap;
        int z = 0;
        for (gap = n / 2; gap > 0; gap /= 2) {
            //遍历分组里面的信息操作
            for (z = z + gap; z + gap < length; z++) {
                for (int j = z + gap; j < length; j += gap) {
                    if (a[j] > a[z]) {
                        break;
                    }
                    int temp = a[z];
                    a[z] = a[j];
                    a[j] = temp;
                }
            }
        }
    }


    public static void shellsort1(int a[], int n) {
        int i, j, gap;

        for (gap = n / 2; gap > 0; gap /= 2) //步长
            for (i = 0; i < gap; i++)        //直接插入排序
            {
                for (j = i + gap; j < n; j += gap)
                    if (a[j] < a[j - gap]) {
                        int temp = a[j];
                        int k = j - gap;
                        while (k >= 0 && a[k] > temp) {
                            a[k + gap] = a[k];
                            k -= gap;
                        }
                        a[k + gap] = temp;
                    }
            }
    }


    public static void shellSort5(int a[], int n) {
        int gap, i, j, temp;
        for (gap = n / 2; gap > 0; gap /= 2) {
            for (i = gap; i < n; i++) {
                for (j = i - gap; j >= 0 && a[j] > a[j + gap]; j -= gap) {
                    temp = a[j];
                    a[j] = a[j + gap];
                    a[j + gap] = temp;
                }
            }
        }
    }


    public static void sort(int a[], int n) {
        int N = a.length;
        int h = 1;
        while (h < N / n) {
            h = n * h + 1;
        }
        while (h >= 1) {
            //将数组变为h有序
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && a[j] < a[j - h]; j -= h) {
                    int temp = a[j];
                    a[j] = a[j - h];
                    a[j - h] = temp;
                }
            }
            h = h / 3;
        }
    }


    public static void sort2(int a[], int n) {
        //希尔排序
        int d = a.length;
        while (true) {
            d = d / n;
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < a.length; i = i + d) {
                    int temp = a[i];
                    int j;
                    for (j = i - d; j >= 0 && a[j] > temp; j = j - d) {
                        a[j + d] = a[j];
                    }
                    a[j + d] = temp;
                }
            }
            if (d == 1) {
                break;
            }
        }
    }


    /**
     * 希尔排序最终算法操作
     *
     * @param a
     */
    public static void sort5(int a[]) {

    }


    /**
     * 最终算法
     *
     * @param arr
     * @param n
     */
    private static void shellSort(int[] arr, int n) {
        for (int gap = n; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j, c = arr[i];
                for (j = i; j >= gap && arr[j - gap] > c; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = c;
            }
        }
    }

    /**
     * 最终算法
     *
     * @param arr
     * @param n
     */
    public static void shellSortArray(int[] arr, int n) {
        for (int gap = n; gap > 0; gap /= 2) {
            //数组从0开始，无须在加1了,内部是一个插入排序
            for (int i = gap; i < arr.length; i += 1) {
                //设置标签的值信息
                int temp = arr[i];
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] < arr[i]) {
                        break;
                    }
                    //进行交换设置
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }


    /**
     * 最终算法
     *
     * @param arr
     * @param n
     */
    public static void shellSortArray1(int[] arr, int n) {
        for (int gap = n; gap > 0; gap /= 2) {
            //数组从0开始，无须在加1了,内部是一个插入排序
            for (int i = gap; i < arr.length; i += 1) {
                //设置标签的值信息
                int temp = arr[i];
                for (int j = i - gap; j >= 0 && arr[j] > arr[i]; j -= gap) {
                    //进行交换设置
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }


    public static void shellSortArray2(int[] arr, int n) {
        //步长一直在缩小
        for (int gap = n; gap > 0; gap /= 2) {
            //内部是一个插入排序
            for (int i = gap; i < arr.length; i++) {
                //这块需要是j--的操作
                for (int j = i - gap; j >= 0 && arr[j] > arr[j + gap]; j -= gap) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }


//    public static void shellSortArray3(int[] arr, int n) {
//        //步长一直在缩小
//        for (int gap = n; gap > 0; gap /= 2) {
//            //内部是一个插入排序
//            for (int i = gap; i < arr.length; i++) {
//                //这块需要是j--的操作
//                for (int j = i; j >= gap && arr[j - gap] > arr[i]; j -= gap) {
//                    arr[j] = arr[j - gap];
//                }
//                arr[i] = c;
//            }
//        }
//    }

    public static void main(String[] args) {
        int[] a = new int[]{9, 3, 6, 1, 2, 8};
        System.out.println("排序之前：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }

        System.out.println();
        System.out.println("排序之后：");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }
}
