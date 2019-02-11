/********************************************************
 ***                     _ooOoo_                       ***
 ***                    o8888888o                      ***
 ***                    88" . "88                      ***
 ***                    (| -_- |)                      ***
 ***                    O\  =  /O                      ***
 ***                 ____/`---'\____                   ***
 ***               .'  \\|     |//  `.                 ***
 ***              /  \\|||  :  |||//  \                ***
 ***             /  _||||| -:- |||||-  \               ***
 ***             |   | \\\  -  /// |   |               ***
 ***             | \_|  ''\---/''  |   |               ***
 ***             \  .-\__  `-`  ___/-. /               ***
 ***           ___`. .'  /--.--\  `. . __              ***
 ***        ."" '<  `.___\_<|>_/___.'  >'"".           ***
 ***       | | :  `- \`.;`\ _ /`;.`/ - ` : | |         ***
 ***       \  \ `-.   \_ __\ /__ _/   .-` /  /         ***
 ***  ======`-.____`-.___\_____/___.-`____.-'======    ***
 ***                     `=---='                       ***
 ***   .............................................   ***
 ***         佛祖镇楼                  BUG辟易         ***
 ***   佛曰:                                           ***
 ***           写字楼里写字间，写字间里程序员；        ***
 ***           程序人员写程序，又拿程序换酒钱。        ***
 ***           酒醒只在网上坐，酒醉还来网下眠；        ***
 ***           酒醉酒醒日复日，网上网下年复年。        ***
 ***           但愿老死电脑间，不愿鞠躬老板前；        ***
 ***           奔驰宝马贵者趣，公交自行程序员。        ***
 ***           别人笑我忒疯癫，我笑自己命太贱；        ***
 ***           不见满街漂亮妹，哪个归得程序员？        ***
 *********************************************************
 ***               江城子 . 程序员之歌                 ***
 ***           十年生死两茫茫，写程序，到天亮。        ***
 ***               千行代码，Bug何处藏。               ***
 ***           纵使上线又怎样，朝令改，夕断肠。        ***
 ***           领导每天新想法，天天改，日日忙。        ***
 ***               相顾无言，惟有泪千行。              ***
 ***           每晚灯火阑珊处，夜难寐，加班狂。        ***
 *********************************************************
 ***      .--,       .--,                              ***
 ***      ( (  \.---./  ) )                            ***
 ***       '.__/o   o\__.'                             ***
 ***          {=  ^  =}              高山仰止,         ***
 ***           >  -  <               景行行止.         ***
 ***          /       \              虽不能至,         ***
 ***         //       \\             心向往之。        ***
 ***        //|   .   |\\                              ***
 ***        "'\       /'"_.-~^`'-.                     ***
 ***           \  _  /--'         `                    ***
 ***         ___)( )(___                               ***
 ***        (((__) (__)))                              ***
 ********************************************************/
package com.java.common.jersey;

import com.java.common.util.ReflectUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.ApplicationPath;
import java.util.TreeMap;

/**
 * 获取jersey的统一前缀

 */
@Component
@ConditionalOnBean(annotation = EnableJersey.class)
public class JerseyPathConfig implements ApplicationContextAware {

    @Value("${spring.jersey.application-path:}")
    private String applicationPath;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (StringUtils.isEmpty(applicationPath)) {
            try {
                TreeMap<String, Object> treeMap = new TreeMap<>(applicationContext.getBeansWithAnnotation(ApplicationPath.class));
                Class<?> clazz = treeMap.firstEntry().getValue().getClass();
                /*循环一直到 class不是Object.class*/
                ApplicationPath applicationPath = ReflectUtils.getAnnotation(clazz, ApplicationPath.class);
                if (applicationPath != null) {
                    /*处理字符串，确保是 斜杠 + 字符串的模式*/
                    String path = applicationPath.value();
                    if (!path.startsWith("/")) {
                        path = "/" + path;
                    }
                    if (path.endsWith("/")) {
                        path = path.substring(0, path.length() - 1);
                    }
                    this.applicationPath = path;
                } else {
                    /*默认情况下使用 /v1*/
                    this.applicationPath = "/v1";
                }
            } catch (Exception e) {
                this.applicationPath = "/v1";
            }
        }
    }

    public String getApplicationPath() {
        return this.applicationPath;
    }
}
