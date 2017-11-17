package basic.function;

/**
 * @Author:chaoqiang.zhou
 * @Description:Java 8允许我们给接口添加一个非抽象的方法实现，只需要使用 default关键字即可，这个特征又叫做扩展
 * @Date:Create in 14:00 2017/11/17
 */
public interface Formula {

    double calculate(int a);


    default double spart(int a) {
        return Math.sqrt(a);
    }


    public static void main(String[] args) {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return 0;
            }
        };
    }
}
