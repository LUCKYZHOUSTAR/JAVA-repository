package com.lucky.boot.config;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: lucky
 * @created: 2020/05/06 14:33
 */
@Slf4j
@Configuration
@ConditionalOnProperty(name = "debug.enable", havingValue = "true")
public class DebugConfig {

    private static boolean debug;

    @PostConstruct
    public void init() {
        debug = true;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void debug(Logger logger, String template, Object... args) {
        if (isDebug()) {
            logger.info(template, args);
        }
    }
}
