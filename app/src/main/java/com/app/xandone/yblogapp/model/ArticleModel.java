package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.model.bean.ArticleBean;
import com.app.xandone.yblogapp.model.repository.ArticleRepository;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import androidx.lifecycle.MutableLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 16:29
 * description:
 */
public class ArticleModel extends BaseViewModel {
    private MutableLiveData<ArticleBean> liveData;
    private ArticleRepository articleRepo;

    public MutableLiveData<ArticleBean> getLiveData() {
        return liveData;
    }

    @Override
    protected void onCreate() {
        articleRepo = ArticleRepository.getInstance();
        liveData = articleRepo.getArticleDatas(1, 10);
    }
}
