package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.bean.ArticleBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Admin
 * created on: 2020/8/12 16:56
 * description:
 */
public interface ApiService {

    @GET("yblog/art/artlist")
    Call<ArticleBean> getArticleDatas(@Query("page") int page, @Query("row") int row);

}
