package com.app.xandone.yblogapp.config;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.app.xandone.baselib.config.BaseConfig;
import com.app.xandone.yblogapp.BuildConfig;

/**
 * author: Admin
 * created on: 2020/8/11 14:07
 * description:
 */
public class AppConfig {
    private static final String APP_NAME = "";
    public static int SCREEN_WIDTH = -1;
    public static int SCREEN_HEIGHT = -1;

    public static void init(Application application, boolean isDebug, boolean isLogAble) {
        BaseConfig.init(application, APP_NAME, isDebug, isLogAble);
        getScreenSize(application);
    }

    public static boolean isLogEnable() {
        return BuildConfig.LOG_ENABLE;
    }

    public static void getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (windowManager == null) {
            return;
        }
        Display display = windowManager.getDefaultDisplay();
        display.getMetrics(dm);
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int t = SCREEN_HEIGHT;
            SCREEN_HEIGHT = SCREEN_WIDTH;
            SCREEN_WIDTH = t;
        }
    }
}
