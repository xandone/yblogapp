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
