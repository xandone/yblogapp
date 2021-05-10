package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.model.bean.BannerBean;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;

/**
 * author: Admin
 * created on: 2021/5/7 09:44
 * description:
 */
public interface IFetchLive {

    MediatorLiveData<List<CodeArticleBean>> getCodeArticleLiveData();

    MediatorLiveData<CodeDetailsBean> getCodeDetailsLiveData();

    MediatorLiveData<BaseResponse<List<EssayArticleBean>>> getEssayArticleLiveData();

    MediatorLiveData<EssayDetailsBean> getEssayDetailsLiveData();

    MediatorLiveData<List<BannerBean>> getBannerLiveData();

    MediatorLiveData<List<CodeTypeBean>> getCodeTypeLiveData();

    MediatorLiveData<ApkBean> getLastLiveApkInfo();
}
