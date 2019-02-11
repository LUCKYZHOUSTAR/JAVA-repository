/**
 * 佛祖镇楼                  BUG辟易
 * 佛曰:
 * 写字楼里写字间，写字间里程序员；
 * 程序人员写程序，又拿程序换酒钱。
 * 酒醒只在网上坐，酒醉还来网下眠；
 * 酒醉酒醒日复日，网上网下年复年。
 * 但愿老死电脑间，不愿鞠躬老板前；
 * 奔驰宝马贵者趣，公交自行程序员。
 * 别人笑我忒疯癫，我笑自己命太贱；
 * 不见满街漂亮妹，哪个归得程序员？
 */
package com.java.common.jersey;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.java.common.swagger.SwaggerConfig;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ApiListingResource
 * <p>
 * 生成swagger的描述数据
 * <p>
 * 添加全局自定义的参数和自动以的异常
 * <p>
 * 添加Jsonp的canllback参数
 */
@Component
@Path("/swagger.{type:json|yaml}")
@ConditionalOnBean(annotation = EnableJersey.class)
public class ApiListingResource extends io.swagger.jaxrs.listing.ApiListingResource {

    /**
     * slf4j日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 参数配置
     */
    @Autowired
    private SwaggerConfig config;
    /**
     * 是否已经添加过参数
     */
    private AtomicBoolean add = new AtomicBoolean(false);
    /**
     * 参数类别class的映射
     */
    private static Map<String, Class<?>> map = new HashMap<String, Class<?>>() {
        {
            put("query", QueryParameter.class);
            put("header", HeaderParameter.class);
            put("cookie", CookieParameter.class);
            put("form", FormParameter.class);
        }
    };
    /**
     * jsonp自动添加的字段
     */
    private static Set<String> set = Sets.newHashSet("__callback", "callback");

    /**
     * 使用不可变map防止篡改
     */
    private static Map<String, Class<?>> parameterMap = Collections.unmodifiableMap(map);
    /**
     * 使用不可变set防止篡改
     */
    private static Set<String> callbackSet = Collections.unmodifiableSet(set);
    /**
     * context的上下文
     */
    @Context
    private ServletContext context;

    @GET
    @SuppressWarnings("all")
    @Produces({MediaType.APPLICATION_JSON, "application/yaml"})
    public Response getListing(
            @Context Application app, @Context ServletConfig sc, @Context HttpHeaders headers,
            @Context UriInfo uriInfo, @PathParam("type") String type) {
        Swagger swagger = (Swagger) super.getListing(app, sc, headers, uriInfo, type).getEntity();
        if (!add.get() && config != null && config.getParameters() != null) {
            try {
                assemblyParameter(swagger);
            } catch (Exception e) {
                logger.error("assembly parameter error");
            }
            add.set(true);
        }
        /*如果不是排序的map，则使用排序*/
        if (swagger.getPaths() != null && !(swagger.getPaths() instanceof TreeMap)) {
            swagger.setPaths(new TreeMap<>(swagger.getPaths()));
            String context = sc.getServletContext().getContextPath();
            if (StringUtils.isNotEmpty(context)) {
                if ("/".equals(context)) {
                    context = "";
                }
                if (context.length() > 1 && context.endsWith("/")) {
                    context = context.substring(0, context.length() - 1);
                }
                if (context.length() > 1 && !context.startsWith("/")) {
                    context = "/" + context;
                }
            }
            swagger.setBasePath(context + swagger.getBasePath());
        }
        return Response.ok().entity(swagger).type(
                StringUtils.isNotBlank(type) && type.trim().equalsIgnoreCase("yaml") ?
                        "application/yaml" : MediaType.APPLICATION_JSON).build();
    }

    /**
     * 组织自定义的参数
     *
     * @param swagger 原来的swagger 描述
     */
    private void assemblyParameter(Swagger swagger) {
        swagger.getPaths().values().stream().map(io.swagger.models.Path::getOperationMap).map(Map::values).flatMap(Collection::stream).forEach(o -> {
            /*添加全局自定义的参数*/
            config.getParameters().stream().filter(c -> parameterMap.containsKey(c.getCategory()) && o.getParameters().stream().noneMatch(p -> p instanceof AbstractSerializableParameter && p.getIn().startsWith(c.getCategory()) && p.getName().equals(c.getName()))).forEach(c -> {
                        try {
                            Class<?> clazz = parameterMap.get(c.getCategory());
                            AbstractSerializableParameter<?> parameter = (AbstractSerializableParameter) clazz.newInstance();
                            parameter.setName(c.getName());
                            parameter.setType(StringUtils.isNotEmpty(c.getType()) ? c.getType() : "string");
                            parameter.setDescription(StringUtils.isNotEmpty(c.getDescription()) ? c.getDescription() : c.getName());
                            Optional.ofNullable(c.getDefaultValue()).ifPresent(parameter::setDefaultValue);
                            Optional.ofNullable(c.getAllowValues()).ifPresent(str -> parameter.setEnumValue(Lists.newArrayList(str.split(","))));
                            o.addParameter(parameter);
                        } catch (Exception e) {
                            logger.error("", e);
                        }
                    }
            );
            /*添加jsonp的callback参数*/
            if (!CollectionUtils.isEmpty(o.getParameters()) && new HashSet<>(o.getProduces()).contains("application/x-javascript") && o.getParameters().stream().noneMatch(p -> (p instanceof QueryParameter) && callbackSet.contains(p.getName()))) {
                QueryParameter queryParameter = new QueryParameter();
                queryParameter.setType("string");
                queryParameter.setName("callback");
                queryParameter.setDefaultValue("callback");
                o.addParameter(queryParameter);
            }
            /*添加全局自定义的response*/
            if (config.getResponses() != null) {
                config.getResponses().forEach((k, v) -> o.getResponses().putIfAbsent(k, new io.swagger.models.Response().description(v)));
            }
        });
    }
}
