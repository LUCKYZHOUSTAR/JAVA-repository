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
package com.java.common.feign;

import com.java.common.feign.decode.SelectFeignDecoder;
import com.java.common.feign.encode.SelectFeignEncoder;
import feign.RequestTemplate;
import feign.Response;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * AbstractQueryFeignEncodeDecode
 * 抽象的根据query参数来判断是否进行加密解密
 */
public abstract class AbstractQueryFeignEncodeDecode
        implements SelectFeignEncoder, SelectFeignDecoder {

    /**
     * 是否解码
     *
     * @param response
     * @return
     */
    @Override
    public boolean isDecode(Response response) {
        Map<String, Collection<String>> headers = response.request().headers();
        return !CollectionUtils.isEmpty(headers) && headers.containsKey(getQueryKey())
                && headers.get(getQueryKey()).contains(getQueryValue());
    }

    /**
     * 是否解码
     *
     * @param template
     * @return
     */
    @Override
    public boolean isEncode(RequestTemplate template) {
        Collection<String> collection = template.queries().get(getQueryKey());
        if (!CollectionUtils.isEmpty(collection) && collection.contains(getQueryValue())) {
            template.header(getQueryKey(), getQueryValue());
            return true;
        }
        return false;
    }

    /**
     * Query 里面decodencode的值
     * 根据该值来选择使用哪个加密解密器进行处理
     *
     * @return 用户自定义的解码值
     */
    protected abstract String getQueryValue();

    /**
     * query的key值
     *
     * @return
     */
    protected abstract String getQueryKey();
}
