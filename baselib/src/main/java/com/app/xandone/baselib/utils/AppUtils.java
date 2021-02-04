package com.app.xandone.baselib.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

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

    public static void installApp(Context context, final File file) {
        if (!XFileUtils.isFileExists(file)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authority = "com.app.xandone.yblogapp.fileprovider";
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        if (uri == null) {
            return;
        }
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(uri, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (windowManager == null) {
            return 0;
        }
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        return dm.widthPixels;
    }

}
