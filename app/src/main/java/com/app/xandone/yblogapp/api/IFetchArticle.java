package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.ApkBean;
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
public interface IFetchArticle extends IFetchLive {

    Disposable getCodeDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback);

    Disposable getCodeDetails(String id, IRequestCallback<CodeDetailsBean> callback);

    Disposable getEssayDatas(int page, int row, IRequestCallback<BaseResponse<List<EssayArticleBean>>> callback);

    Disposable getEssayDetails(String id, IRequestCallback<EssayDetailsBean> callback);

    Disposable getBannerDatas(IRequestCallback<List<BannerBean>> callback);

    Disposable getCodeTypeDatas(IRequestCallback<List<CodeTypeBean>> callback);

    Disposable getLastApkInfo(IRequestCallback<ApkBean> callback);


}
