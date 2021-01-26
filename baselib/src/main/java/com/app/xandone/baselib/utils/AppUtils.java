package com.app.xandone.baselib.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * author: Admin
 * created on: 2021/1/26 15:03
 * description:
 */
public class AppUtils {

    private static Application sApp;

    public static void init(Application application) {
        AppUtils.sApp = application;
    }

    public static String getPackageName() {
        return sApp.getPackageName();
    }

    public static String getAppName() {
        return getAppName(getPackageName());
    }

    public static String getAppName(final String packageName) {
        try {
            PackageManager pm = sApp.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getAppVersionName() {
        return getAppVersionName(sApp.getPackageName());
    }

    public static String getAppVersionName(final String packageName) {
        try {
            PackageManager pm = sApp.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAppVersionCode() {
        return getAppVersionCode(sApp.getPackageName());
    }

    public static int getAppVersionCode(final String packageName) {
        try {
            PackageManager pm = sApp.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static void installApk(Context context, String path) {
        File file = new File(path);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ////通过FileProvider创建一个content类型的Uri
            uri = FileProvider.getUriForFile(sApp, "com.app.xandone.yblogapp.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

}
