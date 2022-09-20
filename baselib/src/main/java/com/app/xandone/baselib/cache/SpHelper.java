package com.app.xandone.baselib.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.app.xandone.baselib.config.BaseConfig;

import androidx.annotation.NonNull;

/**
 * author: Admin
 * created on: 2020/8/11 15:40
 * description:
 */
public class SpHelper {

    /**
     * 默认的sp，文件名：app名
     * /data/data/<包名>/shared_prefs/yblog
     *
     * @param context
     * @return
     */
    public static SharedPreferences getDefaultSp(Context context) {
        return context.getSharedPreferences(BaseConfig.appName, Context.MODE_PRIVATE);
    }

    /**
     * 指定name的sp，文件名：name
     * <p>
     * /data/data/<包名>/shared_prefs/name
     *
     * @param context
     * @param spName
     * @return
     */
    public static SharedPreferences getSpByName(Context context, @NonNull String spName) {
        return context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }


    /**
     * 默认的sp，文件名：app名
     *
     * @param context
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void save2DefaultSp(Context context, @NonNull String key, T value) {
        SharedPreferences.Editor editor = getDefaultSp(context).edit();
        if (value == null) {
            editor.remove(key).apply();
        } else if (value instanceof String) {
            editor.putString(key, (String) value).apply();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value).apply();
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value).apply();
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value).apply();
        }
    }

    /**
     * 获取默认sp存的值，文件名：app名
     *
     * @param context
     * @param keyword
     * @param defValue
     * @param <T>
     * @return
     */
    public static <T> T getDefaultValue(Context context, String keyword, T defValue) {
        SharedPreferences sp = getDefaultSp(context);
        T value;
        if (defValue instanceof String) {
            String s = sp.getString(keyword, (String) defValue);
            value = (T) s;
        } else if (defValue instanceof Integer) {
            Integer i = sp.getInt(keyword, (Integer) defValue);
            value = (T) i;
        } else if (defValue instanceof Boolean) {
            Boolean b = sp.getBoolean(keyword, (Boolean) defValue);
            value = (T) b;
        } else if (defValue instanceof Long) {
            Long l = sp.getLong(keyword, (Long) defValue);
            value = (T) l;
        } else if (defValue instanceof Float) {
            Float f = sp.getFloat(keyword, (Float) defValue);
            value = (T) f;
        } else {
            value = defValue;
        }
        return value;
    }

    /**
     * 指定name的sp，文件名：name
     *
     * @param context
     * @param spName
     * @param key
     * @param value
     * @param <T>
     */
    public static <T> void save2SpByName(Context context, @NonNull String spName, @NonNull String key, T value) {
        SharedPreferences.Editor editor = getSpByName(context, spName).edit();
        if (value == null) {
            editor.remove(key).apply();
        } else if (value instanceof String) {
            editor.putString(key, (String) value).apply();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value).apply();
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value).apply();
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value).apply();
        }
    }

    /**
     * 指定name的sp，文件名：name
     *
     * @param context
     * @param spName
     * @param keyword
     * @param defValue
     * @param <T>
     * @return
     */
    public static <T> T getValueByName(Context context, @NonNull String spName, String keyword, T defValue) {
        SharedPreferences sp = getDefaultSp(context);
        T value;
        if (defValue instanceof String) {
            String s = sp.getString(keyword, (String) defValue);
            value = (T) s;
        } else if (defValue instanceof Integer) {
            Integer i = sp.getInt(keyword, (Integer) defValue);
            value = (T) i;
        } else if (defValue instanceof Boolean) {
            Boolean b = sp.getBoolean(keyword, (Boolean) defValue);
            value = (T) b;
        } else if (defValue instanceof Long) {
            Long l = sp.getLong(keyword, (Long) defValue);
            value = (T) l;
        } else if (defValue instanceof Float) {
            Float f = sp.getFloat(keyword, (Float) defValue);
            value = (T) f;
        } else {
            value = defValue;
        }
        return value;
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
     * 清除默认的sp下的某个key值
     *
     * @param context
     */
    public static void clearDefaultSp(Context context, String key) {
        getDefaultSp(context).edit().remove(key).apply();
    }


    /**
     * 清除指定的sp下的所有值
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
        if (names != null && names.length > 0) {
            clearSp(context, names);
        }
    }
}
