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
package com.java.common.util;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 反射的工具类
 */
public class ReflectUtils {
    /**
     * slf4j日志
     */
    private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    /**
     * 根据类别获取类上的注解
     *
     * @param clazz  原始类
     * @param tClass 注解类
     * @param <T>    注解的泛型规约
     * @return 返回注解
     */
    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> tClass) {
        while (!clazz.equals(Object.class)) {
            T t = clazz.getAnnotation(tClass);
            if (t != null) {
                return t;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    /**
     * 设置属性
     *
     * @param targetObject 目标对象
     * @param fieldName    字段名
     * @param fieldObject  字段值
     * @throws Exception
     */
    public static void setField(Object targetObject, String fieldName, Object fieldObject) throws Exception {
        Class<?> clazz = targetObject.getClass();
        while (!clazz.equals(Object.class)) {
            Field field = clazz.getDeclaredField(fieldName);
            if (field != null) {
                field.setAccessible(true);
                field.set(targetObject, fieldObject);
                break;
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * copy属性生成bean。
     * 必须有默认的无参数的构造方法
     *
     * @param src    原对象
     * @param target 目标对象的class
     * @param ignore 忽略的属性
     * @param <T>    泛型规约
     * @return
     */
    @SuppressWarnings("all")
    public static <T> T copy(Object src, Class<T> target, String... ignore) throws Exception {
        T t = target.newInstance();
        BeanUtils.copyProperties(src, t, ignore);
        return t;
    }

    /**
     * 安全调用没有参数的void方法
     *
     * @param obj        实体类
     * @param methodName 方法名
     */
    @SuppressWarnings("all")
    public static void safeInvokeNoArgMethod(Object obj, String methodName) {
        safeInvokeNoArgMethod(obj, obj.getClass(), methodName);
    }

    /**
     * 安全调用没有参数的void方法
     *
     * @param obj        实体类
     * @param clazz      class的类名
     * @param methodName 方法名
     */
    @SuppressWarnings("all")
    public static void safeInvokeNoArgMethod(Object obj, Class clazz, String methodName) {
        if (obj != null) {
            try {
                Set<Method> methods = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withName(methodName));
                methods.stream().filter(m -> m.getParameterCount() == 0).forEach(method -> {
                    try {
                        method.invoke(obj);
                    } catch (Exception e) {
                        logger.warn("invoke {} error", methodName);
                    }
                });
            } catch (Exception e) {
                logger.warn("invoke {} error", methodName);
            }
        } else {
            logger.warn("obj is null");
        }
    }
}
