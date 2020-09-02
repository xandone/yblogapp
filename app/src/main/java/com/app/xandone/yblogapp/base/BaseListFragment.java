package com.app.xandone.yblogapp.base;

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

    @Override
    public int getLayout() {
        return R.layout.frag_base_list;
    }

    @Override
    public void init(View view) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                LogHelper.d("11111onRefresh.............1");
                getData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                LogHelper.d("11111onLoadMore.............2");
                getDataMore();
            }
        });
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
}
