package com.app.xandone.yblogapp.model.repository;


import android.util.Log;

import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.model.bean.BannerBean;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;
import com.app.xandone.yblogapp.rx.BaseSubscriber;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.rx.RxHelper;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;
import io.reactivex.disposables.Disposable;

/**
 * author: Admin
 * created on: 2020/8/12 17:19
 * description:
 */
public class CodeRepository implements IFetchArticle {

    private MediatorLiveData<List<CodeArticleBean>> mArtsLiveData;

    private MediatorLiveData<CodeDetailsBean> mCodeDetailsLiveData;

    private MediatorLiveData<BaseResponse<List<EssayArticleBean>>> mEssayLiveData;

    private MediatorLiveData<EssayDetailsBean> mEssayDetailsLiveData;

    private MediatorLiveData<List<BannerBean>> mBannerLiveData;

    private MediatorLiveData<List<CodeTypeBean>> mCodeTypeLiveData;

    private MediatorLiveData<ApkBean> mLiveApkBean;

    @Override
    public MediatorLiveData<List<CodeArticleBean>> getCodeArticleLiveData() {
        if (mArtsLiveData == null) {
            mArtsLiveData = new MediatorLiveData<>();
        }
        return mArtsLiveData;
    }

    @Override
    public MediatorLiveData<CodeDetailsBean> getCodeDetailsLiveData() {
        if (mCodeDetailsLiveData == null) {
            mCodeDetailsLiveData = new MediatorLiveData<>();
        }
        return mCodeDetailsLiveData;
    }

    @Override
    public MediatorLiveData<BaseResponse<List<EssayArticleBean>>> getEssayArticleLiveData() {
        if (mEssayLiveData == null) {
            mEssayLiveData = new MediatorLiveData<>();
        }
        return mEssayLiveData;
    }

    @Override
    public MediatorLiveData<EssayDetailsBean> getEssayDetailsLiveData() {
        if (mEssayDetailsLiveData == null) {
            mEssayDetailsLiveData = new MediatorLiveData<>();
        }
        return mEssayDetailsLiveData;
    }


    @Override
    public MediatorLiveData<List<BannerBean>> getBannerLiveData() {
        if (mBannerLiveData == null) {
            mBannerLiveData = new MediatorLiveData<>();
        }
        return mBannerLiveData;
    }


    @Override
    public MediatorLiveData<List<CodeTypeBean>> getCodeTypeLiveData() {
        if (mCodeTypeLiveData == null) {
            mCodeTypeLiveData = new MediatorLiveData<>();
        }
        return mCodeTypeLiveData;
    }

    public CodeRepository() {
    }

    @Override
    public Disposable getCodeDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getCodeDatas(page, row, type)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<CodeArticleBean>>() {
                    @Override
                    public void onSuccess(List<CodeArticleBean> articleBeans) {
                        if (mArtsLiveData.getValue() == null) {
                            mArtsLiveData.setValue(articleBeans);
                            return;
                        }
                        if (!isLoadMore) {
                            mArtsLiveData.setValue(articleBeans);
                        } else {
                            List<CodeArticleBean> list = mArtsLiveData.getValue();
                            list.addAll(articleBeans);
                            mArtsLiveData.setValue(list);
                        }
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public Disposable getCodeDetails(String id, IRequestCallback<CodeDetailsBean> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getCodeDetails(id).compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose()).subscribeWith(new BaseSubscriber<List<CodeDetailsBean>>() {
                    @Override
                    public void onSuccess(List<CodeDetailsBean> detailsBeans) {
                        mCodeDetailsLiveData.setValue(detailsBeans.get(0));
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }


    @Override
    public Disposable getEssayDatas(int page, int row, IRequestCallback<BaseResponse<List<EssayArticleBean>>> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getEssayDatas(page, row)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleBaseResponse())
                .subscribeWith(new BaseSubscriber<BaseResponse<List<EssayArticleBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<EssayArticleBean>> beans) {
                        mEssayLiveData.setValue(beans);
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public Disposable getEssayDetails(String id, IRequestCallback<EssayDetailsBean> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getEssayDetails(id)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<EssayDetailsBean>>() {
                    @Override
                    public void onSuccess(List<EssayDetailsBean> detailsBeans) {
                        mEssayDetailsLiveData.setValue(detailsBeans.get(0));
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public Disposable getBannerDatas(IRequestCallback<List<BannerBean>> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getBannerDatas()
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<BannerBean>>() {
                    @Override
                    public void onSuccess(List<BannerBean> beans) {
                        mBannerLiveData.setValue(beans);
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public Disposable getCodeTypeDatas(IRequestCallback<List<CodeTypeBean>> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getCodeTypeDatas()
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<CodeTypeBean>>() {
                    @Override
                    public void onSuccess(List<CodeTypeBean> beans) {
                        mCodeTypeLiveData.setValue(beans);
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }


    @Override
    public Disposable getLastApkInfo(IRequestCallback<ApkBean> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getLastApkInfo()
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<ApkBean>>() {
                    @Override
                    public void onSuccess(List<ApkBean> beans) {
                        if (!SimpleUtils.isEmpty(beans)) {
                            mLiveApkBean.setValue(beans.get(0));
                        }
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public MediatorLiveData<ApkBean> getLastLiveApkInfo() {
        if (mLiveApkBean == null) {
            mLiveApkBean = new MediatorLiveData<>();
        }
        return mLiveApkBean;
    }
}
