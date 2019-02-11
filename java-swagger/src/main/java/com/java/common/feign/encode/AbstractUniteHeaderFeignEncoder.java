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
package com.java.common.feign.encode;

import feign.RequestTemplate;

import java.util.Map;

/**
 * AbstractUniteHeaderFeignEncoder
 * feign请求的header的统一编码器
 */
public abstract class AbstractUniteHeaderFeignEncoder implements FeignEncoder {
    /**
     * 编码
     *
     * @param template feign请求的Template
     * @throws Exception
     */
    @Override
    public void encode(RequestTemplate template) {
        getUniteHeaders().forEach(template::header);
    }

    /**
     * 获取统一添加的header
     *
     * @return 返回header的map
     */
    protected abstract Map<String, String> getUniteHeaders();
}
