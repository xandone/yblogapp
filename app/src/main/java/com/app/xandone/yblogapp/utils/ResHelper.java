package com.app.xandone.yblogapp.utils;

import android.graphics.drawable.Drawable;


import com.app.xandone.yblogapp.App;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

/**
 * @author: xiao
 * created on: 2022/7/7 14:30
 * description:资源辅助类
 */
public class ResHelper {
    @ColorInt
    public static int getColor(@ColorRes int color) {
        return ContextCompat.getColor(App.sContext, color);
    }

    public static Drawable getDrawable(@DrawableRes int drawable) {
        return ContextCompat.getDrawable(App.sContext, drawable);
    }
}
