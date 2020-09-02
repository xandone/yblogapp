package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.repository.ArticleRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 16:29
 * description:
 */
public class ArticleModel extends BaseViewModel {
    private IFetchArticle articleRepo;


    @Override
    protected void onCreate() {
        articleRepo = new ArticleRepository();
    }

    public MutableLiveData<List<CodeArticleBean>> getArticleDatas(int page, int row, boolean isLoadMore) {
        return articleRepo.getArticleDatas(page, row, isLoadMore);
    }
}
