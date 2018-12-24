package basic.lambda;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2018/12/24 15:13
 * @Description:https://www.cnblogs.com/pikachu-zhaof/p/9724826.html
 */

import lombok.ToString;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 功能描述:
 *
 * @param: 新的java.util.stream包提供了“支持在流上的函数式风格的值操作”（引用javadoc）的工具。
 * <p>
 * java.util.Stream 表示能应用在一组元素上一次执行的操作序列。Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，而中间操作返回Stream本身，这样你就可以将多个操作依次串起来。活动流Stream 的创建需要指定一个数据源，比如 java.util.Collection的子类，List或者Set， Map不支持。
 * <p>
 * Java 8扩展了集合类，可以通过 Collection.stream() 或者 Collection.parallelStream() 来创建一个串行Stream或者并行Stream。
 * <p>
 * 串行Stream如下所示：
 * <p>
 * Stream<T> stream = collection.stream();
 * <p>
 * <p>
 * 一个流就像一个地带器。这些值“流过”（模拟水流）然后他们离开。一个流可以只被遍历一次，然后被丢弃。流也可以无限使用。
 * <p>
 * 流Stream的操作可以是 串行执行  或者  并行执行。 它们可以使用其中一种方式开始，然后切换到另外的一种方式，使用stream.sequential()或stream.parallel()来达到这种切换。
 * <p>
 * 串行Stream上的操作是在一个线程中依次完成，而并行Stream则是在多个线程上同时执行。
 * @return:
 * @auther: zhou
 * @date: 2018/12/24 下午3:14
 * <p>
 * https://blog.csdn.net/qq_28410283/article/details/80782808
 */
public class StreamTest {


    /***
     * 流提供了流畅的API，可以进行数据转换和对结果执行某些操作。流操作既可以是“中间的”也可以是“末端的”。
     *
     * 中间的 -中间的操作保持流打开状态，并允许后续的操作。上面例子中的filter和map方法就是中间的操作。这些操作的返回数据类型是流；它们返回当前的流以便串联更多的操作。
     * 末端的 - 末端的操作必须是对流的最终操作。当一个末端操作被调用，流被“消耗”并且不再可用。上面例子中的sum方法就是一个末端的操作。
     */


    /***
     * 中间的操作：
     *
     * filter 1 - 排除所有与断言不匹配的元素。
     * map 1 2 3 4 - 通过Function对元素执行一对一的转换。
     * flatMap 1 2 3 4 5 - 通过FlatMapper将每个元素转变为无或更多的元素。
     * peek 1 - 对每个遇到的元素执行一些操作。主要对调试很有用。
     * distinct 1 - 根据.equals行为排除所有重复的元素。这是一个有状态的操作。
     * sorted 1 2 - 确保流中的元素在后续的操作中，按照比较器（Comparator）决定的顺序访问。这是一个有状态的操作。
     * limit 1 - 保证后续的操作所能看到的最大数量的元素。这是一个有状态的短路的操作。
     * substream 1 2 - 确保后续的操作只能看到一个范围的（根据index）元素。像不能用于流的String.substring一样。也有两种形式，一种有一个开始索引，一种有一个结束索引。二者都是有状态的操作，有一个结束索引的形式也是一个短路的操作。
     * 末端的操作：
     *
     * forEach 1 - 对流中的每个元素执行一些操作。
     * toArray 1 2 - 将流中的元素倾倒入一个数组。
     * reduce 1 2 3 - 通过一个二进制操作将流中的元素合并到一起。
     * collect 1 2 - 将流中的元素倾倒入某些容器，例如一个Collection或Map.
     * min 1 - 根据一个比较器找到流中元素的最小值。
     * max 1 -根据一个比较器找到流中元素的最大值。
     * count 1 - 计算流中元素的数量。
     * anyMatch 1 - 判断流中是否至少有一个元素匹配断言。这是一个短路的操作。
     * allMatch 1 - 判断流中是否每一个元素都匹配断言。这是一个短路的操作。
     * noneMatch 1 - 判断流中是否没有一个元素匹配断言。这是一个短路的操作。
     * findFirst 1 - 查找流中的第一个元素。这是一个短路的操作。
     * findAny 1 - 查找流中的任意元素，可能对某些流要比findFirst代价低。这是一个短路的操作。
     */


    @Test
    public void createStreamTest() {
        String[] dd = {"a", "b", "c"};
        Arrays.stream(dd).forEach(System.out::print);// abc
        System.out.println();
        Stream.of(dd).forEach(System.out::print);// abc
        System.out.println();
        Arrays.asList(dd).stream().forEach(System.out::print);// abc
        System.out.println();
        Stream.iterate(0, x -> x + 1).limit(10).forEach(System.out::print);// 0123456789
        System.out.println();
        Stream.generate(() -> "x").limit(10).forEach(System.out::print);// xxxxxxxxxx


    }

    @Test
    public void filterTest() {
        String[] dd = {"a", "b", "c"};
        Stream<String> stream = Arrays.stream(dd);

        //带括号的话，需要有return语句，就是执行一个布尔值的操作
        stream.filter(str -> {
            return str.equals("a");
        }).forEach(System.out::println);
        stream.filter(str -> str.equals("a")).forEach(System.out::println);//返回字符串为a的值
    }


    //给的是T，返回的是R，调用这个函数后可以改变返回的类型
    @Test
    public void mapTest() {
        Integer[] dd = {1, 2, 3};
        Stream<Integer> stream = Arrays.stream(dd);
        stream.map(str -> Integer.toString(str)).forEach(str -> {
            System.out.println(str);// 1 ,2 ,3
            System.out.println(str.getClass());// class java.lang.String
        });

        //就是给一个T，返回一个R
        List<Emp> list = Arrays.asList(new Emp("a"), new Emp("b"), new Emp("c"));
        list.stream().map(emp -> emp.getName()).forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void flatMapTest() {
        String[] strs = {"aaa", "bbb", "ccc"};
        Arrays.stream(strs).map(str -> str.split("")).forEach(System.out::println);// Ljava.lang.String;@53d8d10a
        Arrays.stream(strs).map(str -> str.split("")).flatMap(Arrays::stream).forEach(System.out::println);// aaabbbccc
        Arrays.stream(strs).map(str -> str.split("")).flatMap(str -> Arrays.stream(str)).forEach(System.out::println);// aaabbbccc
    }


    /**
     * 功能描述:
     *
     * @param: //去重复
     * Stream<T> distinct();
     * //排序
     * Stream<T> sorted();
     * //根据属性排序
     * Stream<T> sorted(Comparator<? super T> comparator);
     * //对对象的进行操作
     * Stream<T> peek(Consumer<? super T> action);
     * //截断--取先maxSize个对象
     * Stream<T> limit(long maxSize);
     * //截断--忽略前N个对象
     * Stream<T> skip(long n);
     * ---------------------
     * 作者：葵花下的獾
     * 来源：CSDN
     * 原文：https://blog.csdn.net/qq_28410283/article/details/80643711
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     * @return:
     * @auther: zhou
     * @date: 2018/12/24 下午3:34
     */

    public static List<Emp> list = new ArrayList<>();

    static {
        list.add(new Emp("xiaoHong1", 20, 1000.0));
        list.add(new Emp("xiaoHong2", 25, 2000.0));
        list.add(new Emp("xiaoHong3", 30, 3000.0));
        list.add(new Emp("xiaoHong4", 35, 4000.0));
        list.add(new Emp("xiaoHong5", 38, 5000.0));
        list.add(new Emp("xiaoHong6", 45, 9000.0));
        list.add(new Emp("xiaoHong7", 55, 10000.0));
        list.add(new Emp("xiaoHong8", 42, 15000.0));
    }

    public static void println(Stream<Emp> stream) {
        stream.forEach(emp -> {
            System.out.println(String.format("名字：%s，年纪：%s，薪水：%s", emp.getName(), emp.getAge(), emp.getSalary()));
        });
    }


    @Test
    public void distinceTest() {

        // 对数组流，先过滤重复，在排序，再控制台输出 1，2，3,其实T是集合
        Arrays.asList(list).stream().distinct().sorted().forEach(str -> {
            System.out.println(str);
        });

        // 对list里的emp对象，取出薪水，并对薪水进行排序，然后输出薪水的内容，map操作，改变了Strenm的泛型对象

        list.stream().map(emp -> emp.getSalary()).sorted().forEach(salary -> System.out.println(salary));

        // 根据emp的属性name，进行排序
        println(list.stream().sorted(Comparator.comparing(Emp::getName)));

        // 给年纪大于30岁的人，薪水提升1.5倍，并输出结果
        Stream<Emp> stream = list.stream().filter(emp -> {
            return emp.getAge() > 30;
            //peek可以执行额外的操作
        }).peek(emp -> {
            emp.setSalary(emp.getSalary() * 1.5);
        });

        System.out.println(stream);

    }


    @Test
    public void toArray() {
        List<String> strs = Arrays.asList("a", "b", "c");
        String[] dd = strs.stream().toArray(str -> new String[strs.size()]);
        String[] dd1 = strs.stream().toArray(String[]::new);
        Object[] obj = strs.stream().toArray();

        String[] dd2 = strs.toArray(new String[strs.size()]);
        Object[] obj1 = strs.toArray();

    }


    @Test
    public void toCompared() {
        List<String> strs = Arrays.asList("d", "b", "a", "c", "a");
        Optional<String> min = strs.stream().min(Comparator.comparing(Function.identity()));
        Optional<String> max = strs.stream().max((o1, o2) -> o1.compareTo(o2));
        System.out.println(String.format("min:%s; max:%s", min.get(), max.get()));// min:a; max:d

        Optional<String> aa = strs.stream().filter(str -> !str.equals("a")).findFirst();
        Optional<String> bb = strs.stream().filter(str -> !str.equals("a")).findAny();

        Optional<String> aa1 = strs.parallelStream().filter(str -> !str.equals("a")).findFirst();
        Optional<String> bb1 = strs.parallelStream().filter(str -> !str.equals("a")).findAny();

        System.out.println(aa.get() + "===" + bb.get());// d===d
        System.out.println(aa1.get() + "===" + bb1.get());// d===b or d===c
    }


    @Test
    public void anMatchTest() {
        List<String> strs = Arrays.asList("a", "a", "a", "a", "b");
        boolean aa = strs.stream().anyMatch(str -> str.equals("a"));
        boolean bb = strs.stream().allMatch(str -> str.equals("a"));
        boolean cc = strs.stream().noneMatch(str -> str.equals("a"));
        long count = strs.stream().filter(str -> str.equals("a")).count();
        System.out.println(aa);// TRUE
        System.out.println(bb);// FALSE
        System.out.println(cc);// FALSE
        System.out.println(count);// 4
    }

    @ToString
    public static class Emp {
        private String name;

        private Integer age;

        private Double salary;


        public Emp(String name) {
            this.name = name;
        }

        public Emp(String name, Integer age, Double salary) {
            super();
            this.name = name;
            this.age = age;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double salary) {
            this.salary = salary;
        }


    }


}
