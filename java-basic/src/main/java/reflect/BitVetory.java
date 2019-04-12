package reflect;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午5:07 2018/9/30
 * 一个整形有32位，也就是32个1，因此可以表示从0~31的这么多个数字，比如0就表示00000000000000000000000000000000
 * 1就可以表示0.。。。  1，也就是级就是第几位上是1，因此可以表示32个数字
 * 所以在放置的时候，先求余数，代表在数组的位置，然后求出余数放置即可
 * 那么还原的时候，
 */
public class BitVetory {


  int[] bitset;

  public BitVetory(int size) {
    bitset = new int[size >> 5];  //除以32,分多少组
  }

  boolean get(int pos) {
    int wordN = (pos >> 5); //确定自己的组号
    int bitN = (pos & 0x1F);//得到除以32后的余数
    return (bitset[wordN] & (1 << bitN)) != 0; //如果存在则返回1，不存在返回0
  }

  void set(int pos) {
    int wordN = (pos >> 5);
    int bitN = (pos & 0x1F);
    bitset[wordN] |= 1 << bitN;  //将这位置为1
    System.out.println(1 << bitN);
    System.out.println(bitset[0]);
  }


  public static void main(String[] args) {
    BitVetory bitVetory = new BitVetory(64);
    bitVetory.set(32);
    System.out.println(bitVetory.get(32));
    bitVetory.set(33);
    System.out.println("sadf ");
    System.out.println(35/ 32);
    System.out.println(35&31);
  }
}
