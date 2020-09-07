package com.app.xandone.yblogapp.model.repository;


import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;
import com.app.xandone.yblogapp.rx.BaseSubscriber;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.rx.RxHelper;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 17:19
 * description:
 */
public class CodeRepository implements IFetchArticle {

    private MediatorLiveData<List<CodeArticleBean>> mArtsLiveData = new MediatorLiveData<>();

    private MediatorLiveData<CodeDetailsBean> mCodeDetailsLiveData = new MediatorLiveData<>();

    private MediatorLiveData<List<EssayArticleBean>> mEssayLiveData = new MediatorLiveData<>();

    private MediatorLiveData<EssayDetailsBean> mEssayDetailsLiveData = new MediatorLiveData<>();

    @Override
    public MediatorLiveData<List<CodeArticleBean>> getCodeArticleLiveData() {
        return mArtsLiveData;
    }

    @Override
    public MediatorLiveData<CodeDetailsBean> getCodeDetailsLiveData() {
        return mCodeDetailsLiveData;
    }

    @Override
    public MediatorLiveData<List<EssayArticleBean>> getEssayArticleLiveData() {
        return mEssayLiveData;
    }

    @Override
    public MediatorLiveData<EssayDetailsBean> getEssayDetailsLiveData() {
        return mEssayDetailsLiveData;
    }

    public CodeRepository() {
    }

    @Override
    public void getCodeDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback) {
        ApiClient.getInstance()
                .getApiService()
                .getCodeDatas(page, row, type)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribe(new BaseSubscriber<List<CodeArticleBean>>() {
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
                    public void onFail(String message, int code) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public void getCodeDetails(String id, IRequestCallback<CodeDetailsBean> callback) {
        ApiClient.getInstance()
                .getApiService()
                .getCodeDetails(id)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribe(new BaseSubscriber<List<CodeDetailsBean>>() {
                    @Override
                    public void onSuccess(List<CodeDetailsBean> detailsBeans) {
                        mCodeDetailsLiveData.setValue(detailsBeans.get(0));
                    }

                    @Override
                    public void onFail(String message, int code) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }


    @Override
    public void getEssayDatas(int page, int row, boolean isLoadMore, IRequestCallback<List<EssayArticleBean>> callback) {
        ApiClient.getInstance()
                .getApiService()
                .getEssayDatas(page, row)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribe(new BaseSubscriber<List<EssayArticleBean>>() {
                    @Override
                    public void onSuccess(List<EssayArticleBean> beans) {
                        if (mEssayLiveData.getValue() == null) {
                            mEssayLiveData.setValue(beans);
                            return;
                        }
                        if (!isLoadMore) {
                            mEssayLiveData.setValue(beans);
                        } else {
                            List<EssayArticleBean> list = mEssayLiveData.getValue();
                            list.addAll(beans);
                            mEssayLiveData.setValue(list);
                        }
                    }

                    @Override
                    public void onFail(String message, int code) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public void getEssayDetails(String id, IRequestCallback<EssayDetailsBean> callback) {
        ApiClient.getInstance()
                .getApiService()
                .getEssayDetails(id)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribe(new BaseSubscriber<List<EssayDetailsBean>>() {
                    @Override
                    public void onSuccess(List<EssayDetailsBean> detailsBeans) {
                        mEssayDetailsLiveData.setValue(detailsBeans.get(0));
                    }

                    @Override
                    public void onFail(String message, int code) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }
}
