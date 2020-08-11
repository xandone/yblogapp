package com.app.xandone.baselib.cache;

import android.content.Context;
import android.os.Environment;

import com.app.xandone.baselib.config.BaseConfig;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * author: Admin
 * created on: 2020/8/11 11:32
 * description:
 */
public class FileHelper {

    /**
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    public static String getExternalFilesDir(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    public static String getExternalFilesDirDcim(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    public static String getExternalCacheDir(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取app缓存文件夹位置
     *
     * @param context
     * @return
     */
    public static String getAppCacheDir(Context context) {
        File cacheDir = new File(getExternalCacheDir(context) + File.separator + BaseConfig.appName);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir.getPath();
    }


    public static String readAssetsFile(String filename, Context context) {
        try {
            InputStream inputStream = context.getAssets().open(filename);
            byte[] bytes = new byte[1024];
            int read;
            StringBuilder content = new StringBuilder();
            while ((read = inputStream.read(bytes, 0, bytes.length)) != -1) {
                content.append(new String(bytes, 0, read));
            }
            closeIo(inputStream);
            return content.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void closeIo(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
