package cmc.lucky.basic.helloword;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:28 2017/10/23
 */
public class Test {

    public static void main(String[] args) {

        System.out.println(isPowerOfTwo(1));
        System.out.println(isPowerOfTwo(3));
        System.out.println(isPowerOfTwo(2));
        System.out.println(isPowerOfTwo(4));
    }

    private static boolean isPowerOfTwo(int val) {
        return (val & -val) == val;
    }
}
