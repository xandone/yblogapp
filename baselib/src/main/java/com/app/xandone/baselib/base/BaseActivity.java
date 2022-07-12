package com.app.xandone.baselib.base;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.xandone.baselib.R;
import com.gyf.immersionbar.ImmersionBar;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;


/**
 * author: Admin
 * created on: 2020/8/12 11:03
 * description:
 */
public abstract class BaseActivity<VB extends ViewBinding> extends BaseSimpleActivity<VB> {

    protected Toolbar mToolbar;

    private ImmersionBar mImmersionBar;

    @Override
    protected void initContentView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.base_toolbar_layout, null);
        FrameLayout content = rootView.findViewById(R.id.content);
        View view = getLayout();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        content.addView(view);
        setContentView(rootView);
        mToolbar = findViewById(R.id.toolbar);

        initToolbar();

        initImmersionBar();
    }

    protected void initToolbar() {
        setToolBar(getTitle(), R.drawable.back_ic);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setToolBar(CharSequence title, int icon) {
        setToolBar(title);
        mToolbar.setNavigationIcon(icon);
    }

    protected void setToolBar(CharSequence title) {
        mToolbar.setTitle(title);
        mToolbar.setNavigationIcon(R.drawable.back_ic);
    }


    /**
     * 沉浸式
     */
    private void initImmersionBar() {
        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();

            // 设置标题栏沉浸
            if (mToolbar != null) {
                ImmersionBar.setTitleBar(this, mToolbar);
            }
        }
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected boolean isStatusBarEnabled() {
        return true;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    public ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = statusBarConfig();
        }
        return mImmersionBar;
    }


    /**
     * 初始化沉浸式状态栏
     */
    @NonNull
    protected ImmersionBar statusBarConfig() {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(true)
                // 指定导航栏背景颜色
//                .navigationBarColor(android.R.color.white)
                // 状态栏字体和导航栏内容自动变色，必须指定状态栏颜色和导航栏颜色才可以自动变色
                .autoDarkModeEnable(true, 0.2f);
    }
}
