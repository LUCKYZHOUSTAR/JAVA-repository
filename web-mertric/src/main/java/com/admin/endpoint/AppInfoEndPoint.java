package com.admin.endpoint;

import com.admin.utils.Analyzer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @Auther: chaoqiang.zhou
 * @Date: 2019/3/6 15:10
 * @Description:
 */

@RestControllerEndpoint(id = "appInfo")
@Slf4j
public class AppInfoEndPoint {
    @Autowired
    private Environment env;

    private Map<String, Object> cache = new ConcurrentHashMap<>();

    @RequestMapping(method = {RequestMethod.GET})
    public Object get() {
        return cache;
    }

    @PostConstruct
    public void init() {
        CompletableFuture.runAsync(() -> {
            String appName = env.getProperty("spring.application.name", "");
            try {
                JarDependencies dependencies = Analyzer.getAllPomInfo(appName);
                cache.put("appName", appName);
                cache.put("summerframeworkVersion", dependencies.getSummerframeworkVersion());
                cache.put("springBootVersion",dependencies.getSpringBootVersion());
                cache.put("springCloudVersion",dependencies.getSpringCloudVersion());
                List<HashMap<String, String>> list = new ArrayList<>();
                dependencies.getPomInfos().forEach(p -> {
                    if (p.getGroupId().equals("com.bkjk.platform.summerframework")
                            && p.getArtifactId().startsWith("platform-starter")) {
                        HashMap<String, String> kv = new HashMap<>();
                        kv.put(p.getArtifactId(), p.getVersion());
                        list.add(kv);
                    }
                });
                cache.put("using", list);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }
}
