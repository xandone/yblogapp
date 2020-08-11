package com.app.xandone.baselib.imageload;

import android.content.Context;
import android.view.View;

/**
 * @author: Admin
 * created on: 2020/8/11 16:24
 * description:
 */
public interface ImageLoaderInf<T extends View> {
    void display(Context context, Object file, T view);
}
