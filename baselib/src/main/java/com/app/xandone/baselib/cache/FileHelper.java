package com.app.xandone.baselib.cache;

import android.content.Context;
import android.os.Environment;

import com.app.xandone.baselib.config.BaseConfig;
import com.app.xandone.baselib.log.LogHelper;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

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

    public static File getExternalFilesDirFile(Context context) {
        return context.getExternalFilesDir(null);
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

    public static File getExternalCacheDirFile(Context context) {
        return context.getExternalCacheDir();
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
        if (content == null) {
            return true;
        }
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
                    LogHelper.d("Failed to delete " + name);
                }
            }
        }
        return true;
    }


    public static long getFolderSize(File file) throws Exception {
        if (!file.exists()) {
            return 0;
        }
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList == null) {
                return 0;
            }
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化文件单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

}
