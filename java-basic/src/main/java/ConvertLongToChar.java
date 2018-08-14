import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午3:45 2018/8/14
 * 一个long是4个字节，也就32位
 * 一个char是两个字节，所以就是16位
 * 两个char可以存放一个long
 * 前16个字节，存放一个long的前16个字节，后16个字节，存放long的后面的字节信息
 */
public class ConvertLongToChar {


  public static void main(String[] args) {
    List<Long> times = new ArrayList<>();

    times.add(345345l);
    times.add(345345345l);

    String result = ConvertLongToChar.convertLongToChar(times);
    System.out.println(result);
    System.out.println(convertCharToLong(result));

    //131071=17个1  & flag=flag，& 有1就是1，否则就是0
    //& ，两个数只要有一个为1则为1，否则就为0。
    System.out.println(131071 & flag);
    System.out.println((131071>>16)&flag);
    System.out.println((1<<16)| flag);
  }
  //65535  二进制就是16个1
  private static char flag = 0xFFFF;

  public static String convertLongToChar(List<Long> numList) {
    try {
      List<Long> copyNumList = new ArrayList<>();//前后两个最大值用于站位,不做逻辑处理
      copyNumList.add(Long.valueOf(Integer.MAX_VALUE));
      copyNumList.addAll(numList);
      copyNumList.add(Long.valueOf(Integer.MAX_VALUE));
      int cell = copyNumList.size();
      char[] src = new char[2 * copyNumList.size()];
      for (int i = 0; i < cell; i++) {
        longToCharArray(copyNumList.get(i), src, 2 * i);
      }
      return new String(src);
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return null;
  }

  public final static char[] longToCharArray(long value, char[] src, int offset) {
    //  1010=10
    src[offset] = (char) (value & flag);
    src[offset + 1] = (char) ((value >> 16) & flag);
    return src;
  }




  public final static List<Long> convertCharToLong(String back) {
    char[] data = new char[back.length()];
    back.getChars(0, data.length, data, 0);

    int cellCount = data.length / 2;

    List<Long> numList = new ArrayList<>();

    for (int i = 1, index = 0; i < cellCount - 1 && index < back.length(); i++, index++) {
      numList.add(charArrayToLong(data, 2 * i));
    }

    return numList;
  }

  /**
   * 提取价格（整数数组）
   */
  public final static long[] getPriceArray(String back, int start, int length) {
    char[] data = new char[back.length()];
    back.getChars(0, data.length, data, 0);

    int cellCount = data.length / 2;

    long[] priceArray = new long[length];

    for (int i = 1 + start, index = 0; i < cellCount && index < length; i++, index++) {
      priceArray[index] = charArrayToLong(data, 2 * i);
    }
    return priceArray;
  }

  public final static long charArrayToLong(char[] src, int offset) {
    long value;
    value = ((src[offset] & flag) | ((src[offset + 1] & flag) << 16));
    return value;
  }

  /**
   * 获取价格和库存字段中的起始日期
   */
  public final static long getStarDaysInt(String str) {
    char[] data = new char[str.length()];
    str.getChars(2, data.length, data, 0);
    return ConvertLongToChar.charArrayToLong(data, 0);
  }
}
