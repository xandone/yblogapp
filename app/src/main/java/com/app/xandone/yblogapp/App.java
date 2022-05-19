package com.app.xandone.yblogapp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.yblogapp.base.ActManager;
import com.app.xandone.yblogapp.config.AppConfig;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.bugly.crashreport.CrashReport;


import java.util.List;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;

/**
 * author: Admin
 * created on: 2020/8/11 14:08
 * description:
 */
public class App extends Application {
    public static Application sContext;

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        init();
    }

    private void init() {
        //系统配置
        AppConfig.init(this, BuildConfig.DEBUG, BuildConfig.LOG_ENABLE);

        ActManager.getInstance().init(this);

        //Bugly
        CrashReport.initCrashReport(this, AppConfig.getBuglyId(), AppConfig.isDebug());

        boolean isNightMode = SpHelper.getDefaultBoolean(sContext, ISpKey.IS_NIGHT_MODE_KEY);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 判断Android程序是否在前台运行
     */
    public static boolean isForeground() {
        ActivityManager activityManager = (ActivityManager) sContext.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return false;
        }
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        String packageName = sContext.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
