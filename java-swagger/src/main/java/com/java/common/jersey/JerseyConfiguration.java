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

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.StringMemberValue;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;
import java.util.UUID;

/**
 * Jersey的默认的ResourceConfig的加载
 * 如果个人需要自定义ResourceConfig
 * 在主类上加上EnableJersey注解才会生效
 */
@Configuration
@ConditionalOnBean(annotation = EnableJersey.class)
public class JerseyConfiguration {
    /**
     * slf4j日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * basepath
     */
    @Value("${spring.jersey.application-path:/v1}")
    private String appath;

    /**
     * ResourceConfig没有这个实例的时候才会加载
     * 使用javassist动态生成类
     *
     * @return JerseyResourceConfig默认的jersey加载器
     */
    @ConditionalOnMissingBean
    @Bean(name = "defautJerseyResourceConfig")
    public ResourceConfig resourceConfig() throws Exception {
        logger.info("use javassist bytecode to create the ResourceConfig,use basepath {}", appath);
        ClassPool pool = ClassPool.getDefault();
        pool.appendClassPath(new ClassClassPath(ResourceConfig.class));
        pool.appendClassPath(new ClassClassPath(ApplicationPath.class));
        CtClass cc = pool.makeClass(getClass().getPackage().getName() + ".A" + UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
        CtClass superClass = pool.get(ResourceConfig.class.getName());
        cc.setSuperclass(superClass);
        ClassFile ccFile = cc.getClassFile();
        ConstPool constPool = ccFile.getConstPool();
        AnnotationsAttribute annotations = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation(ApplicationPath.class.getName(), constPool);
        annotation.addMemberValue("value", new StringMemberValue(appath, constPool));
        annotations.addAnnotation(annotation);
        ccFile.addAttribute(annotations);
        logger.info("create the ResourceConfig success, use basepath {}", appath);
        return ResourceConfig.class.cast(cc.toClass().newInstance());
    }
}
