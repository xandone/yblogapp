package com.app.xandone.yblogapp.ui.code;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.widgetlib.utils.SpacesItemDecoration;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseListFragment;
import com.app.xandone.yblogapp.constant.IConstantKey;
import com.app.xandone.yblogapp.model.CodeModel;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.ui.articledetails.ArticleDetailsActivity;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * author: Admin
 * created on: 2020/8/31 17:55
 * description:
 */
public class CodeListFragment extends BaseListFragment {
    private CodeModel codeModel;

    private BaseQuickAdapter<CodeArticleBean, BaseViewHolder> mAdapter;
    private List<CodeArticleBean> datas;
    private int mType;
    private int mPage = 1;

    private static final int ROW = 10;
    public static final String TYPE = "type";

    public static CodeListFragment getInstance(int type) {
        CodeListFragment fragment = new CodeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void init(View view) {
        super.init(view);
        mType = getArguments().getInt(TYPE);
        datas = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<CodeArticleBean, BaseViewHolder>(R.layout.item_code_list, datas) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, CodeArticleBean codeArticleBean) {
                baseViewHolder.setText(R.id.code_title_tv, codeArticleBean.getTitle());
                baseViewHolder.setText(R.id.code_type_tv, codeArticleBean.getTypeName());
                baseViewHolder.setText(R.id.code_content_tv, codeArticleBean.getContent());
                baseViewHolder.setText(R.id.code_date_tv, codeArticleBean.getPostTime());
                ImageView codeCoverImg = baseViewHolder.getView(R.id.code_cover_img);
                if (TextUtils.isEmpty(codeArticleBean.getCoverImg())) {
                    codeCoverImg.setVisibility(View.GONE);
                } else {
                    codeCoverImg.setVisibility(View.VISIBLE);
                    ImageLoadHelper.getInstance().display(App.sContext, codeArticleBean.getCoverImg(), codeCoverImg);
                }
            }
        };
        recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        recycler.addItemDecoration(new SpacesItemDecoration(App.sContext, 10, 10, 10));
        recycler.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                startActivity(new Intent(mActivity, ArticleDetailsActivity.class)
                        .putExtra(IConstantKey.ID, datas.get(position).getArtId()));
            }
        });
    }

    @Override
    protected void initDataObserver() {
        codeModel = ModelProvider.getModel(mActivity, CodeModel.class, App.sContext);
    }

    @Override
    protected void lazyLoadData() {
        mPage = 1;
        requestData();
    }

    @Override
    protected void requestData() {
        getCodeDatas(false);
    }

    private void getCodeDatas(boolean isLoadMore) {
        codeModel.getCodeDatas(mPage, ROW, mType, isLoadMore, new IRequestCallback<List<CodeArticleBean>>() {
            @Override
            public void success(List<CodeArticleBean> beans) {
                datas = beans;
                mAdapter.setList(datas);
                onLoadFinish();
                if (!isLoadMore) {
                    finishRefresh();
                    if (beans.size() <= 0) {
                        onLoadEmpty();
                    }
                } else {
                    finishLoadMore();
                }
            }

            @Override
            public void error(String message, int statusCode) {
                onLoadStatus(statusCode);
            }
        });
    }

    @Override
    public void getData() {
        mPage = 1;
        getCodeDatas(false);
    }

    @Override
    public void getDataMore() {
        mPage++;
        getCodeDatas(true);
    }
}
