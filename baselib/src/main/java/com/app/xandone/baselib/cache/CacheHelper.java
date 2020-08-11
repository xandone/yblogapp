package com.app.xandone.baselib.cache;

import android.content.Context;

/**
 * author: Admin
 * created on: 2020/8/11 15:52
 * description:
 */
public class CacheHelper {

    /**
     * 清除所有sp缓存
     *
     * @param context
     * @param names
     */
    public static void clearSpCache(Context context, String... names) {
        SpHelper.clearAllSp(context, names);
    }

    /**
     * 清除所有缓存
     *
     * @param context
     * @param names
     */
    public static void clearAllCache(Context context, String... names) {
        clearSpCache(context, names);
    }
}
