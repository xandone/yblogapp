package com.app.xandone.yblogapp.ui.essay;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.widgetlib.utils.SizeUtils;
import com.app.xandone.widgetlib.utils.SpacesItemDecoration;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseListFragment;
import com.app.xandone.yblogapp.constant.IConstantKey;
import com.app.xandone.yblogapp.model.EssayModel;
import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.BannerBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.ui.articledetails.ArticleDetailsActivity;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

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
    private BannerImageAdapter<BannerBean> bannerAdapter;
    private List<BannerBean> bannerList;

    private static final int ROW = 10;

    public static Essayfragment getInstance() {
        return new Essayfragment();
    }

    @Override
    public void init(View view) {
        super.init(view);
        datas = new ArrayList<>();
        bannerList = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<EssayArticleBean, BaseViewHolder>(R.layout.item_essay_list, datas) {
            @Override
            protected void convert(@NotNull BaseViewHolder baseViewHolder, EssayArticleBean essayArticleBean) {
                baseViewHolder.setText(R.id.essay_title_tv, essayArticleBean.getTitle());
                baseViewHolder.setText(R.id.essay_content_tv, essayArticleBean.getContent());
                baseViewHolder.setText(R.id.essay_date_tv, essayArticleBean.getPostTime());
                ImageView codeCoverImg = baseViewHolder.getView(R.id.essay_cover_img);
                RecyclerView imgRecycler = baseViewHolder.getView(R.id.img_recycler);
                List<String> coverData = JsonUtils.json2List(essayArticleBean.getCoverImg(), new TypeToken<List<String>>() {
                }.getType());
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
        initBanner();
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

        requestData();
    }

    private void initBanner() {
        Banner banner = new Banner(mActivity);
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                SizeUtils.dp2px(App.sContext, 200));
        banner.setLayoutParams(params);
        bannerAdapter = new BannerImageAdapter<BannerBean>(bannerList) {

            @Override
            public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                if (!mActivity.isFinishing()) {
                    ImageLoadHelper.getInstance().display(mActivity, bannerList.get(position).getImgUrl(), holder.imageView);
                }

            }
        };
        banner.setAdapter(bannerAdapter).addBannerLifecycleObserver(this).setIndicator(new CircleIndicator(mActivity));
        mAdapter.addHeaderView(banner);

        bannerAdapter.setOnBannerListener(new OnBannerListener<BannerBean>() {
            @Override
            public void OnBannerClick(BannerBean bannerBean, int position) {
                startActivity(new Intent(mActivity, ArticleDetailsActivity.class)
                        .putExtra(IConstantKey.ID, bannerList.get(position).getArticelId())
                        .putExtra(IConstantKey.TYPE, ArticleDetailsActivity.TYPE_ESSAY)
                        .putExtra(IConstantKey.TITLE, bannerList.get(position).getTitle())
                );
            }
        });
    }

    @Override
    protected void requestData() {
        getCodeDatas(1, false);
        getBannerDatas();
    }

    private void getBannerDatas() {
        essayModel.getBannerDatas(new IRequestCallback<List<BannerBean>>() {
            @Override
            public void success(List<BannerBean> bannerBeans) {
                bannerList.clear();
                bannerList.addAll(bannerBeans);
                bannerAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(String message, int statusCode) {

            }
        });
    }

    private void getCodeDatas(int page, boolean isLoadMore) {
        essayModel.getEssayDatas(page, ROW, new IRequestCallback<BaseResponse<List<EssayArticleBean>>>() {
            @Override
            public void success(BaseResponse<List<EssayArticleBean>> response) {
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
                    datas = response.getData();
                    mAdapter.setList(datas);
                } else {
                    datas.addAll(response.getData());
                    mAdapter.setList(datas);
                    if (datas.size() >= response.getTotal()) {
                        finishLoadNoMoreData();
                    } else {
                        finishLoadMore();
                    }
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
        getCodeDatas(1, false);
        getBannerDatas();
    }

    @Override
    public void getDataMore() {
        getCodeDatas(datas.size() / ROW + 1, true);
    }
}
