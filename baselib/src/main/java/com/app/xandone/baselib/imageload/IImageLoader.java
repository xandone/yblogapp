package com.app.xandone.baselib.imageload;

import android.content.Context;
import android.view.View;

import java.io.File;

import androidx.annotation.UiThread;

/**
 * @author: Admin
 * created on: 2020/8/11 16:24
 * description:
 */
public interface IImageLoader<T extends View> {
    void display(Context context, Object file, T view);

    void loadSource(Context context, Object file, SourceCallback callback);

    interface SourceCallback {
        @UiThread
        void onStart();

        @UiThread
        void onProgress(int progress);

        @UiThread
        void onDelivered(boolean isDisplaySuccess, File source);
    }
}
