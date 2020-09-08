package com.app.xandone.baselib.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.xandone.baselib.config.BaseConfig;

import androidx.annotation.NonNull;

/**
 * author: Admin
 * created on: 2020/8/11 15:40
 * description:
 */
public class SpHelper {

    public static SharedPreferences getDefaultSp(Context context) {
        return context.getSharedPreferences(BaseConfig.appName, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSpByName(Context context, @NonNull String spName) {
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    /**
     * 保存到默认的sp
     *
     * @param context
     * @param key
     * @param msg
     */
    public static void save2DefaultSp(Context context, @NonNull String key, String msg) {
        getDefaultSp(context).edit().putString(key, msg).apply();
    }

    /**
     * 保存到指定sp
     *
     * @param context
     * @param spName
     * @param key
     * @param msg
     */
    public static void save2Sp(Context context, @NonNull String spName, @NonNull String key, String msg) {
        getSpByName(context, spName).edit().putString(key, msg).apply();
    }

    /**
     * 获取默认的sp下的String
     *
     * @param context
     * @param key
     * @return
     */
    public String getDefaultString(Context context, @NonNull final String key) {
        return getDefaultString(context, key, "");
    }

    public String getDefaultString(Context context, @NonNull final String key, @NonNull final String defaultValue) {
        return getDefaultSp(context).getString(key, defaultValue);
    }

    /**
     * 获取指定的sp下的String
     *
     * @param context
     * @param spName
     * @param key
     * @return
     */
    public String getStringByName(Context context, String spName, @NonNull final String key) {
        return getStringByName(context, spName, key, "");
    }

    public String getStringByName(Context context, String spName, @NonNull final String key, @NonNull final String defaultValue) {
        return getSpByName(context, spName).getString(key, defaultValue);
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
            getSpByName(context, name).edit().clear().apply();
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
