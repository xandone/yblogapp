package com.app.xandone.baselib.base;

import android.content.Intent;
import android.os.Bundle;

import com.app.xandone.baselib.utils.ProgressDialogHelper;
import com.app.xandone.baselib.utils.ProgressDialogUtil;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * author: Admin
 * created on: 2020/8/12 10:34
 * description:
 */
public abstract class BaseSimpleActivity extends AppCompatActivity implements IActivityInit, IApiLoading {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doBeforeSetContentView();
        initContentView();
        initButterKnife();
        init();
        initDataObserver();
    }

    protected void initContentView() {
        setContentView(getLayout());
    }

    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    public void doBeforeSetContentView() {

    }

    protected void initDataObserver() {


    }

    protected void startActivity(Class activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void showApiLoading() {
        ProgressDialogHelper.getInstance().showLoading(this);
    }

    @Override
    public void cancleApiLoading() {
        ProgressDialogHelper.getInstance().dimissLoading();
    }
}
