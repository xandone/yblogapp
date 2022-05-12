package com.app.xandone.yblogapp.base;

import android.os.Bundle;
import android.view.View;

import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.ui.code.IListAction;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * author: Admin
 * created on: 2020/9/2 09:27
 * description:
 */
public abstract class BaseListFragment<E> extends BaseWallFragment implements IRefreshCallback {
    @BindView(R.id.refreshLayout)
    protected SmartRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    protected RecyclerView recycler;

    protected List<E> mDatas;
    protected BaseQuickAdapter<E, BaseViewHolder> mAdapter;

    protected IListAction<E> mIListAction;

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

        mIListAction = new IListAction<E>() {

            @Override
            public void dealLoadSuccess(BaseResponse<List<E>> response, boolean isLoadMore) {
                if (mDatas == null || mAdapter == null) {
                    throw new NullPointerException("先初始化相关参数..");
                }
                onLoadFinish();
                if (!isLoadMore) {
                    finishRefresh();
                    if (response == null) {
                        onLoadNetError();
                        return;
                    }
                    if (SimpleUtils.isEmpty(response.getData())) {
                        onLoadEmpty();
                        return;
                    }
                    mDatas = response.getData();
                    mAdapter.setList(mDatas);
                } else {
                    if (mDatas.size() >= response.getTotal()) {
                        finishLoadNoMoreData();
                    } else {
                        finishLoadMore();
                        mDatas.addAll(response.getData());
                        mAdapter.setList(mDatas);
                    }
                }
            }

            @Override
            public void dealLoadFail(String message, int statusCode) {
                onLoadStatus(statusCode);
            }
        };

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
    public void finishLoadNoMoreData() {
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void unableLoadMore() {
        refreshLayout.setEnableLoadMore(false);
    }
}
