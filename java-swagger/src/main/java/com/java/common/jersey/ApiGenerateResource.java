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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.ApiOperation;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.*;

/**
 * 生成feignclient的Resource，
 * 只要是客户端feignclient的api的自动生成
 * 访问http://{ip}:{port}/{context}/{basepath}/api
 */
@Component
@Path("/api")
@Produces({"application/json"})
@ConditionalOnBean(annotation = EnableJersey.class)
public class ApiGenerateResource {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.application.name:}")
    private String serviceId;
    @Value("${server.port:80}")
    private String port;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JerseyPathConfig jerseyPathConfig;

    @GET
    public Response apiResponse() throws Exception {
        return Response.ok().entity(getFeignClientString()).type(MediaType.APPLICATION_JSON).build();
    }

    @SuppressWarnings("all")
    private String getFeignClientString() throws Exception {
        StringBuilder content = new StringBuilder();
        /*判断org.springframework.cloud.netflix.feign.FeignClient这个类是否存在*/
        try {
            Class.forName("org.springframework.cloud.openfeign.FeignClient");
            content.append(String.format("@FeignClient(\"%s\")\n", serviceId));
        } catch (ClassNotFoundException e) {
            String inetAddress = InetAddress.getLocalHost().getHostAddress();
            if (StringUtils.isEmpty(serviceId)) {
                serviceId = UUID.randomUUID().toString().replace("-", "");
            }
            content.append(String.format("@FeignClient(name = \"%s\",url = \"http://%s:%s\")\n", serviceId, inetAddress, port));
        }
        content.append("public interface DemoFeignClient {\n");
        Set<Class<?>> importClasses = Sets.newHashSet();
        importClasses.add(Path.class);
        importClasses.add(ResponseEntity.class);
        getJerseyResourceClass().forEach(resourceClass -> {
                    logger.info("class is {}", resourceClass);
                    // 获取类@path注解，取出前缀
                    String classPath = "";
                    Path classPathAnnotation = resourceClass.getAnnotation(Path.class);
                    if (classPathAnnotation != null) {
                        classPath = classPathAnnotation.value();
                    }

                    for (Method method : ReflectionUtils.getAllMethods(resourceClass)) {
                        if (method.getAnnotation(GET.class) != null) {
                            importClasses.add(GET.class);
                            content.append("\t@GET\n");
                        } else if (method.getAnnotation(POST.class) != null) {
                            importClasses.add(POST.class);
                            content.append("\t@POST\n");
                        } else if (method.getAnnotation(PUT.class) != null) {
                            importClasses.add(PUT.class);
                            content.append("\t@PUT\n");
                        } else if (method.getAnnotation(DELETE.class) != null) {
                            importClasses.add(DELETE.class);
                            content.append("\t@DELETE\n");
                        } else {
                            continue;
                        }
                        // 获取方法@Path注解
                        Path methodPathAnnotation = method.getAnnotation(Path.class);
                        // 方法上若无@Path注解，路径以类的@Path注解值为准
                        content.append("\t@Path(\"").append(jerseyPathConfig.getApplicationPath()).append(classPath);
                        if (methodPathAnnotation != null) {
                            content.append(methodPathAnnotation.value());
                        }
                        content.append("\")\n");
                        // 获取方法@Consumes注解
                        Consumes methodConsumesAnnotation = method.getAnnotation(Consumes.class);
                        if (methodConsumesAnnotation != null) {
                            content.append("\t@Consumes(");
                            importClasses.add(Consumes.class);
                            if (methodConsumesAnnotation.value().length > 0) {
                                for (String v : methodConsumesAnnotation.value()) {
                                    content.append("\"").append(v).append("\", ");
                                }
                                content.setLength(content.length() - ", ".length());
                            }
                            content.append(")\n");
                        }
                        content.append("\tResponseEntity");
                        // 获取返回值泛型
                        ApiOperation apiOperationAnnotation = method.getAnnotation(ApiOperation.class);
                        if (apiOperationAnnotation != null && apiOperationAnnotation.response() != Void.class) {
                            try {
                                String responseClass = apiOperationAnnotation.response().getSimpleName();
                                importClasses.add(apiOperationAnnotation.response());
                                String responseContainer = apiOperationAnnotation.responseContainer();
                                if (!StringUtils.isEmpty(responseContainer)) {
                                    responseContainer = responseContainer.replace(responseContainer.substring(0, 1), responseContainer.substring(0, 1).toUpperCase());
                                    content.append("<").append(responseContainer).append("<").append(responseClass).append(">>");
                                } else {
                                    content.append("<").append(responseClass).append(">");
                                }
                            } catch (Exception e) {
                                logger.trace("返回值无泛型", e);
                            }
                        } else {
                            Class<?> returnType = method.getReturnType();
                            if (!returnType.equals(Response.class)) {
                                String typeName = method.getGenericReturnType().getTypeName();
                                String newType = typeName;
                                content.append("<").append(typeName).append(">");
                            }
                        }
                        // 获取方法名
                        String methodName = classPath;
                        if (methodName.length() > 0) {
                            // 增加前缀，避免重名
                            methodName = methodName.replaceAll("/", "_");
                            if (methodName.startsWith("_")) {
                                methodName = methodName.substring(1);
                            }
                            if (!methodName.endsWith("_")) {
                                methodName += "_";
                            }
                        }
                        methodName += method.getName();
                        content.append(" ").append(methodName).append("(");
                        /* 获取参数，@Context注解属于jersey的上下文。生成feign的时候需要排除掉,DefaultValue也得去掉 */
                        Arrays.stream(method.getParameters()).filter(param -> param.getAnnotation(Context.class) == null).forEach(param -> {
                            Arrays.stream(param.getAnnotations())
                                    .filter(p -> p.annotationType().getPackage().getName().startsWith("javax.ws.rs"))
                                    .filter(p -> !p.annotationType().equals(DefaultValue.class)).forEach(paramAnnotation -> {
                                content.append("@").append(paramAnnotation.annotationType().getSimpleName());
                                Class<?> paramAnnotationClass = paramAnnotation.annotationType();
                                importClasses.add(paramAnnotation.annotationType());
                                try {
                                    String v = paramAnnotationClass.getMethod("value").invoke(paramAnnotation).toString();
                                    content.append("(\"").append(v).append("\")");
                                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                                    logger.debug("此注解不包含value属性{}", paramAnnotation.annotationType().getSimpleName(), e);
                                }
                                content.append(" ");
                            });
                            // 添加参数类型
                            if (param.getParameterizedType() instanceof Class) {
                                content.append(param.getType().getSimpleName()).append(" ");
                                importClasses.add(param.getType());
                                // 添加参数名称

                            } else {
                                content.append(param.getParameterizedType().getTypeName()).append(" ");
                            }
                            content.append(param.getName()).append(", ");
                        });
                        // 去除多余后缀连接符
                        if (method.getParameterCount() > 0) {
                            content.setLength(content.length() - ", ".length());
                        }
                        content.append(");\n\n");
                    }
                }
        );
        content.append("}");
        StringBuilder sb = new StringBuilder();
        sb.append("package com.jyall.apollo.feignclient;\n\n");
        importClasses.stream().filter(s -> !s.isPrimitive()).map(s -> String.format("import %s;\n", s.getName())).forEach(sb::append);
        sb.append("import java.util.*;\n");
        sb.append("import org.springframework.cloud.netflix.feign.FeignClient;\n");
        sb.append("\n/**\n*用户注解\n*/\n");
        return sb.toString() + content.toString();
    }

    /**
     * 获取所有的带jersey注解的Path的类
     *
     * @return jersey 所有的resource
     */
    private Set<Class<?>> getJerseyResourceClass() {
        Set<Class<?>> set = Sets.newHashSet();
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Component.class);
        List<Object> classes = Lists.newArrayList();
        beans.values().stream()
                .filter(k -> !AopUtils.isJdkDynamicProxy(k))
                .filter(k -> getClassOfBean(k).getAnnotation(Path.class) != null)
                .forEach(classes::add);
        logger.info("the register jersey resource size is {}", classes.size());
        classes.forEach(v -> {
            Class<?> clazz = getClassOfBean(v);
            try {
                set.add(Thread.currentThread().getContextClassLoader().loadClass(clazz.getName()));
            } catch (Exception e) {
                set.add(clazz);
            }
            set.add(clazz);
        });
        /*将api和listing的Resource移除掉*/
        set.remove(ApiGenerateResource.class);
        set.remove(ApiListingResource.class);
        return set;
    }

    private Class<?> getClassOfBean(Object bean) {
        Class<?> clazz = bean.getClass();
        try {
            if (AopUtils.isAopProxy(bean)) {
                clazz = AopUtils.getTargetClass(bean);
            }
        } catch (Exception e) {
            logger.error("getClassOfBean error", e);
        }
        return clazz;
    }
}
