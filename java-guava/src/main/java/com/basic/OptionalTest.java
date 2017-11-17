package com.basic;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 9:56 2017/11/17
 */
public class OptionalTest {


    /**
     * 　null只是一个关键字，用来标识一个不确定的对象，他既不是对象，也不是Objcet对象的实例
     */
    @Test
    public void testNullObject() {
        if (null instanceof java.lang.Object) {
            System.out.println("null属于java.lang.Object类型");
        } else {
            System.out.println("null不属于java.lang.Object类型");
        }
    }


    /**
     * 　    Optional.of(T)：获得一个Optional对象，其内部包含了一个非null的T数据类型实例，若T=null，则立刻报错。
     * 　　Optional.absent()：获得一个Optional对象，其内部包含了空值
     * 　　Optional.fromNullable(T)：将一个T的实例转换为Optional对象，T的实例可以不为空，也可以为空[Optional.fromNullable(null)，和Optional.absent()等价。
     *
     * @throws Exception
     */
    @Test
    public void testOptional() throws Exception {
        Optional<OptionalTest> possible = Optional.of(this);
        if (possible.isPresent()) {
            System.out.println("possible isPresent:" + possible.isPresent());
            System.out.println("possible value:" + possible.get());
        }
    }


    @Test
    public void testOptional2() throws Exception {
        Optional<Integer> possible = Optional.of(6);
        Optional<Integer> absentOpt = Optional.absent();
        Optional<Integer> NullableOpt = Optional.fromNullable(null);
        Optional<Integer> NoNullableOpt = Optional.fromNullable(10);
        if (possible.isPresent()) {
            System.out.println("possible isPresent:" + possible.isPresent());
            System.out.println("possible value:" + possible.get());
        }
        if (absentOpt.isPresent()) {
            System.out.println("absentOpt isPresent:" + absentOpt.isPresent());
            ;
        }
        if (NullableOpt.isPresent()) {
            System.out.println("fromNullableOpt isPresent:" + NullableOpt.isPresent());
            ;
        }
        if (NoNullableOpt.isPresent()) {
            System.out.println("NoNullableOpt isPresent:" + NoNullableOpt.isPresent());
            ;
        }
    }


    /**
     * 　　1>. boolean isPresent()：如果Optional包含的T实例不为null，则返回true；若T实例为null，返回false
     * 　　2>. T get()：返回Optional包含的T实例，该T实例必须不为空；否则，对包含null的Optional实例调用get()会抛出一个IllegalStateException异常
     * 　　3>. T or(T)：若Optional实例中包含了传入的T的相同实例，返回Optional包含的该T实例，否则返回输入的T实例作为默认值
     * 　　4>. T orNull()：返回Optional实例中包含的非空T实例，如果Optional中包含的是空值，返回null，逆操作是fromNullable()
     * 　　5>. Set<T> asSet()：返回一个不可修改的Set，该Set中包含Optional实例中包含的所有非空存在的T实例
     */
    @Test
    public void testMethodReturn() {
        Optional<Long> value = method();
        if (value.isPresent()) {
            System.out.println("获得返回值: " + value.get());
        } else {
            System.out.println("获得返回值: " + value.or(-12L));
        }
        System.out.println("获得返回值 orNull: " + value.orNull());

        Optional<Long> valueNoNull = methodNoNull();
        if (valueNoNull.isPresent() == true) {
            Set<Long> set = valueNoNull.asSet();
            System.out.println("获得返回值 set 的 size : " + set.size());
            System.out.println("获得返回值: " + valueNoNull.get());
        } else {
            System.out.println("获得返回值: " + valueNoNull.or(-12L));
        }

        System.out.println("获得返回值 orNull: " + valueNoNull.orNull());
    }


    private Optional<Long> method() {
        return Optional.fromNullable(null);
    }

    private Optional<Long> methodNoNull() {
        return Optional.fromNullable(15L);
    }


    /**
     * *. OptionalObject.isPresent(): 返回对象是否有值。
     * <p>
     * . Optional.absent(): 返回一个空Optional对象,isPresent() 将会返回false
     * <p>
     * . Optional.of(): 创Optional对象，输入参数不能为null
     * <p>
     * . Optional.fromNullable(): 创Optional对象，输入可以为null
     * <p>
     * . OptionalObject.asSet(): 和Optional对象值合并，如果为null则返回size为0的Set
     * <p>
     * . OptionalObject.or(): 和Optional对象值合并，如果为null为空加则返回or参数作为默认值
     * <p>
     * . OptionalObject.orNull(): 和Optional对象值合并，如果为null为空加则返回Null作为默认值
     */


    @Test
    public void should_get_total_age_for_all_employees() throws Exception {
        final List<Employee> list = Lists.newArrayList(new Employee("em1", 30), new Employee("em2", 40), null, new Employee("em4", 18));
        int sum = 0;
        for (Employee employee : list) {
            if (employee != null) {
                sum += employee.getAge();
            }
        }
        System.out.println(sum);
    }

    @Test
    public void should_get_total_age_for_all_employees2() throws Exception {
        final List<Employee> list = Lists.newArrayList(new Employee("em1", 30),
                new Employee("em2", 40),
                null,
                new Employee("em4", 18));
        int sum = 0;
        for (Employee employee : list) {
            //我们不在担心对象对空了，利用Optional的fromNullable创建了一个可空对象，并将其or上一个dummy的员工信息，所以在这里我们不在担心NullPointerExceptiond。
            sum += Optional.fromNullable(employee).or(new Employee("dummy", 0)).getAge();
        }
        System.out.println(sum);
    }

    private class Employee {
        private final String name;
        private final int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
