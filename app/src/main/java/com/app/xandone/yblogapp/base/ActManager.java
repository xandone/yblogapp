package com.app.xandone.yblogapp.base;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.app.xandone.baselib.base.BaseSimpleActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.fragment.app.FragmentActivity;

/**
 * author: Admin
 * created on: 2021/5/7 10:02
 * description:
 */
public class ActManager implements Application.ActivityLifecycleCallbacks {

    private final ArrayMap<String, BaseSimpleActivity> mActivitySet = new ArrayMap<>();

    private Application mApplication;
    /**
     * 最后一个可见 Activity 标记
     */
    private String mLastVisibleTag;
    /**
     * 最后一个不可见 Activity 标记
     */
    private String mLastInvisibleTag;

    private ActManager() {
    }

    public static ActManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final ActManager INSTANCE = new ActManager();
    }

    public void init(Application application) {
        mApplication = application;
        mApplication.registerActivityLifecycleCallbacks(this);
    }

    public ArrayMap<String, BaseSimpleActivity> getAllActivity() {
        return mActivitySet;
    }

    /**
     * 获取栈顶的 Activity
     */
    public FragmentActivity getTopActivity() {
        return mActivitySet.get(mLastVisibleTag);
    }

    /**
     * 判断当前应用是否处于前台状态
     */
    public boolean isForeground() {
        // 如果最后一个可见的 Activity 和最后一个不可见的 Activity 是同一个的话
        if (mLastVisibleTag.equals(mLastInvisibleTag)) {
            return false;
        }
        FragmentActivity activity = getTopActivity();
        return activity != null;
    }

    /**
     * 销毁所有的 Activity
     */
    public void finishAllActivities() {
        finishAllActivities((Class<? extends Activity>) null);
    }

    /**
     * 销毁所有的 Activity，除这些 Class 之外的 Activity
     */
    @SafeVarargs
    public final void finishAllActivities(Class<? extends Activity>... classArray) {
        String[] keys = mActivitySet.keySet().toArray(new String[]{});
        for (String key : keys) {
            Activity activity = mActivitySet.get(key);
            if (activity != null && !activity.isFinishing()) {
                boolean whiteClazz = false;
                if (classArray != null) {
                    for (Class<? extends Activity> clazz : classArray) {
                        if (activity.getClass() == clazz) {
                            whiteClazz = true;
                        }
                    }
                }
                // 如果不是白名单上面的 Activity 就销毁掉
                if (!whiteClazz) {
                    activity.finish();
                    mActivitySet.remove(key);
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        mLastVisibleTag = getObjectTag(activity);
        if (activity instanceof FragmentActivity) {
            mActivitySet.put(getObjectTag(activity), (BaseSimpleActivity) activity);
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        mLastVisibleTag = getObjectTag(activity);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        mLastVisibleTag = getObjectTag(activity);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        mLastInvisibleTag = getObjectTag(activity);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        mLastInvisibleTag = getObjectTag(activity);
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        mActivitySet.remove(getObjectTag(activity));
        mLastInvisibleTag = getObjectTag(activity);
        if (getObjectTag(activity).equals(mLastVisibleTag)) {
            // 清除当前标记
            mLastVisibleTag = null;
        }
    }

    /**
     * 获取一个对象的独立无二的标记
     */
    private static String getObjectTag(Object object) {
        // 对象所在的包名 + 对象的内存地址
        return object.getClass().getName() + Integer.toHexString(object.hashCode());
    }
}
