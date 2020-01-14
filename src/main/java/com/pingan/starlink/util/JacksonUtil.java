package com.pingan.starlink.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    // Convert Object to String
    public static String bean2Json(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    // Convert string to simple Object
    public static <T> T json2Bean(String jsonStr, Class<T> objClass)
            throws IOException {
        return mapper.readValue(jsonStr, objClass);
    }

    // Convert string to List<Object>
    public static <T> T json2Bean(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) throws IOException {
        if (jsonStr == null) {
            return null;
        }
        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        return mapper.readValue(jsonStr, javaType);

    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    public static String formatJson(String jsonString) {

        int level = 0;

        //存放格式化的json字符串

        StringBuffer jsonForMatStr = new StringBuffer();

        for (int index = 0; index < jsonString.length(); index++) {//将字符串中的字符逐个按行输出

            //获取s中的每个字符
            char c = jsonString.charAt(index);

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }

            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }

        }
        return jsonForMatStr.toString();
    }
}
