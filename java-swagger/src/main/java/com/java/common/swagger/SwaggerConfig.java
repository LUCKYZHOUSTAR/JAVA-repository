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
package com.java.common.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * SwaggerConfig
 */
@ConfigurationProperties("swagger")
public class SwaggerConfig {

    /**
     * swagger全局参数
     */
    private List<SwaggerParameter> parameters;
    /**
     * swagger的全局响应
     */
    private Map<String, String> responses;
    /**
     * 根路径隐射到swagger
     */
    private boolean rootEnabled = true;

    public List<SwaggerParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<SwaggerParameter> parameters) {
        this.parameters = parameters;
    }

    public boolean isRootEnabled() {
        return rootEnabled;
    }

    public void setRootEnabled(boolean rootEnabled) {
        this.rootEnabled = rootEnabled;
    }

    public Map<String, String> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, String> responses) {
        this.responses = responses;
    }
}
