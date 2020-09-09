package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
import com.app.xandone.yblogapp.model.repository.CodeRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2020/9/9 16:29
 * description:
 */
public class CodeTypeModel extends BaseViewModel {
    private IFetchArticle articleRepo;
    private IRequestCallback<List<CodeTypeBean>> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        articleRepo = new CodeRepository();

        articleRepo.getCodeTypeLiveData().observe(owner, new Observer<List<CodeTypeBean>>() {
            @Override
            public void onChanged(List<CodeTypeBean> beans) {
                if (callback != null) {
                    callback.success(beans);
                }
            }
        });
    }

    public void getCodeTypeDatas(IRequestCallback<List<CodeTypeBean>> callback) {
        this.callback = callback;
        addSubscrible(articleRepo.getCodeTypeDatas(callback));
    }
}
