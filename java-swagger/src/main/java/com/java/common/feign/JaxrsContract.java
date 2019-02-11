/*
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.java.common.feign;

import com.google.common.collect.Lists;
import feign.MethodMetadata;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.ws.rs.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;

/**
 * jersey注解的解析，只要是feign使用jsr的注解
 * 继承了MVC的SpringMvcContract 解析器
 * jsr和mvc的注解可以同时使用
 */
@Component
@ConditionalOnClass(FeignClient.class)
@ConditionalOnBean(annotation = EnableFeignClients.class)
public final class JaxrsContract extends SpringMvcContract {

    private static final String ACCEPT = "Accept";
    private static final String CONTENT_TYPE = "Content-Type";


    @Override
    protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
        Path path = clz.getAnnotation(Path.class);
        if (path != null) {
            String pathValue = emptyToNull(path.value());
            checkState(pathValue != null, "Path.value() was empty on type %s", clz.getName());
            if (pathValue == null) {
                return;
            }
            if (!pathValue.startsWith("/")) {
                pathValue = "/" + pathValue;
            }
            if (pathValue.endsWith("/")) {
                pathValue = pathValue.substring(0, pathValue.length() - 1);
            }
            data.template().insert(0, pathValue);
        } else {
            super.processAnnotationOnClass(data, clz);
        }
        Consumes consumes = clz.getAnnotation(Consumes.class);
        if (consumes != null) {
            handleConsumesAnnotation(data, consumes, clz.getName());
        }
        Produces produces = clz.getAnnotation(Produces.class);
        if (produces != null) {
            handleProducesAnnotation(data, produces, clz.getName());
        }
    }

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        if (!RequestMapping.class.isInstance(methodAnnotation) && !methodAnnotation
                .annotationType().isAnnotationPresent(RequestMapping.class)) {
            Class<? extends Annotation> annotationType = methodAnnotation.annotationType();
            HttpMethod http = annotationType.getAnnotation(HttpMethod.class);
            if (http != null) {
                checkState(data.template().method() == null, "Method %s contains multiple HTTP methods. Found: %s and %s",
                        method.getName(), data.template().method(), http.value());
                data.template().method(http.value());
            } else if (annotationType == Path.class) {
                String pathValue = emptyToNull(Path.class.cast(methodAnnotation).value());
                checkState(pathValue != null, "Path.value() was empty on method %s", method.getName());
                String methodAnnotationValue = Path.class.cast(methodAnnotation).value();
                if (!methodAnnotationValue.startsWith("/") && !data.template().url().endsWith("/")) {
                    methodAnnotationValue = "/" + methodAnnotationValue;
                }
                methodAnnotationValue = methodAnnotationValue.replaceAll("\\{\\s*(.+?)\\s*(:.+?)?\\}", "\\{$1\\}");
                data.template().append(methodAnnotationValue);
            } else if (annotationType == Produces.class) {
                handleProducesAnnotation(data, (Produces) methodAnnotation, "method " + method.getName());
            } else if (annotationType == Consumes.class) {
                handleConsumesAnnotation(data, (Consumes) methodAnnotation, "method " + method.getName());
            }
        } else {
            super.processAnnotationOnMethod(data, methodAnnotation, method);
        }

    }

    private void handleProducesAnnotation(MethodMetadata data, Produces produces, String name) {
        String[] serverProduces = produces.value();
        String clientAccepts = serverProduces.length == 0 ? null : emptyToNull(serverProduces[0]);
        checkState(clientAccepts != null, "Produces.value() was empty on %s", name);
        data.template().header(ACCEPT, (String) null);
        data.template().header(ACCEPT, clientAccepts);
    }

    private void handleConsumesAnnotation(MethodMetadata data, Consumes consumes, String name) {
        String[] serverConsumes = consumes.value();
        String clientProduces = serverConsumes.length == 0 ? null : emptyToNull(serverConsumes[0]);
        checkState(clientProduces != null, "Consumes.value() was empty on %s", name);
        data.template().header(CONTENT_TYPE, (String) null);
        data.template().header(CONTENT_TYPE, clientProduces);
    }

    @Override
    protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
        boolean isHttpParam = false;
        for (Annotation parameterAnnotation : annotations) {
            Class<? extends Annotation> annotationType = parameterAnnotation.annotationType();
            if (annotationType == PathParam.class) {
                String name = PathParam.class.cast(parameterAnnotation).value();
                checkState(emptyToNull(name) != null, "PathParam.value() was empty on parameter %s", paramIndex);
                nameParam(data, name, paramIndex);
                isHttpParam = true;
            } else if (annotationType == QueryParam.class) {
                String name = QueryParam.class.cast(parameterAnnotation).value();
                checkState(emptyToNull(name) != null, "QueryParam.value() was empty on parameter %s", paramIndex);
                Collection<String> query = addTemplatedParam(data.template().queries().get(name), name);
                data.template().query(name, query);
                nameParam(data, name, paramIndex);
                isHttpParam = true;
            } else if (annotationType == HeaderParam.class) {
                String name = HeaderParam.class.cast(parameterAnnotation).value();
                checkState(emptyToNull(name) != null, "HeaderParam.value() was empty on parameter %s", paramIndex);
                Collection<String> header = addTemplatedParam(data.template().headers().get(name), name);
                data.template().header(name, header);
                nameParam(data, name, paramIndex);
                isHttpParam = true;
            } else if (annotationType == FormParam.class) {
                String name = FormParam.class.cast(parameterAnnotation).value();
                checkState(emptyToNull(name) != null, "FormParam.value() was empty on parameter %s", paramIndex);
                data.formParams().add(name);
                nameParam(data, name, paramIndex);
                isHttpParam = true;
            } else if (annotationType == FormDataParam.class) {
                String name = FormDataParam.class.cast(parameterAnnotation).value();
                checkState(emptyToNull(name) != null, "FormDataParam.value() was empty on parameter %s", paramIndex);
                data.formParams().add(name);
                nameParam(data, name, paramIndex);
                isHttpParam = true;
            } else if (annotationType == CookieParam.class) {
                String name = CookieParam.class.cast(parameterAnnotation).value();
                checkState(emptyToNull(name) != null, "CookieParam.value() was empty on parameter %s", paramIndex);
                Collection<String> cookie = data.template().headers().get("Cookie");
                cookie = cookie == null ? Lists.newArrayList() : cookie;
                cookie.add(String.format("%s={%s}", name, name));
                nameParam(data, name, paramIndex);
                data.template().header("Cookie", cookie);
                isHttpParam = true;
            }
        }
        if (!isHttpParam) {
            isHttpParam = super.processAnnotationsOnParameter(data, annotations, paramIndex);
        }
        return isHttpParam;
    }
}
