package com.app.xandone.yblogapp.ui.essay;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.widgetlib.utils.SpacesItemDecoration;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseListFragment;
import com.app.xandone.yblogapp.constant.IConstantKey;
import com.app.xandone.yblogapp.model.EssayModel;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
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
import androidx.recyclerview.widget.RecyclerView;

/**
 * author: Admin
 * created on: 2020/9/3 16:20
 * description:
 */
public class Essayfragment extends BaseListFragment {

    private EssayModel essayModel;

    private BaseQuickAdapter<EssayArticleBean, BaseViewHolder> mAdapter;
    private List<EssayArticleBean> datas;
    private int mPage = 1;

    private static final int ROW = 10;

    public static Essayfragment getInstance() {
        Essayfragment fragment = new Essayfragment();
        return fragment;
    }

    @Override
    public void init(View view) {
        super.init(view);
        datas = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<EssayArticleBean, BaseViewHolder>(R.layout.item_essay_list, datas) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, EssayArticleBean essayArticleBean) {
                baseViewHolder.setText(R.id.essay_title_tv, essayArticleBean.getTitle());
                baseViewHolder.setText(R.id.essay_content_tv, essayArticleBean.getContent());
                baseViewHolder.setText(R.id.essay_date_tv, essayArticleBean.getPostTime());
                ImageView codeCoverImg = baseViewHolder.getView(R.id.essay_cover_img);
                RecyclerView imgRecycler = baseViewHolder.getView(R.id.img_recycler);
                List<String> coverData = JsonUtils.json2List(essayArticleBean.getCoverImg());
                if (coverData == null || coverData.size() == 0) {
                    codeCoverImg.setVisibility(View.GONE);
                    imgRecycler.setVisibility(View.GONE);
                } else if (coverData.size() == 1) {
                    codeCoverImg.setVisibility(View.VISIBLE);
                    imgRecycler.setVisibility(View.GONE);
                    ImageLoadHelper.getInstance().display(App.sContext, coverData.get(0), codeCoverImg);
                } else {
                    codeCoverImg.setVisibility(View.GONE);
                    imgRecycler.setVisibility(View.VISIBLE);
                    BaseQuickAdapter<String, BaseViewHolder> imgAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_img, coverData) {
                        @Override
                        protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
                            ImageView img = baseViewHolder.getView(R.id.item_essay_cover_img);
                            ImageLoadHelper.getInstance().display(App.sContext, s, img);
                        }
                    };
                    imgRecycler.setAdapter(imgAdapter);
                    imgRecycler.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
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
                        .putExtra(IConstantKey.ID, datas.get(position).getEssayId())
                        .putExtra(IConstantKey.TYPE, ArticleDetailsActivity.TYPE_ESSAY)
                        .putExtra(IConstantKey.TITLE, datas.get(position).getTitle())
                );
            }
        });
    }

    @Override
    protected void initDataObserver() {
        essayModel = ModelProvider.getModel(mActivity, EssayModel.class, App.sContext);

        getData();
    }

    @Override
    protected void requestData() {
        getCodeDatas(false);
    }

    private void getCodeDatas(boolean isLoadMore) {
        essayModel.getEssayDatas(mPage, ROW, isLoadMore, new IRequestCallback<List<EssayArticleBean>>() {
            @Override
            public void success(List<EssayArticleBean> beans) {
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
