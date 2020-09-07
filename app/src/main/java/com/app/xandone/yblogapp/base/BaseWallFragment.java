package com.app.xandone.yblogapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.xandone.baselib.base.BaseFrament;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.widgetlib.view.LoadingLayout;
import com.app.xandone.yblogapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * author: Admin
 * created on: 2020/9/1 10:52
 * description:有加载状态页的基类Fragment
 */
public abstract class BaseWallFragment extends BaseFrament implements ILoadingWall,
        LoadingLayout.OnReloadListener {
    @BindView(R.id.loadLayout)
    protected LoadingLayout loadLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frag_base_wall, container, false);
        FrameLayout walFrame = rootView.findViewById(R.id.wall_frame);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        View view = inflater.inflate(getLayout(), null);
        view.setLayoutParams(params);
        walFrame.addView(view);
        return rootView;
    }

    @Override
    protected void initButterKnife(View view) {
        super.initButterKnife(view);
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

    @Override
    public void onLoading() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.LOADING);
    }

    @Override
    public void onLoadEmpty() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.EMPTY);
    }

    @Override
    public void onLoadSeverError() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.SERVER_ERROR);
    }

    @Override
    public void onLoadNetError() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.NET_ERROR);
    }

    @Override
    public void onLoadFinish() {
        loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.FINISH);
    }

    @Override
    public void onLoadStatus(int statusCode) {
        loadLayout.setLoadingStatus(statusCode);
    }
}
