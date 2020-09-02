package com.app.xandone.yblogapp.ui.code;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseListFragment;
import com.app.xandone.yblogapp.model.ArticleModel;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * author: Admin
 * created on: 2020/8/31 17:55
 * description:
 */
public class CodeListFragment extends BaseListFragment {
    private ArticleModel articleModel;
    private MutableLiveData<List<CodeArticleBean>> liveData;
    private int mPage = 1;
    private static final int ROW = 10;

    private BaseQuickAdapter<CodeArticleBean, BaseViewHolder> mAdapter;
    private List<CodeArticleBean> datas;

    @Override
    public void init(View view) {
        super.init(view);
        datas = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<CodeArticleBean, BaseViewHolder>(R.layout.item_code_list, datas) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, CodeArticleBean codeArticleBean) {
                baseViewHolder.setText(R.id.code_title_tv, codeArticleBean.getTitle());
            }
        };
        recycler.setAdapter(mAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    protected void initDataObserver() {
        articleModel = ModelProvider.getModel(mActivity, ArticleModel.class, App.sContext);
        liveData = articleModel.getArticleDatas(mPage, ROW, false);
        liveData.observe(this, new Observer<List<CodeArticleBean>>() {
            @Override
            public void onChanged(List<CodeArticleBean> articleBeans) {
                datas.clear();
                datas.addAll(articleBeans);
                mAdapter.notifyDataSetChanged();
                onLoadFinish();
                finishRefresh();
                finishLoadMore();
            }
        });
    }

    @Override
    protected void requestData() {
        getArticleDatas(false);
    }

    private void getArticleDatas(boolean isLoadMore) {
        articleModel.getArticleDatas(mPage, ROW, isLoadMore);
    }

    @Override
    public void getData() {
        mPage = 1;
        getArticleDatas(false);
    }

    @Override
    public void getDataMore() {
        LogHelper.d("more....");
        mPage++;
        getArticleDatas(true);
    }
}
