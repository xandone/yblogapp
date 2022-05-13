package com.app.xandone.baselib.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.FileNameMap;
import java.net.URLConnection;


/**
 * author: Admin
 * created on: 2020/9/24 14:10
 * description:
 */
public class ImageUtils {

    /**
     * 适配 anroid10
     * 通过MediaStore保存，兼容AndroidQ，保存成功自动添加到相册数据库，无需再发送广播告诉系统插入相册
     *
     * @param context     context
     * @param sourceFile  源文件
     *                    saveFileName 保存的文件名
     * @param saveDirName picture子目录
     * @return 成功或者失败
     */
    public static void saveFile2SdCard(Context context, File sourceFile, String saveDirName) {
        String mimeType = getMimeType(sourceFile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String fileName = sourceFile.getName();
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DESCRIPTION, "This is an image");
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + saveDirName);
            ContentResolver contentResolver = context.getContentResolver();
            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri == null) {
                ToastUtils.showShort("保存失败");
                return;
            }
            OutputStream out = null;
            FileInputStream fis = null;
            try {
                out = contentResolver.openOutputStream(uri);
                fis = new FileInputStream(sourceFile);
                if (out != null) {
                    FileUtils.copy(fis, out);
                    ToastUtils.showShort("已保存至相册");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeIo(out, fis);
            }
        } else {
            //这个方法不会把getExternalFilesDir的图片刷新到相册
            MediaScannerConnection.scanFile(context, new String[]{sourceFile.getPath()},
                    new String[]{mimeType}, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
            ToastUtils.showShort("已保存至相册");
        }
    }


    private static String getMimeType(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(file.getName());
    }

    private static void closeIo(Closeable... closeables) {
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
