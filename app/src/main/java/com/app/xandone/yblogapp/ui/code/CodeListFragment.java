package com.app.xandone.yblogapp.ui.code;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.xandone.baselib.imageload.ImageLoadHelper;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.widgetlib.utils.SpacesItemDecoration;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseListFragment;
import com.app.xandone.yblogapp.constant.IConstantKey;
import com.app.xandone.yblogapp.model.CodeModel;
import com.app.xandone.yblogapp.model.base.BaseResponse;
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
public class CodeListFragment extends BaseListFragment<CodeArticleBean> {
    private CodeModel codeModel;
    private int mType;

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
        mDatas = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<CodeArticleBean, BaseViewHolder>(R.layout.item_code_list, mDatas) {
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
                        .putExtra(IConstantKey.ID, mDatas.get(position).getArtId())
                        .putExtra(IConstantKey.TYPE, ArticleDetailsActivity.TYPE_CODE)
                        .putExtra(IConstantKey.TITLE, mDatas.get(position).getTitle())
                );
            }
        });

        codeModel = ModelProvider.getModel(mActivity, CodeModel.class, App.sContext);
    }

    @Override
    protected void lazyLoadData() {
        super.lazyLoadData();
        requestData();
    }

    @Override
    protected void requestData() {
        getCodeDatas(1, false);
    }

    private void getCodeDatas(int page, boolean isLoadMore) {
        codeModel.getCodeDatas(page, ROW, mType, new IRequestCallback<BaseResponse<List<CodeArticleBean>>>() {
            @Override
            public void success(BaseResponse<List<CodeArticleBean>> response) {
                mIListAction.dealLoadSuccess(response, isLoadMore);
            }

            @Override
            public void error(String message, int statusCode) {
                mIListAction.dealLoadFail(message, statusCode, isLoadMore);
            }
        });
    }

    @Override
    public void getData() {
        getCodeDatas(1, false);
    }

    @Override
    public void getDataMore() {
        getCodeDatas(mDatas.size() / ROW + 1, true);
    }

}
