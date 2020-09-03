package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Admin
 * created on: 2020/8/12 16:56
 * description:
 */
public interface IApiService {

    @GET("yblog/art/artlist")
    Flowable<BaseResponse<List<CodeArticleBean>>> getArticleDatas(@Query("page") int page,
                                                                  @Query("row") int row,
                                                                  @Query("type") int type);

}
