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

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 解析Http请求的Form数据
 * 主要是jersey的文件上传
 */
public class FormRequestUtils {

    /**
     * 获取单个文件
     * 使用treeMap 获取第一个数据
     *
     * @param request http请求的request，主要是获取content-type
     * @param bytes   POST请求的body转换的的二进制数组
     * @return
     * @throws Exception
     */
    public static Map.Entry<String, byte[]> assemblyOneFile(HttpServletRequest request, byte[] bytes) throws Exception {
        TreeMap<String, byte[]> treeMap = new TreeMap<>(assemblyRequestFile(request, bytes));
        if (treeMap.size() > 0) {
            return treeMap.firstEntry();
        } else {
            throw new IOException("没有上传的文件");
        }
    }

    /**
     * 构建form的请求的参数
     *
     * @param request http请求的request，主要是获取content-type
     * @param bytes   POST请求的body转换的的二进制数组
     * @return 字段名为key，字段值为value的map
     * @throws Exception
     */
    public static Map<String, String> assemblyRequestForm(HttpServletRequest request, byte[] bytes) throws Exception {
        Map<String, String> params = Maps.newHashMap();
        assemblyRequest(request, bytes).entrySet().stream().filter(entry -> entry.getValue() instanceof String)
                .forEach(entry -> params.put(entry.getKey(), String.valueOf(entry.getValue())));
        return params;
    }


    /**
     * 构建请求的参数转换实体对象
     * 调用gson的转换
     *
     * @param request http请求的request，主要是获取content-type
     * @param bytes   POST请求的body转换的的二进制数组
     * @param clazz   clazz规约
     * @param <T>     提供无参数的构造方法
     * @return
     * @throws Exception
     */
    public static <T> T assemblyRequestForm(HttpServletRequest request, byte[] bytes, Class<T> clazz) throws Exception {
        return GsonUtil.map2Bean(assemblyRequestForm(request, bytes), clazz);
    }


    /**
     * 构建文件的请求参数
     *
     * @param request http请求的request，主要是获取content-type
     * @param bytes   POST请求的body转换的的二进制数组
     * @return 文件名作为key，文件的二进制数组作为value
     * @throws Exception
     */
    public static Map<String, byte[]> assemblyRequestFile(HttpServletRequest request, byte[] bytes) throws Exception {
        return assemblyRequest(request, bytes).entrySet().stream().filter(entry -> entry.getValue() instanceof byte[])
                .collect(Collectors.toMap(Map.Entry::getKey, en -> (byte[]) en.getValue()));
    }


    public static Map<String, Object> assemblyRequest(HttpServletRequest request, byte[] dataOrigin) throws Exception {
        Map<String, Object> values = Maps.newHashMap();
        /*状态码，表示没有特殊操作 **/
        final int NONE = 0;
        /*表示下一行要读到报头信息**/
        final int DATAHEADER = 1;
        /*表示下面要读的是上传文件和二进制数据**/
        final int FILEDATA = 2;
        /*表示下面要读到表单域的文本值**/
        final int FIELDDATA = 3;
        /* 对于post多个文件的表单，b作为原始数据的副本提供提取文件数据的操作 **/
        byte[] b;
        /* 请求消息类型 **/
        String contentType = request.getContentType();
        /*表单域的名称**/
        String fieldname = "";
        /*表单域的值**/
        String fieldvalue;
        String fileFormName = "";
        /*上传文件的真实名字**/
        String fileRealName;
        /* 分界符字符串 **/
        String boundary = "";
        /* 结束分界符字符串 **/
        String lastboundary = "";
        /*  在消息头类型中找到分界符的定义 */
        int pos = contentType.indexOf("boundary=");
        int pos2;
        if (pos != -1) {
            pos += "boundary=".length();
            /* 解析出分界符 */
            boundary = "--" + contentType.substring(pos);
            /* 得到结束分界符 */
            lastboundary = boundary + "--";
        }
        /* 起始状态为NONE */
        int state = NONE;
        /* 从字节数组中得到表示实体的字符串 */
        String reqcontent = new String(dataOrigin);
        /* 从字符串中得到输出缓冲流 */
        BufferedReader reqbuf = new BufferedReader(new StringReader(reqcontent));
        for (; ; ) {
            String line = reqbuf.readLine();
            if (line == null || line.equals(lastboundary)) {
                break;
            }
            switch (state) {
                case NONE:
                    if (line.startsWith(boundary)) {
                        /* 如果读到分界符，则表示下一行一个头信息 */
                        state = DATAHEADER;
                    }
                    break;
                case DATAHEADER:
                    pos = line.indexOf("filename=");
                    /* 先判断出这是一个文本表单域的头信息，还是一个上传文件的头信息 */
                    if (pos == -1) {
                        /* 如果是文本表单域的头信息，解析出表单域的名称 */
                        pos = line.indexOf("name=");
                        /* 1表示后面的"的占位  */
                        pos += "name=".length() + 1;
                        line = line.substring(pos);
                        int l = line.length();
                        /* 应该是"  */
                        line = line.substring(0, l - 1);
                        /* 表单域的名称放入fieldname */
                        fieldname = line;
                        /* 设置状态码，准备读取表单域的值 */
                        state = FIELDDATA;
                    } else {
                        /* 如果是文件数据的头，先存储这一行，用于在字节数组中定位 */
                        String temp = line;
                        /* 先解析出文件名 */
                        pos = line.indexOf("name=");
                        /* 1表示后面的"的占位 */
                        pos += "name=".length() + 1;
                        pos2 = line.indexOf("filename=");
                        /* 3表示";加上一个空格 */
                        fileFormName = line.substring(pos, pos2 - 3);
                        /* 1表示后面的"的占位 */
                        pos2 += "filename=".length() + 1;
                        line = line.substring(pos2);
                        int l = line.length();
                        line = line.substring(0, l - 1);
                        /* 对于IE浏览器的设置 */
                        pos2 = line.lastIndexOf("\\");
                        line = line.substring(pos2 + 1);
                        fileRealName = line;
                        /* 确定有文件被上传 */
                        if (fileRealName.length() != 0) {
                            /* 下面这一部分从字节数组中取出文件的数据 */
                            /* 复制原始数据以便提取文件 */
                            b = dataOrigin;
                            /* 定位行 */
                            pos = byteIndexOf(b, temp, 0);
                            /* 定位下一行，2 表示一个回车和一个换行占两个字节 */
                            b = subBytes(b, pos + temp.getBytes().length + 2, b.length);
                            /* 再读一行信息，是这一部分数据的Content-type */
                            line = reqbuf.readLine();
                            /* 设置文件输入流，准备写文件
                             字节数组再往下一行，4表示两回车换行占4个字节，本行的回车换行2个字节，Content-type的下
                             一行是回车换行表示的空行，占2个字节
                             得到文件数据的起始位置 */
                            b = subBytes(b, line.getBytes().length + 4, b.length);
                            /* 定位文件数据的结尾 */
                            pos = byteIndexOf(b, boundary, 0);
                            /* 取得文件数据 */
                            b = subBytes(b, 0, pos - 1);
                            values.put(fileFormName, fileRealName);
                            values.put(fileRealName, b);
                            state = FILEDATA;
                        }
                    }
                    break;
                case FIELDDATA:
                    /* 读取表单域的值 */
                    line = reqbuf.readLine();
                    fieldvalue = line;
                    values.put(fieldname, fieldvalue);
                    state = NONE;
                    break;
                case FILEDATA:
                    /* 如果是文件数据不进行分析，直接读过去 */
                    while ((!line.startsWith(boundary)) && (!line.startsWith(lastboundary))) {
                        line = reqbuf.readLine();
                        if (line.startsWith(boundary)) {
                            state = DATAHEADER;
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        return values;
    }

    private static byte[] subBytes(byte[] b, int from, int end) {
        byte[] result = new byte[end - from];
        System.arraycopy(b, from, result, 0, end - from);
        return result;
    }

    private static int byteIndexOf(byte[] b, String s, int start) {
        return byteIndexOf(b, s.getBytes(), start);
    }


    private static int byteIndexOf(byte[] b, byte[] s, int start) {
        int i;
        if (s.length == 0) {
            return 0;
        }
        int max = b.length - s.length;
        if (max < 0) {
            return -1;
        }
        if (start > max) {
            return -1;
        }
        if (start < 0) {
            start = 0;
        }
        /* 在b中找到s的第一个元素 */
        search:
        for (i = start; i <= max; i++) {
            if (b[i] == s[0]) {
                /* 找到了s中的第一个元素后，比较剩余的部分是否相等 */
                int k = 1;
                while (k < s.length) {
                    if (b[k + i] != s[k]) {
                        continue search;
                    }
                    k++;
                }
                return i;
            }
        }
        return -1;
    }
}
