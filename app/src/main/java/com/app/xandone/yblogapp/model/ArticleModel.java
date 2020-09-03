package com.app.xandone.yblogapp.model;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.repository.ArticleRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2020/8/12 16:29
 * description:
 */
public class ArticleModel extends BaseViewModel {
    private IFetchArticle articleRepo;
    private IRequestCallback<List<CodeArticleBean>> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        articleRepo = new ArticleRepository();

        articleRepo.getCodeArticleLiveData().observe(owner, new Observer<List<CodeArticleBean>>() {
            @Override
            public void onChanged(List<CodeArticleBean> beans) {
                if (callback != null) {
                    callback.success(beans);
                }
            }
        });
    }

    public void getArticleDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback) {
        this.callback = callback;
        articleRepo.getArticleDatas(page, row, type, isLoadMore, callback);
    }
}
