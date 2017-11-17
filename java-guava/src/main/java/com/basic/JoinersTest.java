package com.basic;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 13:00 2017/11/17
 */
public class JoinersTest {

    final ImmutableList charList = ImmutableList.of("23", "23", "23");

    private static String normalJoin(List<String> strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
            if (strings.indexOf(s) + 1 != strings.size()) {
                builder.append(",");
            }
        }
        return builder.toString();
    }


    /**
     * skipNulls()：忽略NULL；
     * useForNull(“Hello”)：NULL的地方都用字符串”Hello”来代替。
     */
    @Test
    public void joinerJoin() {
        System.out.println(Joiner.on(",").join(charList));
        Joiner joiner1 = Joiner.on("; ").skipNulls();
        Joiner joiner2 = Joiner.on("; ").useForNull("Hello");
        System.out.println(joiner1.join("A", null, "B", "C"));
        System.out.println(joiner2.join("A", null, "B", "C"));


        //append到StringBuilder
        StringBuilder builder = new StringBuilder();
        Joiner joiner = Joiner.on(",").skipNulls();
        joiner.appendTo(builder, "Hello", "Guava");
        System.out.println(builder); //Hello,Guava


        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        Joiner.MapJoiner mapJoiner = Joiner.on(",").withKeyValueSeparator("=");
        System.out.println(mapJoiner.join(map)); //key3=value3,key2=value2,key1=value1
    }


    @Test
    public void splitTest() {
        Splitter splitter = Splitter.on('|').trimResults();
        Iterable<String> parts = splitter.split("1|2|3|||");
    }

    @Test
    public void testSplitter() {
        String startString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
        Map<String, String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C", "Redskins");
        testMap.put("New York City", "Giants");
        testMap.put("Philadelphia", "Eagles");
        testMap.put("Dallas", "Cowboys");
        Splitter.MapSplitter mapSplitter =
                Splitter.on("#").withKeyValueSeparator("=");
        Map<String, String> splitMap =
                mapSplitter.split(startString);
        System.out.println("");
    }
}
