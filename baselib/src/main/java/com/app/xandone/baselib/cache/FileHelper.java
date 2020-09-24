package com.app.xandone.baselib.cache;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import com.app.xandone.baselib.config.BaseConfig;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.RequiresApi;

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

    public static String getExternalStorageDirectory(Context context) {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
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

    public static boolean deleteDir(String path) {
        File file = new File(path);
        //判断是否待删除目录是否存在
        if (!file.exists()) {
            return false;
        }
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        for (String name : content) {
            File temp = new File(path, name);
            //判断是否是目录
            if (temp.isDirectory()) {
                //递归调用，删除目录里的内容
                deleteDir(temp.getAbsolutePath());
                //删除空目录
                temp.delete();
            } else {
                //直接删除文件
                if (!temp.delete()) {
                    System.err.println("Failed to delete " + name);
                }
            }
        }
        return true;
    }


}
