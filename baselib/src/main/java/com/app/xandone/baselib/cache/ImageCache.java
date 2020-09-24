package com.app.xandone.baselib.cache;


import android.content.Context;
import android.os.Build;

import java.io.File;


/**
 * author: Admin
 * created on: 2020/9/24 10:17
 * description:
 */
public class ImageCache {
    public static String getImageCache(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return FileHelper.getExternalFilesDir(context);
        } else {
            return FileHelper.getExternalStorageDirectory(context) + File.separator + "yblog";
        }
    }
}
