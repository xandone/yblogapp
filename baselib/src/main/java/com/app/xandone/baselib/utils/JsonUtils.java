package com.app.xandone.baselib.utils;

import com.google.gson.Gson;

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
}
