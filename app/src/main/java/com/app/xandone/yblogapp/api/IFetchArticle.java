package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.BannerBean;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;
import io.reactivex.disposables.Disposable;

/**
 * author: Admin
 * created on: 2020/8/12 17:59
 * description:
 */
public interface IFetchArticle {
    Disposable getCodeDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback);

    MediatorLiveData<List<CodeArticleBean>> getCodeArticleLiveData();

    Disposable getCodeDetails(String id, IRequestCallback<CodeDetailsBean> callback);

    MediatorLiveData<CodeDetailsBean> getCodeDetailsLiveData();

    Disposable getEssayDatas(int page, int row, boolean isLoadMore, IRequestCallback<BaseResponse<List<EssayArticleBean>>> callback);

    MediatorLiveData<BaseResponse<List<EssayArticleBean>>> getEssayArticleLiveData();

    Disposable getEssayDetails(String id, IRequestCallback<EssayDetailsBean> callback);

    MediatorLiveData<EssayDetailsBean> getEssayDetailsLiveData();

    Disposable getBannerDatas(IRequestCallback<List<BannerBean>> callback);

    MediatorLiveData<List<BannerBean>> getBannerLiveData();

    Disposable getCodeTypeDatas(IRequestCallback<List<CodeTypeBean>> callback);

    MediatorLiveData<List<CodeTypeBean>> getCodeTypeLiveData();
}
