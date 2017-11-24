package cmc.lucky.algorithm;

/**
 * @Author:chaoqiang.zhou
 * @Description:递归发
 * @Date:Create in 19:17 2017/9/12
 */
public class Recursion {


    /**
     * 求某个数的阶乘
     * 每一个递归调用通过进一步调用自己来记住这次递归的过程，当其中调用
     * 满足终止条件的时候，递推就结束了
     *
     * @return
     */
    public static int factorial(int n) {
        if (n < 0) {
            return 0;
        }

        return n * factorial(n - 1);
    }


    /**
     * 如果一个函数中所有递归形式的调用都出现在函数的末尾，我们称这个递归函数是尾递归的。
     * 尾递归就是从最后开始计算, 每递归一次就算出相应的结果, 也就是说, 函数调用出现在调用者函数的尾部, 因为是尾部, 所以根本没有必要去保存任何局部变量. 直接让被调用的函数返回时越过调用者, 返回到调用者的调用者去.
     *
     * @param n if (n == 1 || n == 0) {
     *          return 1;
     *          }
     * @return
     */
    public static int tailFactorial(int n, int a) {
        if (n < 0) {
            return 0;
        }

        if (n == 0) {
            return 1;
        }

        if (n == 1) {
            return a;
        }
        return tailFactorial(n - 1, n * a);
    }


    public static int facttail(int n, int a) {

    /*Compute a factorialina tail - recursive manner.*/

        //也就是不需要栈里面来保存那么多的临时变量，节省了栈的开销,有点类似，从后向前推，每次都计算出结果
        //a，其实在这里就是最后的结果
        if (n < 0)
            return 0;
        else if (n == 0)
            return 1;
        else if (n == 1)
            return a;
        else
            return facttail(n - 1, n * a);

    }


    /**
     * 对整数进行分解因数操作
     *
     * @param n
     */
    public static void maxFactor(int n) {
        //最小的分解因数
        int min = 2;
        while (min <= n) {
            //m其实一直在做减少的操作
            if (min == n) {
                System.out.println(min);
                break;
            } else if (n % min == 0) {
                //如果能整除的话，就找到一个分解的因子
                System.out.println(min);
                //对n进行缩小操作
                n = n / min;
            } else {
                //继续下一个操作
                min++;
            }
        }
    }


    public static int maxFactor(int n, String max) {
        int min = 2;

        while (min <= n) {
            if (n == min) {
                //循环到最后了
                return min;
            } else if (n % min == 0) {
                //能呗整除
                n = n / min;
                //缩小后继续
            } else {
                min++;
            }
        }

        return min;
    }

    /**
     * 采用递归的形式，进行分解因数
     * 分解质因数，后面会越来越大
     *
     * @param n
     */
    public static void maxFactor(int n, int min) {
        if (n < 2) return;
        while (!(n % min == 0)) {
            min++;
        }
        System.out.println(min);
        maxFactor(n / min, min);
    }


    /**
     * 调和级数计算操作
     *
     * @param n
     * @return
     */
    public static int series(int n) {

        if (n == 1) {
            return 1;
        }
        return series(n - 1) + 1 / n;
    }


    /**
     * 尾递归
     * H(N,A)==
     * a+1;
     * H(n-1,a+1/n)
     * 通过增加一个参数a来记录当前递归中目前为止已经计算的数列之和。最终就形成了尾递归版本
     *
     * @param n
     * @param a
     * @return
     */
    public static int series(int n, int a) {
        return 0;
    }


    public static void main(String[] args) {
//        maxFactor(12);
//        maxFactor(12, 2);
        System.out.println(maxFactor(12, ""));
    }
}
