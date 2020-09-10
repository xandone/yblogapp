package com.app.xandone.baselib.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Admin
 * created on: 2020/8/11 16:06
 * description:
 */
public class JsonUtils {
    private static final Gson GSON = new Gson();

    public static <T> T json2Obj(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static String obj2Json(Object o) {
        return GSON.toJson(o);
    }

    public static <E> List<E> json2List(String json, Type type) {
        try {
            return GSON.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }
}
