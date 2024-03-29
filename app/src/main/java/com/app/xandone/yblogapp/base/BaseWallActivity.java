package com.app.xandone.yblogapp.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.xandone.baselib.base.BaseActivity;
import com.app.xandone.widgetlib.view.LoadingLayout;
import com.app.xandone.yblogapp.R;
import com.gyf.immersionbar.ImmersionBar;

import java.util.Objects;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import butterknife.BindView;

/**
 * author: Admin
 * created on: 2020/9/1 10:52
 * description:有加载状态页的基类Fragment
 */
public abstract class BaseWallActivity<VB extends ViewBinding> extends BaseActivity<VB> implements ILoadingWall,
        LoadingLayout.OnReloadListener {
    @BindView(R.id.loadLayout)
    protected LoadingLayout loadLayout;

    protected Toolbar mToolbar;

    private ImmersionBar mImmersionBar;

    @Override
    protected void initContentView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.act_base_wall, null);
        FrameLayout walFrame = rootView.findViewById(R.id.wall_frame);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        View view = getLayout();
        view.setLayoutParams(params);
        walFrame.addView(view);
        setContentView(rootView);

        initToolbar();

        initImmersionBar();
    }

    @CallSuper
    @Override
    public void initView() {
        loadLayout.setOnReloadListener(this);
        onLoading();
    }

    /**
     * 重新加载按钮
     */
    @Override
    public void reLoadData() {
        onLoading();
        requestData();
    }

    /**
     * 加载数据，实现该方法
     */
    protected abstract void requestData();

    protected void initToolbar() {
        mToolbar = findViewById(com.app.xandone.baselib.R.id.toolbar);

        setToolBar(getTitle(), com.app.xandone.baselib.R.drawable.back_ic);
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
        mToolbar.setNavigationIcon(com.app.xandone.baselib.R.drawable.back_ic);
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

    /**
     * {@link ILoadingWall}
     */
    @Override
    public void onLoading() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.LOADING);
    }

    /**
     * {@link ILoadingWall}
     */
    @Override
    public void onLoadEmpty() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.EMPTY);
    }

    /**
     * {@link ILoadingWall}
     */
    @Override
    public void onLoadSeverError() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.SERVER_ERROR);
    }

    /**
     * {@link ILoadingWall}
     */
    @Override
    public void onLoadNetError() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.NET_ERROR);
    }

    /**
     * {@link ILoadingWall}
     */
    @Override
    public void onLoadFinish() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.FINISH);
    }

    /**
     * {@link ILoadingWall}
     */
    @Override
    public void onLoadStatus(int statusCode) {
        loadLayout.setLoadingStatus(statusCode);
    }
}
