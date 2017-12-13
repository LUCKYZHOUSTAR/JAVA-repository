package com.lucky.jetty.http;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Author:chaoqiang.zhou
 * @Description:http://blog.csdn.net/u011179993/article/details/47131773
 * http://blog.csdn.net/column/details/httpclient.html
 * @Date:Create in 12:42 2017/12/13
 */
public class HttpTest {
    public static void main(String[] args) throws IOException, URISyntaxException {
        example();
    }


    /**
     * 创建httpclient实例
     * 创建httpmethod 方法实例 ，最常用的是HttpGet,HttpPost 类
     * httpclient 通过execute方法提交Get 或者Post 请求
     * 使用CloseableHttpResponse 来接受服务器返回的状态信息和实体信息
     * 关闭连接
     * <p>
     * 作者：起个名忒难
     * 链接：http://www.jianshu.com/p/95ada83cee56
     * 來源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void example() throws URISyntaxException, IOException {
        //创建httpclient实例，采用默认的参数配置
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //通过URIBuilder类创建URI
        URI uri = new URIBuilder().setScheme("http")
                .setHost("www.baidu.com")
                .build();

        HttpGet get = new HttpGet(uri);   //使用Get方法提交

        //请求的参数配置，分别设置连接池获取连接的超时时间，连接上服务器的时间，服务器返回数据的时间
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(3000)
                .setConnectTimeout(3000)
                .setSocketTimeout(3000)
                .build();
        //配置信息添加到Get请求中
        get.setConfig(config);
        //通过httpclient的execute提交 请求 ，并用CloseableHttpResponse接受返回信息
        CloseableHttpResponse response = httpClient.execute(get);
        //服务器返回的状态
        int statusCode = response.getStatusLine().getStatusCode();
        //判断返回的状态码是否是200 ，200 代表服务器响应成功，并成功返回信息
        if (statusCode == HttpStatus.SC_OK) {
            //EntityUtils 获取返回的信息。官方不建议使用使用此类来处理信息
            System.out.println("Demo.example -------->" + EntityUtils.toString(response.getEntity(), Consts.UTF_8));
        } else {
            System.out.println("Demo.example -------->" + "获取信息失败");
        }

    }


    public void test1() throws Exception {
        URI uri = new URIBuilder().setScheme("http")
                .setHost("www.google.com")
                .setPath("/search")
                .setParameter("q", "httpclient")
                .setParameter("", "")
                .build();
        HttpGet get = new HttpGet(uri);
//除了使用URIBuilder工具类来创建连接，也可以直接采用字符串来定义uri
        String url = "http://www.google.com/search?hl=en&q=httpclient&btnG=Google+Search&aq=f&oq";
        HttpGet get1 = new HttpGet(url);
//效果也是一样的

    }

}
