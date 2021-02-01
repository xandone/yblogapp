package com.app.xandone.baselib.cache;

import android.content.Context;

import java.io.File;

/**
 * author: Admin
 * created on: 2021/2/1 10:13
 * description:
 */
public class ApkCache {
    public static String getApkFilePath(Context context) {
        return FileHelper.getExternalFilesDir(context) + File.separator + "apkfile";
    }
}
