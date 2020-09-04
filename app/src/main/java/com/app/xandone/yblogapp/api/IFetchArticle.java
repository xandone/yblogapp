package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 17:59
 * description:
 */
public interface IFetchArticle {
    MediatorLiveData<List<CodeArticleBean>> getCodeArticleLiveData();

    MediatorLiveData<CodeDetailsBean> getCodeDetailsLiveData();

    void getCodeDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback);

    void getCodeDetails(String id, IRequestCallback<CodeDetailsBean> callback);

}
