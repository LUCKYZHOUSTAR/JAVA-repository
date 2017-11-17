package com.basic;

import com.google.common.base.Strings;
import org.junit.Test;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 12:54 2017/11/17
 */
public class StringsTest {

    /**
     * Guava Strings工具类的使用，null和empty的判断与转化
     * @author chenleixing
     */
    @Test
    public void testStrings(){
        Strings.isNullOrEmpty("");//返回true
        Strings.nullToEmpty(null);//""
        Strings.nullToEmpty("chen");//返回"chen"
        Strings.emptyToNull("");//返回null
        Strings.emptyToNull("chen");//返回"chen"

        Strings.commonPrefix("aaab", "aac");//"aa"否则返回""
        Strings.commonSuffix("aaac", "aac");//"aac"否则返回""
        Strings.padStart("7", 3, '0');//"007"
        Strings.padStart("2010", 3, '0');//"2010"
        Strings.padEnd("4.", 5, '0');//"4.000"
        Strings.padEnd("2010", 3, '!');//"2010"
        Strings.repeat("hey", 3);//"heyheyhey"
    }
}
