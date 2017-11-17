package com.collection;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:chaoqiang.zhou
 * @Description: 　Guava引进了JDK里没有的，但是非常有用的一些新的集合类型。所有这些新集合类型都能和JDK里的集合平滑集成。Guava集合非常精准地实现了JDK定义的接口。Guava中定义的新集合有：
 * 　　Multiset
 * 　　SortedMultiset
 * 　　Multimap
 * 　　ListMultimap
 * 　　SetMultimap
 * 　　BiMap
 * 　　ClassToInstanceMap
 * 　　Table
 * @Date:Create in 11:41 2017/11/17
 */
public class MultisetTest {


    /***
     * HashMap                  HashMultiset                      Yes
     　　TreeMap                   TreeMultiset                       Yes (if the comparator does)
     　　LinkedHashMap         LinkedHashMultiset             Yes
     　　ConcurrentHashMap  ConcurrentHashMultiset       No
     　　ImmutableMap          ImmutableMultiset               No
     */
    @Test
    public void testMultsetWordCount() {
        String strWorld = "wer|dfd|dd|dfd|dda|de|dr";
        String[] words = strWorld.split("\\|");
        List<String> wordList = new ArrayList<String>();
        for (String word : words) {
            wordList.add(word);
        }
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(wordList);

        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }


        Multiset<String> wordsCount = HashMultiset.create();
        wordsCount.addAll(wordList);
        System.out.println(wordsCount.count("dd"));
    }


    @Test
    public void testMultsetWordCount2() {
        String strWorld = "wer|dfd|dd|dfd|dda|de|dr";
        String[] words = strWorld.split("\\|");
        List<String> wordList = new ArrayList<String>();
        for (String word : words) {
            wordList.add(word);
        }
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(wordList);


        //System.out.println("wordsMultiset："+wordsMultiset);

        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }

        if (!wordsMultiset.contains("peida")) {
            wordsMultiset.add("peida", 2);
        }
        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }


        if (wordsMultiset.contains("peida")) {
            wordsMultiset.setCount("peida", 23);
        }

        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }

        if (wordsMultiset.contains("peida")) {
            wordsMultiset.setCount("peida", 23, 45);
        }

        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }

        if (wordsMultiset.contains("peida")) {
            wordsMultiset.setCount("peida", 44, 67);
        }

        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }
    }
}
