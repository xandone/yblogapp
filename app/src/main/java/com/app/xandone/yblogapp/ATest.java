package com.app.xandone.yblogapp;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * author: Admin
 * created on: 2020/8/11 11:21
 * description:
 */
public class ATest implements LifecycleObserver {
    public static final String TAG = ATest.class.getName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.d(TAG, "Atest====>onCreate..");
    }
}
