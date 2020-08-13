package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.ArticleBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Admin
 * created on: 2020/8/12 16:56
 * description:
 */
public interface ApiService {

    @GET("yblog/art/artlist")
    Flowable<BaseResponse<List<ArticleBean>>> getArticleDatas(@Query("page") int page, @Query("row") int row);

}
