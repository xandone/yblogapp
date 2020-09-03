package com.app.xandone.yblogapp.base;

import android.os.Bundle;
import android.view.View;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.yblogapp.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * author: Admin
 * created on: 2020/9/2 09:27
 * description:
 */
public abstract class BaseListFragment extends BaseWallFragment implements IRefreshCallback {
    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    protected RecyclerView recycler;

    protected boolean mIsLoadedData;

    @Override
    public int getLayout() {
        return R.layout.frag_base_list;
    }

    @Override
    public void init(View view) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                getData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                getDataMore();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void finishRefresh() {
        refreshLayout.finishRefresh();
    }

    @Override
    public void finishLoadMore() {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsLoadedData) {
            lazyLoadData();
            mIsLoadedData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsLoadedData = false;
    }

    protected abstract void lazyLoadData();
}
