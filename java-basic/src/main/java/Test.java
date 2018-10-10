/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午4:52 2018/10/9
 */
public class Test {


  private void insertSort(int[] a) {
    System.out.println("———————————————————直接插入排序算法—————————————————————");
    int n = a.length;
    int i, j;
    for (i = 1; i < n; i++) {
      /**
       * temp为本次循环待插入有序列表中的数
       */
      int temp = a[i];
      /**
       * 寻找temp插入有序列表的正确位置
       */
      for (j = i - 1; j >= 0 && compare(a[j], temp); j--) {
        /**
         * 元素后移，为插入temp做准备
         */
        a[j + 1] = a[j];
      }
      /**
       * 插入temp
       */
      a[j + 1] = temp;
    }
  }


  /***
   * 比较
   * @param a
   * @param b
   */
  private boolean compare(int a, int b) {
    String str1 = String.valueOf(a);

    String str2 = String.valueOf(b);

    int length = str1.length() + str2.length();

    String temp = str1;
    str1 =str1+str2;
    str2 = str2 + temp;

    for (int i = 0; i < length; i++) {

      if (Integer.parseInt(str1.substring(i, i + 1)) > Integer.parseInt(str2.substring(i, i + 1))) {
        return true;
      }
      if (Integer.parseInt(str1.substring(i, i + 1)) < Integer.parseInt(str2.substring(i, i + 1))) {
        return false;
      }
    }
    return true;

  }


  private static void printResult(int[] a, int n) {
    System.out.print("最终排序结果：");
    for (int j = 0; j < n; j++) {
      System.out.print(" " + a[j]);
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int a[] = {31, 567, 58, 7, 2, 4, 9, 6};
    new Test().insertSort(a);

    printResult(a, a.length);
  }

}
