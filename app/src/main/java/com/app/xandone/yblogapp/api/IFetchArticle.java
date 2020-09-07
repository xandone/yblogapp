package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 17:59
 * description:
 */
public interface IFetchArticle {
    void getCodeDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback);

    MediatorLiveData<List<CodeArticleBean>> getCodeArticleLiveData();

    void getCodeDetails(String id, IRequestCallback<CodeDetailsBean> callback);

    MediatorLiveData<CodeDetailsBean> getCodeDetailsLiveData();

    void getEssayDatas(int page, int row, boolean isLoadMore, IRequestCallback<List<EssayArticleBean>> callback);

    MediatorLiveData<List<EssayArticleBean>> getEssayArticleLiveData();

    void getEssayDetails(String id, IRequestCallback<EssayDetailsBean> callback);

    MediatorLiveData<EssayDetailsBean> getEssayDetailsLiveData();
}
