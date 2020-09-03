package com.app.xandone.yblogapp.model.repository;


import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
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
public class ArticleRepository implements IFetchArticle {

    private MediatorLiveData<List<CodeArticleBean>> mArtsLiveData = new MediatorLiveData<>();

    @Override
    public MediatorLiveData<List<CodeArticleBean>> getCodeArticleLiveData() {
        return mArtsLiveData;
    }

    public ArticleRepository() {
    }

    @Override
    public void getArticleDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback) {
        ApiClient.getInstance()
                .getApiService()
                .getArticleDatas(page, row, type)
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
}
