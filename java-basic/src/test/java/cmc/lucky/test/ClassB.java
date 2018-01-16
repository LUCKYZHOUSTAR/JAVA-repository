package cmc.lucky.test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:11 2018/1/10
 */
public class ClassB extends ClassA {

    static int i = 1;

    static {
        System.out.println("class b1:static blocks" + i);
    }

    int j = 1;

    static {
        i++;
        System.out.println("class B2:STATIC:BLOCK" + i);
    }

    public ClassB() {
        super();
        i++;
        j++;
        System.out.println("consutor b:i:" + i + "j:" + j);
    }


    {
        i++;
        j++;
        System.out.println("class B common block:i:" + i + "j:" + j);
    }


    public void bDisplay() {
        i++;
        System.out.println("class B:static blocks" + i + "j:" + j);
    }

    public static void bTest() {
        i++;
        System.out.println("class B:static blocks" + i);

    }

    public static void main(String[] args) {
        ClassB a = new ClassB();
        a.bDisplay();
    }
}
