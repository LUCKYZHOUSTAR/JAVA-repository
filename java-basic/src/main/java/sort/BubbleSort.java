package sort;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午5:31 2018/10/9
 */
public class BubbleSort {


  public static int binarySearch(int[] a, int destElement) {
    int begin = 0;
    int end = a.length - 1;

    while (begin <= end) {
      int mid = (begin + end) / 2;
      if (a[mid] == destElement) {
        return mid;
      } else if (a[mid] > destElement) {
        end = mid - 1;
      } else if (a[mid] < destElement) {
        begin = mid + 1;
      }
    }
    return -1;
  }

  public static void bublleSort(int[] numbers) {
    int temp = 0;
    int size = numbers.length;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < size - 1 - i; j++) {
        if (numbers[j] > numbers[j + 1]) {
          temp = numbers[j];
          numbers[j] = numbers[j + 1];
          numbers[j + 1] = temp;
        }
      }
    }
  }

  /***
   *
   * 记住
   * 冒泡排序
   * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
   * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
   * 针对所有的元素重复以上的步骤，除了最后一个。
   * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
   * @param numbers 需要排序的整型数组
   */
  public static void bubbleSort(int[] numbers) {

    int temp = 0;
    int size = numbers.length;
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < size - 1 - i; j++) {
        if (numbers[j] > numbers[j + 1])  //交换两数位置
        {
          temp = numbers[j];
          numbers[j] = numbers[j + 1];
          numbers[j + 1] = temp;
        }
      }
    }
  }


  public static void insertSortt(int[] numbers) {
    int size = numbers.length;
    int temp = 0;
    int j = 0;
    for (int i = 0; i < size; i++) {
      temp = numbers[i];
      for (j = i; j > 0 && temp < numbers[j - 1]; j--) {
        numbers[j] = numbers[j - 1];
      }
      numbers[j] = temp;
    }
  }


  /**
   * 记住 插入排序 时间复杂度：O（n^2）.
   *
   * 从第一个元素开始，该元素可以认为已经被排序 取出下一个元素，在已经排序的元素序列中从后向前扫描 如果该元素（已排序）大于新元素，将该元素移到下一位置
   * 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置 将新元素插入到该位置中 重复步骤2
   *
   * @param numbers 待排序数组
   */
  public static void insertSort(int[] numbers) {

    int size = numbers.length;

    int temp = 0;
    int j = 0;

    /***
     * 外层循环，相当于是寻找插入的元素
     */
    for (int i = 0; i < size; i++) {

      temp = numbers[i];

      //寻找位置
      for (j = i; j > 0 && temp < numbers[j - 1]; j--) {
        //类似于向前移动
        numbers[j] = numbers[j - 1];
      }

      //交互位置上的元素
      numbers[j] = temp;
    }
  }

  /**
   * 希尔排序的原理:根据需求，如果你想要结果从大到小排列，它会首先将数组进行分组，然后将较大值移到前面，较小值 移到后面，最后将整个数组进行插入排序，这样比起一开始就用插入排序减少了数据交换和移动的次数，可以说希尔排序是加强
   * 版的插入排序 拿数组5, 2, 8, 9, 1, 3，4来说，数组长度为7，当increment为3时，数组分为两个序列 5，2，8和9，1，3，4，第一次排序，9和5比较，1和2比较，3和8比较，4和比其下标值小increment的数组值相比较
   * 此例子是按照从大到小排列，所以大的会排在前面，第一次排序后数组为9, 2, 8, 5, 1, 3，4 第一次后increment的值变为3/2=1,此时对数组进行插入排序，
   * 实现数组从大到小排
   */
  public static void shellSort(int[] data) {

    int j = 0;
    int temp = 0;
    //每次将步长缩短为原来的一半
    for (int increment = data.length / 2; increment > 0; increment /= 2) {
      for (int i = increment; i < data.length; i++) {
        temp = data[i];
        for (j = i; j >= increment; j -= increment) {
          if (temp > data[j - increment])//如想从小到大排只需修改这里
          {
            data[j] = data[j - increment];
          } else {
            break;
          }

        }
        data[j] = temp;
      }

    }

  }


  /***
   * 快速排序算法
   * @param a
   * @param low
   * @param high
   */
  public void quickSort(int[] a, int low, int high) {
    int start = low;
    int end = high;
    int key = a[low];

    while (end > start) {
      //从后往前比较
      while (end > start && a[end] >= key)  //如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
      {
        end--;
      }
      if (a[end] <= key) {
        int temp = a[end];
        a[end] = a[start];
        a[start] = temp;
      }
      //从前往后比较
      while (end > start && a[start] <= key)//如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
      {
        start++;
      }
      if (a[start] >= key) {
        int temp = a[start];
        a[start] = a[end];
        a[end] = temp;
      }
      //此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
    }
    //递归
    if (start > low) {
      quickSort(a, low, start - 1);//左边序列。第一个索引位置到关键值索引-1
    }
    if (end < high) {
      quickSort(a, end + 1, high);//右边序列。从关键值索引+1到最后一个
    }
  }


  /**
   * 记住
   */
  public static void quickSort2(int[] arr, int low, int high) {
    int i, j, temp, t;
    if (low > high) {
      return;
    }
    i = low;
    j = high;
    //temp就是基准位
    temp = arr[low];

    while (i < j) {
      //先看右边，依次往左递减
      while (temp <= arr[j] && i < j) {
        j--;
      }
      //再看左边，依次往右递增
      while (temp >= arr[i] && i < j) {
        i++;
      }
      //如果满足条件则交换
      if (i < j) {
        t = arr[j];
        arr[j] = arr[i];
        arr[i] = t;
      }

    }
    //最后将基准为与i和j相等位置的数字交换
    arr[low] = arr[i];
    arr[i] = temp;
    //递归调用左半数组
    quickSort2(arr, low, j - 1);
    //递归调用右半数组
    quickSort2(arr, j + 1, high);
  }


  public static void main(String[] args) {
    int[] array = new int[]{11, 5, 8, 44, 77, 78, 23, 43};
    quickSort2(array, 0, array.length - 1);
    for (int i = 0; i < array.length; i++) {
      System.out.println((i + 1) + "th:" + array[i]);
    }
  }
}


