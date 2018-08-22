package cmc.lucky.interview;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午3:28 2018/8/22 以分为单位
 */
public class RedPacket {


  private static final int MIN_MONEY = 1;

  private static final int MAX_MONEY = 200 * 100;


  private static final int LESS = -1;


  private static final int MORE = -2;


  private static final int OK = 1;


  private static final double TIMES = 2.1F;


  private int recursiveCount = 0;


  private int checkMoney(int lastMoney, int count) {

    double avg = lastMoney / count;
    if (avg < MIN_MONEY) {
      return LESS;
    }
    if (avg > MAX_MONEY) {
      return MORE;
    }
    return OK;
  }


}
