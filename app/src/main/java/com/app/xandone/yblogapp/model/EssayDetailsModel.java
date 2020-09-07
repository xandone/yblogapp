package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;
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
public class EssayDetailsModel extends BaseViewModel implements IArtDetailsModel<EssayDetailsBean> {
    private IFetchArticle articleRepo;
    private IRequestCallback<EssayDetailsBean> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        articleRepo = new CodeRepository();

        articleRepo.getEssayDetailsLiveData().observe(owner, new Observer<EssayDetailsBean>() {
                    @Override
                    public void onChanged(EssayDetailsBean codeDetailsBean) {
                        if (callback != null) {
                            callback.success(codeDetailsBean);
                        }
                    }
                }
        );
    }

    @Override
    public void getDetails(String id, IRequestCallback<EssayDetailsBean> callback) {
        this.callback = callback;
        articleRepo.getEssayDetails(id, callback);
    }

}
