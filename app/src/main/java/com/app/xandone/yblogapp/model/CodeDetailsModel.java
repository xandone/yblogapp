package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
import com.app.xandone.yblogapp.model.repository.CodeRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2020/9/4 11:18
 * description:
 */
public class CodeDetailsModel extends BaseViewModel {
    private IFetchArticle articleRepo;
    private IRequestCallback<CodeDetailsBean> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        articleRepo = new CodeRepository();

        articleRepo.getCodeDetailsLiveData().observe(owner, new Observer<CodeDetailsBean>() {
                    @Override
                    public void onChanged(CodeDetailsBean codeDetailsBean) {
                        if (callback != null) {
                            callback.success(codeDetailsBean);
                        }
                    }
                }
        );
    }

    public void getCodeDetails(String id, IRequestCallback<CodeDetailsBean> callback) {
        this.callback = callback;
        articleRepo.getCodeDetails(id, callback);
    }
}
