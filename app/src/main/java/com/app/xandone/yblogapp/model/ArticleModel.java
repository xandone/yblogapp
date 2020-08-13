package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.model.bean.ArticleBean;
import com.app.xandone.yblogapp.model.repository.ArticleRepository;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

/**
 * author: Admin
 * created on: 2020/8/12 16:29
 * description:
 */
public class ArticleModel extends BaseViewModel {
    private MutableLiveData<List<ArticleBean>> liveData;
    private ArticleRepository articleRepo;

    public MutableLiveData<List<ArticleBean>> getLiveData() {
        return liveData;
    }

    @Override
    protected void onCreate() {
        liveData = new MutableLiveData<>();
        articleRepo = ArticleRepository.getInstance();
        liveData = articleRepo.getArticleDatas(1, 10);
    }
}
