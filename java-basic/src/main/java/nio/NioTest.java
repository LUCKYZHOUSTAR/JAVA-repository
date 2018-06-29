package nio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author:chaoqiang.zhou
 * @Date:Create in 下午5:01 2018/5/18
 */
public class NioTest {


  public static void method2(){
    InputStream in = null;
    try{
      in = new BufferedInputStream(new FileInputStream("src/nomal_io.txt"));
      byte [] buf = new byte[1024];
      int bytesRead = in.read(buf);
      while(bytesRead != -1)
      {
        for(int i=0;i<bytesRead;i++)
          System.out.print((char)buf[i]);
        bytesRead = in.read(buf);
      }
    }catch (IOException e)
    {
      e.printStackTrace();
    }finally{
      try{
        if(in != null){
          in.close();
        }
      }catch (IOException e){
        e.printStackTrace();
      }
    }
  }

}
