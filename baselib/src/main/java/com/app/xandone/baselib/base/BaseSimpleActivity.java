package com.app.xandone.baselib.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * author: Admin
 * created on: 2020/8/12 10:34
 * description:
 */
public abstract class BaseSimpleActivity extends AppCompatActivity implements BaseInit {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        doBeforeSetContentView();
        setContentView(getLayout());
        init();
        initDataObserver();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
    }

    protected void initDataObserver() {


    }
}
