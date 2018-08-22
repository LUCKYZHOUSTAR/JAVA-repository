package cmc.lucky.interview;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午4:36 2018/8/22
 */
public class SortTest {


  public static void print(int a[]) {
    for (int i = 0; i < a.length; i++) {
      System.out.println(a[i]);
    }
  }

  /**
   * 选择排序 两层遍历，第一圈寻找最小的，与第一个交换，第二圈接着寻找
   */
  public static void selectSort(int a[]) {
    for (int i = 0; i < a.length; i++) {
      int min = i;

      for (int j = i + i; j < a.length; j++) {
        if (a[j] < a[i]) {
          min = j;
        }
      }
      //找到最小的值后，进行交换
      int temp = a[min];
      a[min] = a[i];
      a[i] = temp;
    }
  }


  /**
   * 冒泡排序 两两交换 冒泡排序总的平均时间复杂度为：O(n2)
   */
  public static void bubbleSort(int a[]) {
    for (int i = 0; i < a.length - 1; i++) {
      for (int j = 0; j < a.length - 1 - i; j++) {
        if (a[j] > a[j + 1]) {
          int tmp = a[j + 1];
          a[j + 1] = a[j];
          a[j] = tmp;
        }
      }
    }
  }


  /**
   * /** 3 3 5==>   3 1 5==>1 3 5 1 3 5 1 3 5 7 1 3 4 5 7 1 3 4 5 6 7 1 3 4 5 6 7 9
   * 做算法，后面的值操作，从1开始传递进去一个数字即可 时间复杂度：O (n^2)
   */
  public static void insertSorter(int[] a) {
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


  public static void quickSort(int[] a, int left, int right) {

    int i, j, t, temp;
    if (left > right) {
      return;
    }

    temp = a[left]; //temp中存的就是基准数
    i = left;
    j = right;
    while (i != j) {
      //顺序很重要，要先从右边开始找
      while (a[j] >= temp && i < j) {
        j--;
      }
      //再找右边的
      while (a[i] <= temp && i < j) {
        i++;
      }
      //交换两个数在数组中的位置
      if (i < j) {
        t = a[i];
        a[i] = a[j];
        a[j] = t;
      }
    }
    //最终将基准数归位
    a[left] = a[i];
    a[i] = temp;

    quickSort(a, left, i - 1);//继续处理左边的，这里是一个递归的过程
    quickSort(a, i + 1, right);//继续处理右边的 ，这里是一个递归的过程
  }

  public static void main(String[] args) {
    int a[] = new int[]{3, 4, 5, 6, 7, 8};

    quickSort(a, 1, a.length-1);
    print(a);
  }
}
