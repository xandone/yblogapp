package com.app.xandone.baselib.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.app.xandone.baselib.utils.KeyBoardUtils;
import com.app.xandone.baselib.utils.ProgressDialogHelper;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * author: Admin
 * created on: 2020/8/12 10:34
 * description:
 */
public abstract class BaseSimpleActivity extends AppCompatActivity implements IActivityInit, IApiLoading,
        IToastAction {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doBeforeSetContentView();
        initContentView();
        initButterKnife();
        init();
        initDataObserver();
        initSoftKeyBoard();
    }

    protected void initContentView() {
        setContentView(getLayout());
    }

    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    /**
     * {@link IActivityInit}
     */
    @Override
    public void doBeforeSetContentView() {

    }

    protected void initDataObserver() {


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
}
