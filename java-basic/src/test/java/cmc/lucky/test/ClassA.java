package cmc.lucky.test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:09 2018/1/10
 */
public class ClassA {

    static int i=1;
    static{
        System.out.println("class A:static blocks"+i);
    }

    int j=1;

    static{
        i++;
        System.out.println("class A:static blocks"+i);
    }

    public ClassA(){
        i++;
        j++;
        System.out.println("class A:static blocks"+i+"j:"+j);

    }

    public void bDisplay(){
        i++;
        System.out.println("class A:static blocks"+i+"j:"+j);
    }

    public static void bTest(){
        i++;
        System.out.println("class A:static blocks"+i);

    }
}
