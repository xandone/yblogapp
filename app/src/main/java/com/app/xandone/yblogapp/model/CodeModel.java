package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.repository.CodeRepository;
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
public class CodeModel extends BaseViewModel {
    private IFetchArticle articleRepo;
    private IRequestCallback<List<CodeArticleBean>> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        articleRepo = new CodeRepository();

        articleRepo.getCodeArticleLiveData().observe(owner, new Observer<List<CodeArticleBean>>() {
            @Override
            public void onChanged(List<CodeArticleBean> beans) {
                if (callback != null) {
                    callback.success(beans);
                }
            }
        });
    }

    public void getCodeDatas(int page, int row, int type, boolean isLoadMore, IRequestCallback<List<CodeArticleBean>> callback) {
        this.callback = callback;
        addSubscrible(articleRepo.getCodeDatas(page, row, type, isLoadMore, callback));
    }
}
