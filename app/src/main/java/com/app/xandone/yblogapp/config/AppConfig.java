package com.app.xandone.yblogapp.config;

import android.app.Application;

import com.app.xandone.baselib.config.BaseConfig;

/**
 * author: Admin
 * created on: 2020/8/11 14:07
 * description:
 */
public class AppConfig {
    private static final String APP_NAME = "";

    public static void init(Application application, boolean isDebug) {
        BaseConfig.init(application, APP_NAME, isDebug);
    }
}
