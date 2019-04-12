package reflect;

import java.util.BitSet;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午4:12 2018/9/30
 */
public class BitTest {


  public static void main(String[] args) {
    int a = 2;
    System.out.println("a 非的结果是：" + (~a));
    int c = 7;
    System.out.println(~c);
    System.out.println(~5);

    byte d = 2;


    test();
  }


  public static void test(){
    int[] data={1,2,5,9,11,21,12,15};
    int max = 0;
    for(int i=0;i<data.length;i++){
      if(max < data[i]){
        max = data[i];
      }
    }
    BitSet bm=new BitSet(max+1);
    System.out.println("The size of bm："+bm.size());

    for(int i=0;i<data.length;i++){
      bm.set(data[i], true);
    }

    StringBuffer buf=new StringBuffer();
    buf.append("[");
    for(int i=0;i<bm.size();i++){
      if(bm.get(i) == true){
        buf.append(String.valueOf(i)+" ");
      }
    }
    buf.append("]");
    System.out.println(buf.toString());
  }
}
