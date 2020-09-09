package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.BannerBean;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;

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
    Flowable<BaseResponse<List<CodeArticleBean>>> getCodeDatas(@Query("page") int page,
                                                               @Query("row") int row,
                                                               @Query("type") int type);

    @GET("yblog/art/artDetails")
    Flowable<BaseResponse<List<CodeDetailsBean>>> getCodeDetails(@Query("artId") String artId);


    @GET("yblog/essay/essaylist")
    Flowable<BaseResponse<List<EssayArticleBean>>> getEssayDatas(@Query("page") int page,
                                                                 @Query("row") int row);

    @GET("yblog/essay/essayDetails")
    Flowable<BaseResponse<List<EssayDetailsBean>>> getEssayDetails(@Query("essayId") String essayId);

    @GET("yblog/banner/list")
    Flowable<BaseResponse<List<BannerBean>>> getBannerDatas();

    @GET("yblog/art/artTypeList")
    Flowable<BaseResponse<List<CodeTypeBean>>> getCodeTypeDatas();

}
