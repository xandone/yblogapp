package com.app.xandone.yblogapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * author: Admin
 * created on: 2020/8/11 11:21
 * description:
 */
public class ATest implements LifecycleEventObserver {
    public static final String TAG = ATest.class.getName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Log.d(TAG, "Atest====>onCreate..");
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_CREATE) {
            Log.d(TAG, "Atest====>onCreate..2222");
        }
    }
}
