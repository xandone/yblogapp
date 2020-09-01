package com.app.xandone.yblogapp.api;

import com.app.xandone.yblogapp.model.bean.ArticleBean;

import java.util.List;

import androidx.lifecycle.MediatorLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 17:59
 * description:
 */
public interface IFetchArticle {
    MediatorLiveData<List<ArticleBean>> getArticleDatas(int page, int row);
}
