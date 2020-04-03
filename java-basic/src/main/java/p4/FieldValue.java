package p4;

public class FieldValue {

    public static void main(String[] args) {
        System.out.println((0 + 15) / 2);
        System.out.println(true && false || true && true);

        System.out.println(1 + 2 + 3 + 4.0);
        System.out.println(4.1 >= 4);
        int f = 0;
        int g = 1;
        for (int i = 0; i <= 15; i++) {
            System.out.println(f);
            f = f + g;
            g = f - g;
        }

        System.out.println('b');
        System.out.println('b' + 'c');
        System.out.println((char) ('a' + 4));


//            StdOut.println(N + " " + F(N));
        for (int N = 0; N < 100; N++)
            System.out.println(N + " " + Fib(N));

        String s = "";
        for (int n = 100; n > 0; n /= 2)
            s = (n % 2) + s;

        System.out.println(s);

        //1100100
        int t = 100;
        System.out.println(t & 1023);
    }


    public static long F(int N) {
        if (N == 0) return 0;
        if (N == 1) return 1;
        return F(N - 1) + F(N - 2);
    }

    public static long Fib(int N) {
        long[] f = new long[N + 1];
        return Fib(N, f);
    }

    public static long Fib(int N, long[] f) {
        if (f[N] == 0) {
            if (N == 1)
                f[N] = 1;
            else if (N > 1)
                f[N] = Fib(N - 1, f) + Fib(N - 2, f);
        }

        return f[N];
    }


}
