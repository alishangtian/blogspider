package com.alishangtian.blogspider.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @Description TODO
 * @ClassName GsonUtils
 * @Author alishangtian
 * @Date 2020/4/25 12:47
 * @Version 0.0.1
 */
@Log4j2
public class GsonUtils {

    /**
     * 默认的objectMapper  线程安全的,多线程调用效率低
     */
    private static Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyyy-MM-dd HH:mm:ss").create();

    /**
     * @param json
     * @return
     */
    public static JsonObject toJson(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    /**
     * 格式化json
     *
     * @param json
     * @return
     */
    public static String formatJson(String json) {
        List<String> jsons = new ArrayList<>();
        Arrays.stream(json.split("\n")).forEach(s -> {
            if (s.endsWith("),")) {
                String[] ss = s.split("'");
                jsons.add(ss[0] + "'" + ss[1] + "',");
            } else {
                jsons.add(s);
            }
        });
        return StringUtils.join(jsons, "\n");
    }

    /**
     * 转换json(对效率要求比较高的服务请自己 new 个 mapper)
     *
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj) {
        return toJSONString(obj, gson);
    }

    public static String toJSONString(Object obj, Gson gson) {
        if (obj == null) {
            return null;
        }
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            log.error("JSONUtils toJSONString failed ! obj :{}", obj.toString(), e);
            return null;
        }
    }

    /**
     * 有格式的
     *
     * @param obj
     * @return
     */
    public static String toJSONStringPretty(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            log.error("JSONUtils toJSONStringPretty failed ! obj :{}", obj.toString(), e);
            return null;
        }
    }

    /**
     * 字符串转对象 (对效率要求比较高的服务请使用自己的mapper)
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return parseObject(json, clazz, gson);
    }

    public static <T> T parseObject(String json, Class<T> clazz, Gson gson) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            log.error("JSONUtils parseObject failed ! json :{}", json, e);
            return null;
        }
    }

    public static <T> T parseObject(String json, Type typeReference) {
        if (StringUtils.isEmpty(json) || typeReference == null) {
            return null;
        }
        try {
            return gson.fromJson(json, typeReference);
        } catch (JsonSyntaxException e) {
            log.error("JSONUtils parseObject failed ! json :{}", json, e);
            return null;
        }
    }
}
