package com.app.xandone.yblogapp.base;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.xandone.baselib.base.BaseActivity;
import com.app.xandone.baselib.view.filter.FilterFragment;
import com.app.xandone.widgetlib.view.LoadingLayout;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.databinding.ActBaseDrawListBinding;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;


/**
 * author: Admin
 * created on: 2021/5/11 10:28
 * description:有筛选项的BaseList
 */
public abstract class BaseDrawListActivity extends BaseActivity<ActBaseDrawListBinding>
        implements IRefreshCallback, ILoadingWall, LoadingLayout.OnReloadListener {

    protected TextView tvRight;

    private ImmersionBar mImmersionBar;

    protected FilterFragment mFilterFragment;

    @Override
    public View getLayout() {
        mBinding = ActBaseDrawListBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @CallSuper
    @Override
    public void initView() {
        tvRight = findViewById(R.id.tv_right);

        onLoading();


        mBinding.loadLayout.setOnReloadListener(this);

        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                getApiData();
            }
        });
        mBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                getApiDataMore();
            }
        });

        mFilterFragment = new FilterFragment();

        initDrawLayoutData();
    }


    private MenuItem itemFilter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_filtter, menu);
        itemFilter = menu.findItem(R.id.toolbar_filter_id);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar_filter_id) {
            openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openDrawer() {
        getSupportFragmentManager().beginTransaction().replace(R.id.drawer_content, mFilterFragment).commit();
        mBinding.drawerLayout.openDrawer(mBinding.drawerContent);
    }

    protected void setRightText(CharSequence cs) {
        if (tvRight != null) {
            tvRight.setText(cs);
        }
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
     * 重新加载数据，实现该方法
     */
    protected abstract void requestData();

    /**
     * 加载中
     */
    @Override
    public void onLoading() {
        mBinding.loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.LOADING);
    }

    /**
     * 空数据
     */
    @Override
    public void onLoadEmpty() {
        mBinding.loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.EMPTY);
    }

    /**
     * 服务器异常
     */
    @Override
    public void onLoadSeverError() {
        mBinding.loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.SERVER_ERROR);
    }

    /**
     * 网络错误
     */
    @Override
    public void onLoadNetError() {
        mBinding.loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.NET_ERROR);
    }

    /**
     * 加载结束
     */
    @Override
    public void onLoadFinish() {
        mBinding.loadLayout.setLoadingStatus(LoadingLayout.ILoadingStatus.FINISH);
    }

    /**
     * 修改加载状态
     *
     * @param statusCode
     */
    @Override
    public void onLoadStatus(int statusCode) {
        mBinding.loadLayout.setLoadingStatus(statusCode);
    }

    public void startRefresh() {
        mBinding.refreshLayout.autoRefresh();
    }

    @Override
    public void finishRefresh() {
        mBinding.refreshLayout.finishRefresh();
    }

    @Override
    public void finishLoadMore() {
        mBinding.refreshLayout.finishLoadMore();
    }

    @Override
    public void finishLoadNoMoreData() {
        mBinding.refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void unableLoadMore() {
        mBinding.refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void unableRefresh() {
        mBinding.refreshLayout.setEnableRefresh(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void filterCimmit(Map<String, Object> map) {
        mBinding.drawerLayout.closeDrawer(mBinding.drawerContent);
        if (map == null) {
            return;
        }
        int type = (int) map.get(FilterFragment.TYPE_KEY);
        if (map.get(FilterFragment.IS_FILTER) != null) {
            boolean isFilter = (boolean) map.get(FilterFragment.IS_FILTER);
            if (itemFilter != null) {
                if (isFilter) {
                    itemFilter.setTitle("已筛选");
                } else {
                    itemFilter.setTitle("筛选");
                }
            }
        }
        setFilterMap(type, map);
    }

    /**
     * 初始化筛选项的数据
     */
    protected abstract void initDrawLayoutData();

    protected abstract void setFilterMap(int type, Map<String, Object> map);


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(mBinding.drawerContent)) {
            mBinding.drawerLayout.closeDrawer(mBinding.drawerContent);
            return;
        }
        super.onBackPressed();
    }

    public static final class SubKey {
        public static final int BASE_CASH_AMOUNT = 6;
    }
}

