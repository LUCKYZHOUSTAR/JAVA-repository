package com.lucky.test.struct

/**
 * @Author:chaoqiang.zhou
 *
 * @Date:Create in 下午3:33 2018/6/29
 * byte -这是用来表示字节值。例如2。
 short -这是用来表示一个短整型。例如10。
 int -这是用来表示整数。例如1234。
 long -这是用来表示一个长整型。例如10000090。
 float -这是用来表示32位浮点数。例如12.34。
 double -这是用来表示64位浮点数，这些数字是有时可能需要的更长的十进制数表示。例如12.3456565。
 char -这定义了单个字符文字。例如“A”。
 Boolean -这表示一个布尔值，可以是true或false。
 String -这些是以字符串的形式表示的文本。例如，“Hello World”的。
 */
class Example {

    static void main(String[] args) {
        //Example of a int datatype
        int x = 5;

        //Example of a long datatype
        long y = 100L;

        //Example of a floating point datatype
        float a = 10.56f;

        //Example of a double datatype
        double b = 10.5e40;

        //Example of a BigInteger datatype
        BigInteger bi = 30g;

        //Example of a BigDecimal datatype
        BigDecimal bd = 3.5g;

        println(x);
        println(y);
        println(a);
        println(b);
        println(bi);
        println(bd);


        int count = 0
        while (count <= 6) {
            println("hello")
            count++
        }

        for (int i = 0; i < 5; i++) {
            println(i);
        }

        int[] array = [0, 1, 2, 3];

        for (int i in array) {
            println(i);
        }


        int d = 2

        //Check for the boolean condition
        if (a < 100) {
            //If the condition is true print the following statement
            println("The value is less than 100");
        }
    }


    static void test1() {
        String x = "dd"

        println(x)
    }


    static void test2() {
        int a = 2

        //Evaluating the expression value
        switch (a) {
        //There is case statement defined for 4 cases
        // Each case statement section has a break condition to exit the loop

            case 1:
                println("The value of a is One");
                break;
            case 2:
                println("The value of a is Two");
                break;
            case 3:
                println("The value of a is Three");
                break;
            case 4:
                println("The value of a is Four");
                break;
            default:
                println("The value is unknown");
                break;
        }
    }
}
