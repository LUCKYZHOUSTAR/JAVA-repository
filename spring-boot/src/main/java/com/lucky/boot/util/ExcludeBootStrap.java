package com.lucky.boot.util;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;

/**
 * @description:https://segmentfault.com/a/1190000021245909
 * @author: lucky
 * @created: 2020/05/28 10:44
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MailSenderAutoConfiguration.class})
public class ExcludeBootStrap {
}
