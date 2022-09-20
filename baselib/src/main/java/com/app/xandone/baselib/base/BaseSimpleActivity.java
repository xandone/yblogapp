package com.app.xandone.baselib.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.app.xandone.baselib.utils.KeyBoardUtils;
import com.app.xandone.baselib.utils.ProgressDialogHelper;


import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;
import butterknife.ButterKnife;

/**
 * author: Admin
 * created on: 2020/8/12 10:34
 * description:
 */
public abstract class BaseSimpleActivity<VB extends ViewBinding> extends AppCompatActivity implements IActivityInit, IApiLoading,
        IToastAction, IClickAction {

    protected VB mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doBeforeSetContentView();
        initContentView();
        initButterKnife();
        initView();
        init();
        initSoftKeyBoard();
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    protected void initContentView() {
        setContentView(getLayout());
    }

    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public <T extends View> T findView(int id) {
        return getDelegate().findViewById(id);
    }

    /**
     * {@link IActivityInit}
     */
    @Override
    public void doBeforeSetContentView() {

    }

    private void initSoftKeyBoard() {
        getAndroidContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtils.hideKeyboard(getCurrentFocus());
            }
        });
    }

    protected ViewGroup getAndroidContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

    protected void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        KeyBoardUtils.hideKeyboard(getCurrentFocus());
        super.startActivityForResult(intent, requestCode);
    }

    /**
     * {@link IApiLoading}
     */
    @Override
    public void showApiLoading() {
        ProgressDialogHelper.getInstance().showLoading(this);
    }

    /**
     * {@link IApiLoading}
     */
    @Override
    public void cancleApiLoading() {
        ProgressDialogHelper.getInstance().dimissLoading();
    }


    protected boolean isRegisterEventBus() {
        return false;
    }


    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        //8.0bug，半透明状态下设置方向出现crash，8.1版本已修复
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O && isTranslucentOrFloating()) {
            return;
        }
        super.setRequestedOrientation(requestedOrientation);
    }

    private boolean isTranslucentOrFloating() {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mBinding != null) {
            mBinding = null;
        }
    }
}
