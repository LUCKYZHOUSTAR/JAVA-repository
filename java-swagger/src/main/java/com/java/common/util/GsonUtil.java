/*
                            _ooOoo_  
                           o8888888o  
                           88" . "88  
                           (| -_- |)  
                            O\ = /O  
                        ____/`---'\____  
                      .   ' \\| |// `.  
                       / \\||| : |||// \  
                     / _||||| -:- |||||- \  
                       | | \\\ - /// | |  
                     | \_| ''\---/'' | |  
                      \ .-\__ `-` ___/-. /  
                   ___`. .' /--.--\ `. . __  
                ."" '< `.___\_<|>_/___.' >'"".  
               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
                 \ \ `-. \_ __\ /__ _/ .-` / /  
         ======`-.____`-.___\_____/___.-`____.-'======  
                            `=---='  
  
         .............................................  
                  佛祖镇楼                  BUG辟易  
          佛曰:  
                  写字楼里写字间，写字间里程序员；  
                  程序人员写程序，又拿程序换酒钱。  
                  酒醒只在网上坐，酒醉还来网下眠；  
                  酒醉酒醒日复日，网上网下年复年。  
                  但愿老死电脑间，不愿鞠躬老板前；  
                  奔驰宝马贵者趣，公交自行程序员。  
                  别人笑我忒疯癫，我笑自己命太贱；  
                  不见满街漂亮妹，哪个归得程序员？
*/
package com.java.common.util;

import com.google.common.collect.Lists;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class GsonUtil {

    /**
     * 将实体转换为json
     *
     * @param obj 实体对象
     * @return 转换后的json
     */
    public static String toJson(Object obj) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create().toJson(obj);
    }

    /**
     * 是不是json对象
     *
     * @param element json元素
     * @return boolean值
     */
    public static boolean jsJsonObject(JsonElement element) {
        return element.isJsonObject();
    }

    /**
     * 是不是json对象
     *
     * @param json 转换的json
     * @return boolean值
     */
    public static boolean isJsonObject(String json) {
        return jsJsonObject(getAsJsonElement(json));
    }

    /**
     * 是否是json数组
     *
     * @param json 转换的json
     * @return boolean值
     */
    public static boolean isJsonArray(String json) {
        return isJsonArray(getAsJsonElement(json));
    }

    /**
     * 是否是json数组
     *
     * @param element json元素
     * @return boolean值
     */
    public static boolean isJsonArray(JsonElement element) {
        return element.isJsonArray();
    }

    /**
     * 获取jsonElement
     *
     * @param json 转换的json
     * @return json的元素
     */
    private static JsonElement getAsJsonElement(String json) {
        return new JsonParser().parse(json);
    }

    /**
     * 将json转换成实体
     *
     * @param json  转换的json
     * @param clazz 泛型规约
     * @return 规约的实体
     */
    public static <T> T json2Bean(String json, Class<T> clazz) {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.SSS").create().fromJson(json, clazz);
    }

    /**
     * 将json转换实体list
     *
     * @param json  转换的json
     * @param clazz 泛型规约
     * @return 规约的bean的实体
     */
    public static <T> List<T> json2BeanList(String json, Class<T> clazz) {
        JsonElement element = getAsJsonElement(json);
        List<T> list = Lists.newArrayList();
        if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                list.add(json2Bean(array.get(i).toString(), clazz));
            }
        } else if (element.isJsonObject()) {
            list.add(json2Bean(element.getAsJsonObject().toString(), clazz));
        }
        return list;
    }

    /**
     * 将json转换map
     *
     * @param json 转换的json
     * @return map对象
     */
    public static Map<String, String> json2Map(String json) {
        return new Gson().fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    /**
     * 将json转换为JsonArray
     *
     * @param json 转换的json
     * @return json数组
     */
    public static JsonArray getAsJsonArray(String json) {
        JsonElement element = getAsJsonElement(json);
        if (element.isJsonArray()) {
            return element.getAsJsonArray();
        } else if (element.isJsonObject()) {
            JsonArray array = new JsonArray();
            array.add(element.getAsJsonObject());
            return array;
        }
        return null;
    }

    /**
     * 将json转换为json对象
     *
     * @param json 转换的json
     * @return json对象
     */
    public static JsonObject getAsJsonObject(String json) {
        JsonElement element = getAsJsonElement(json);
        return element.isJsonObject() ? element.getAsJsonObject() : null;
    }

    /**
     * 使用Gson判断字符串是否是json
     *
     * @param str 要判断的字符串
     * @return 是否是json的boolean值
     */
    public static boolean isJson(String str) {
        try {
            new JsonParser().parse(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 转换为格式化的json
     *
     * @param obj
     * @return 格式化以后的json
     */
    public static String formatJson(Object obj) throws Exception {
        return new GsonBuilder().setPrettyPrinting().create().toJson(new JsonParser().parse(toJson(obj)));
    }

    /**
     * 格式化json
     *
     * @param json 非法师换的json
     * @return 格式化后的json数据
     * @throws Exception
     */
    public static String formatJson(String json) throws Exception {
        return new GsonBuilder().setPrettyPrinting().create().toJson(getAsJsonElement(json));
    }

    /**
     * 将map转换为bean
     *
     * @param map   属性map
     * @param clazz 泛型规约
     * @return 规约对象
     */
    @SuppressWarnings("rawtypes")
    public static <T> T map2Bean(Map map, Class<T> clazz) {
        return json2Bean(toJson(map), clazz);
    }

}
