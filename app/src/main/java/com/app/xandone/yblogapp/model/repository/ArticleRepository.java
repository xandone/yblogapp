package com.app.xandone.yblogapp.model.repository;

import android.util.Log;

import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.api.ApiService;
import com.app.xandone.yblogapp.model.bean.ArticleBean;

import androidx.lifecycle.MediatorLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author: Admin
 * created on: 2020/8/12 17:19
 * description:
 */
public class ArticleRepository {

    private MediatorLiveData<ArticleBean> mArtsLiveData = new MediatorLiveData<>();

    private ArticleRepository() {
    }

    public static ArticleRepository getInstance() {
        return Builder.repository;
    }

    public MediatorLiveData<ArticleBean> getArticleDatas(int page, int row) {


        ApiClient.getInstance()
                .getApiService()
                .getArticleDatas(page, row).enqueue(new Callback<ArticleBean>() {
            @Override
            public void onResponse(Call<ArticleBean> call, Response<ArticleBean> response) {
                mArtsLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArticleBean> call, Throwable t) {

            }
        });

        return mArtsLiveData;

    }

    static class Builder {
        static final ArticleRepository repository = new ArticleRepository();
    }
}
