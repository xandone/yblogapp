package com.app.xandone.baselib.config;

import android.app.Application;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.AppUtils;
import com.app.xandone.baselib.utils.ToastHelper;


/**
 * author: Admin
 * created on: 2020/8/11 11:56
 * description:
 */
public class BaseConfig {
    /**
     * app名称
     */
    public static String appName;

    public static Application sApp;

    public static void init(Application application, String appName, boolean isDebug, boolean isLogAble) {
        BaseConfig.appName = appName;
        BaseConfig.sApp = application;

        //初始化日志库
        LogHelper.init(LogHelper.ENGINE_LOGGER, isLogAble);
        //初始化图片加载引擎
        ImageLoadHelper.getInstance().initEngine(ImageLoadHelper.ENGINE_GLIDE);
        //初始化toast，主要是获取application
        ToastHelper.init(application);
        //初始化App工具包
        AppUtils.init(application);
    }
}
