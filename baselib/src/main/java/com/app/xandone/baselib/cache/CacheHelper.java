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
     * 清除ExternalCacheDir文件夹
     */
    public static void clearExternalCacheDir(Context context) {
        FileHelper.deleteDir(FileHelper.getExternalCacheDir(context));
    }

    /**
     * 清除所有缓存,包括sp缓存
     *
     * @param context
     * @param names
     */
    public static void clearAllCache(Context context, String... names) {
        clearSpCache(context, names);
        clearExternalCacheDir(context);
    }

    /**
     * 获取全部缓存文件大小
     *
     * @param context
     * @return
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize;
        try {
            cacheSize = FileHelper.getFolderSize(FileHelper.getExternalCacheDirFile(context));
        } catch (Exception e) {
            e.printStackTrace();
            return FileHelper.getFormatSize(0);
        }
        return FileHelper.getFormatSize(cacheSize);
    }

    /**
     * 是否有缓存
     *
     * @param context
     * @return
     */
    public static boolean isHaveCache(Context context) {
        long cacheSize;
        try {
            cacheSize = FileHelper.getFolderSize(FileHelper.getExternalCacheDirFile(context));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return cacheSize <= 0;
    }
}
