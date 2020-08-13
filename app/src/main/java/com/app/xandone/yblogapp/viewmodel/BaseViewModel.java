package com.app.xandone.yblogapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

/**
 * author: Admin
 * created on: 2020/8/12 11:39
 * description:
 */
public abstract class BaseViewModel extends ViewModel implements LifecycleOwner {

    private LifecycleOwner mLifecycleOwner;

    protected abstract void onCreate();

    <T extends BaseViewModel> T attachLifecycleOwner(LifecycleOwner lifecycleOwner) {
        T current = (T) this;
        if (mLifecycleOwner != null) {
            return current;
        }

        this.mLifecycleOwner = lifecycleOwner;

        onCreate();

        return current;

    }


    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleOwner.getLifecycle();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
