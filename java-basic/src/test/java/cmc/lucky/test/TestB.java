package cmc.lucky.test;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 上午9:53 2018/10/30
 */
public class TestB {


  public static void main(String[] args) {
//    char[] a = new char[]{'a', 'b', 'c', 'd', 'f', 'g'};
//    char[] b = new char[]{'a', 'b', 'c', 'd', 'f'};
//

    char[] a = new char[]{'a', 'b', 'c', 'd', 'e','f'};
    char[] b = new char[]{'a', 'b', 'c', 'd', 'f'};

    int i = 0, j = 0;

    while (i < a.length || (j < b.length)) {
      if (i > a.length - 1) {
        System.out.println(b[j]);
        return;
      }

      if (j > b.length - 1) {
        System.out.println(a[i]);
        return;
      }
      if (a[i] != b[j]) {
        if (a.length > b.length) {
          System.out.println(a[i]);
        } else {
          System.out.println(b[i]);
        }
        break;
      }

      i++;
      j++;
    }

  }
}
