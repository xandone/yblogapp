package com.app.xandone.baselib.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.xandone.baselib.config.BaseConfig;

/**
 * author: Admin
 * created on: 2020/8/11 15:40
 * description:
 */
public class SpHelper {

    public static SharedPreferences getDefaultSp(Context context) {
        return context.getSharedPreferences(BaseConfig.appName, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSp(Context context, String spName) {
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public static void save2DefaultSp(Context context, String key, String msg) {
        getDefaultSp(context).edit().putString(key, msg).apply();
    }

    public static void save2Sp(Context context, String spName, String key, String msg) {
        getSp(context, spName).edit().putString(key, msg).apply();
    }

    /**
     * 清除默认sp下的某个key值
     *
     * @param context
     * @param keys
     */
    public static void clearDefaultSp(Context context, String... keys) {
        for (String key : keys) {
            getDefaultSp(context).edit().remove(key).apply();
        }
    }

    /**
     * 清除默认的sp下的所有值
     *
     * @param context
     */
    public static void clearDefaultSp(Context context) {
        getDefaultSp(context).edit().clear().apply();
    }

    /**
     * 清除指定的sp下的某个key值
     *
     * @param context
     * @param names
     */
    public static void clearSp(Context context, String... names) {
        for (String name : names) {
            getSp(context, name).edit().clear().apply();
        }
    }

    /**
     * 清除全部sp
     *
     * @param context
     * @param names
     */
    public static void clearAllSp(Context context, String... names) {
        clearDefaultSp(context);
        clearSp(context, names);
    }
}
