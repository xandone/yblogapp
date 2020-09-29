package com.app.xandone.baselib.cache;

import android.content.Context;
import android.os.Environment;

/**
 * author: Admin
 * created on: 2020/8/11 15:52
 * description:
 */
public class CacheHelper {


    /**
     * 清除默认的sp下的某个key值
     *
     * @param context
     * @param key
     */
    public static void clearDefaultSp(Context context, String key) {
        SpHelper.clearDefaultSp(context, key);
    }


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
     * 清除ExternalFilesDir文件夹
     */
    public static void clearExternalFilesDir(Context context) {
        FileHelper.deleteDir(FileHelper.getExternalFilesDir(context));
    }

    /**
     * 清除所有缓存,包括sp缓存
     *
     * @param context
     * @param names
     */
    public static void clearAllCache(Context context, String... names) {
        clearSpCache(context, names);
        clearExternalFilesDir(context);
    }

    /**
     * 获取全部缓存文件大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = FileHelper.getFolderSize(FileHelper.getExternalFilesDirFile(context));
        return FileHelper.getFormatSize(cacheSize);
    }
}
