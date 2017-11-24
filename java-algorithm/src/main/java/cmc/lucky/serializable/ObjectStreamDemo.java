package cmc.lucky.serializable;

import java.io.*;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 16:22 2017/9/26
 */
public class ObjectStreamDemo {
    public static void main(String[] args) {
//        writeObj();
        readObj();
    }
    public static  void writeObj()
    {
        Student s=new Student();
        s.setId(8);
        s.setName("张三");
        try {
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("d:\\obj.txt"));
            oos.writeObject(s);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static  void  readObj()
    {
        try {
            ObjectInputStream ooi=new ObjectInputStream(new FileInputStream("d:\\obj.txt"));
            try {
                Object obj=ooi.readObject();
                Student s=(Student)obj;
                //person s=(person)obj;
                System.out.println("id:"+s.getId()+",name:"+s.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ooi.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
