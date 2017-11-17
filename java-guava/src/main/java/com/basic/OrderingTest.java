package com.basic;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:47 2017/11/17
 */
public class OrderingTest {


    @Test
    public void testFrom() {
        List<People> peopleList = new ArrayList<People>() {{
            add(new People("A", 33));
            add(new People("B", 11));
            add(new People("C", 18));
        }};

        Ordering<People> ordering = Ordering.from(new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {
                return o1.getAge() - o2.getAge();
            }
        });


        /**
         * 取得最小最大值
         */
        for (People p : ordering.greatestOf(peopleList, 2)) {
            System.out.println(MoreObjects.toStringHelper(p)
                    .add("name", p.getName())
                    .add("age", p.getAge())
            );
        }
        for (People p : ordering.sortedCopy(peopleList)) {
            System.out.println(MoreObjects.toStringHelper(p)
                    .add("name", p.getName())
                    .add("age", p.getAge())
            );
        }
    }

    @Test
    public void testUsingToString() {
        List<People> peopleList = new ArrayList<People>() {{
            add(new People("A", 33));
            add(new People("B", 11));
            add(new People("C", 18));
        }};

        Ordering<People> ordering = Ordering.usingToString().onResultOf(new Function<People, Comparable>() {
            @Override
            public Comparable apply(People people) {
                return people.getName();
            }
        });

        for (People p : ordering.sortedCopy(peopleList)) {
            System.out.println(MoreObjects.toStringHelper(p)
                    .add("name", p.getName())
                    .add("age", p.getAge())
            );
        }
    }

    @Test
    public void testReverse() {
        List<People> peopleList = new ArrayList<People>() {{
            add(new People("A", 33));
            add(new People("B", 11));
            add(new People("C", 18));
        }};

        Ordering<People> ordering = Ordering.natural().reverse().onResultOf(new Function<People, Comparable>() {
            @Override
            public Comparable apply(People people) {
                return people.getAge();
            }
        });

        for (People p : ordering.sortedCopy(peopleList)) {
            System.out.println(MoreObjects.toStringHelper(p)
                    .add("name", p.getName())
                    .add("age", p.getAge())
            );
        }
    }

    @Test
    public void testNatural() {
        List<People> peopleList = new ArrayList<People>() {{
            add(new People("A", 33));
            add(new People("B", 11));
            add(new People("C", 18));
        }};

        Ordering<People> ordering = Ordering.natural().onResultOf(new Function<People, Comparable>() {
            @Override
            public Comparable apply(People people) {
                return people.getName();
            }
        });

        for (People p : ordering.sortedCopy(peopleList)) {
            System.out.println(MoreObjects.toStringHelper(p)
                    .add("name", p.getName())
                    .add("age", p.getAge())
            );
        }
    }

    class People {
        String name;
        int age;


        public People(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
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
