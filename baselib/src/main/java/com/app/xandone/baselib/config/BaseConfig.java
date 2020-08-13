package com.app.xandone.baselib.config;

import android.app.Application;
import android.content.Context;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.ToastUtils;


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

    public static void init(Application application, String appName, boolean isDebug) {
        BaseConfig.appName = appName;

        //初始化日志库
        LogHelper.init(LogHelper.ENGINE_LOGGER, isDebug);
        //初始化图片引擎
        ImageLoadHelper.getInstance().initEngine(ImageLoadHelper.ENGINE_GLIDE);
        //初始化toast，主要是获取application
        ToastUtils.init(application);
    }
}
