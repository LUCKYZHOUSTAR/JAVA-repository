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
package com.java.common.swagger;

import com.java.common.jersey.EnableJersey;
import com.java.common.jersey.JerseyPathConfig;
import com.java.common.util.ReflectUtils;
import io.swagger.jaxrs.config.BeanConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.TreeMap;

/**
 * jersey的swagger配置
 */
@Component
@Order(100)
@ConditionalOnBean(annotation = EnableJersey.class)
@EnableConfigurationProperties(SwaggerConfig.class)
public class SwaggerConfiguration extends WebMvcConfigurerAdapter implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 工程名
     */
    @Value("${spring.application.name:swagger}")
    private String applicationName;
    /**
     * jersey的basepath的工具
     */
    @Autowired
    private JerseyPathConfig jerseyPathConfig;
    /**
     * spring的上下文
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 添加swagger静态资源的映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/", "classpath:/templates/", "classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void run(String... args) {
        logger.info("init the jersey-swagger start");
        TreeMap<String, Object> treeMap = new TreeMap<>(applicationContext.getBeansWithAnnotation(EnableJersey.class));
        EnableJersey jersey = ReflectUtils.getAnnotation(treeMap.firstEntry().getValue().getClass(), EnableJersey.class);
        if (jersey != null) {
            String title = jersey.title();
            title = StringUtils.isEmpty(title) ? applicationName : title;
            BeanConfig config = new BeanConfig();
            config.setConfigId(title);
            /*设置title上的超链接跳转到api*/
            config.setTitle("<a href=\"../.." + jerseyPathConfig.getApplicationPath() + "/api\" target = \"_blank\">" + title + "</a>");
            config.setVersion("1.0.0");
            config.setContact(jersey.author());
            config.setSchemes(new String[]{"http", "https"});
            config.setBasePath(jerseyPathConfig.getApplicationPath());
            /*设置basepackage，使用逗号隔开*/
            config.setResourcePackage(String.join(",", jersey.packages()));
            config.setPrettyPrint(true);
            config.setScan(true);
            logger.info("init the jersey-swagger end success");
        } else {
            logger.error("init jersey-swagger error");
        }
    }
}
