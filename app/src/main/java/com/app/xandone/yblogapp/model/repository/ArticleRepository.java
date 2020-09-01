package com.app.xandone.yblogapp.model.repository;


import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.ArticleBean;
import com.app.xandone.yblogapp.rx.CommonSubscriber;
import com.app.xandone.yblogapp.rx.RxHelper;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 17:19
 * description:
 */
public class ArticleRepository implements IFetchArticle {

    private MediatorLiveData<List<ArticleBean>> mArtsLiveData = new MediatorLiveData<>();

    public ArticleRepository() {
    }

    @Override
    public MediatorLiveData<List<ArticleBean>> getArticleDatas(int page, int row) {
        ApiClient.getInstance()
                .getApiService()
                .getArticleDatas(page, row)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribe(new CommonSubscriber<List<ArticleBean>>(null) {
                    @Override
                    public void onSuccess(List<ArticleBean> articleBeans) {
                        mArtsLiveData.setValue(articleBeans);
                    }
                });

        return mArtsLiveData;

    }
}
