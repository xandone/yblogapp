package com.app.xandone.baselib.config;

import android.content.Context;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.log.LogHelper;


/**
 * author: Admin
 * created on: 2020/8/11 11:56
 * description:
 */
public class BaseConfig {
    //app名称
    public static String appName;

    public static void init(Context context, String appName) {
        BaseConfig.appName = appName;

        LogHelper.init(LogHelper.ENGINE_LOGGER);

        ImageLoadHelper.getInstance().initEngine(ImageLoadHelper.ENGINE_GLIDE);
    }
}
