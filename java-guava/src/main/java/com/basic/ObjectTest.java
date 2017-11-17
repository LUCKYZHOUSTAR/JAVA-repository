package com.basic;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 10:39 2017/11/17
 */
public class ObjectTest {


    @Test
    public void nullTest() {
        Objects.equal("a", "a");// returns true
        Objects.equal(null, "a");// returns false
        Objects.equal("a", null);// returns false
        Objects.equal(null, null);// returns true
    }


    @Test
    public void hashCodeTest() {
        System.out.println(Objects.hashCode(this));
    }


    @Test
    public void toStringTest() {
        Objects.toStringHelper(this)
                .add("x", 1)
                .toString();

    }


    class Person implements Comparable<Person> {
        private String lastName;
        private String firstName;
        private int zipCode;

        @Override
        public int compareTo(Person other) {
//            int cmp = lastName.compareTo(other.lastName);
//            if (cmp != 0) {
//                return cmp;
//            }
//            cmp = firstName.compareTo(other.firstName);
//            if (cmp != 0) {
//                return cmp;
//            }
//            return 0;
            return ComparisonChain.start()
                    .compare(this.lastName, other.lastName)
                    .compare(this.firstName, other.firstName)
                    .result();
        }
    }
}
