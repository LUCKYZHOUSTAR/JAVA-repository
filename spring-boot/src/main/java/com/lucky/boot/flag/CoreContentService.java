package com.lucky.boot.flag;

import org.springframework.context.annotation.Bean;

/**
 * @Author:chaoqiang.zhou
 * @Description:
 * @Date:Create in 15:35 2017/12/12
 */
public class CoreContentService implements ContentService {
    @Override
    public void doSomething() {
        System.out.println("do some import things");
    }

    static class CoreContentConfiguration {
        @Bean
        public ContentService contentService() {
            return new CoreContentService();
        }
    }

    static class SimpleContentService implements ContentService {
        @Override
        public void doSomething() {
            System.out.println("do some simple things");
        }
    }

}




