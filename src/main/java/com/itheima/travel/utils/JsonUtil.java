package com.itheima.travel.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author LloydLai
 * @version 1.0
 * @Description: TODO
 * @date Created in 2019/3/26 11:05
 * @Modified By: TODO
 */
public class JsonUtil {
    /**
     * @param jsonStr         json字符串
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类型
     * @return T
     * @Description: 定义一个List<User>，向里面添加两次user，先调用writeValueAsString方法打印出json，
     * 再调用readJson方法，这不仅可以转换泛型List<T>，还可以用于其它集合，比如Map<K,V>等等。
     * List<User> list = readJson(json, List.class, User.class);
     * ObjectMapper可以让对象与JSON之间相互转换，除此之外Jackson还提供了JsonGenerator 和JsonParser 这
     * 两个类，它们可以更细粒度化。调用ObjectMapper的writeValueAsString和readValue方法，最终还是会交给
     * JsonGenerator 和JsonParser 去处理
     * @author LloydLai
     * @date 2019/3/26 11:05
     */
    public static <T> T readJson(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

        return mapper.readValue(jsonStr, javaType);

    }
}
