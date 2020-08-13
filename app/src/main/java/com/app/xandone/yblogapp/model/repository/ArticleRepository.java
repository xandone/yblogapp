package com.app.xandone.yblogapp.model.repository;


import android.util.Log;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.model.bean.ArticleBean;
import com.app.xandone.yblogapp.rx.CommonSubscriber;
import com.app.xandone.yblogapp.rx.RxHelper;
import com.google.gson.Gson;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 17:19
 * description:
 */
public class ArticleRepository {

    private MediatorLiveData<List<ArticleBean>> mArtsLiveData = new MediatorLiveData<>();

    private ArticleRepository() {
    }

    public static ArticleRepository getInstance() {
        return Builder.INSTANCE;
    }

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

    static class Builder {
        static final ArticleRepository INSTANCE = new ArticleRepository();
    }
}
