package com.app.xandone.yblogapp;

import android.app.Application;
import android.content.Context;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.yblogapp.config.AppConfig;


import androidx.multidex.MultiDex;

/**
 * author: Admin
 * created on: 2020/8/11 14:08
 * description:
 */
public class App extends Application {
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        init();
    }

    private void init() {
        AppConfig.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
