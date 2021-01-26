package com.app.xandone.yblogapp.model;

import com.app.xandone.baselib.update.IUpdate;
import com.app.xandone.yblogapp.api.IFetchArticle;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.model.repository.CodeRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2021/1/25 17:40
 * description:
 */
public class ApkModel extends BaseViewModel {
    private IFetchArticle articleRepo;
    private IRequestCallback<ApkBean> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        articleRepo = new CodeRepository();

        articleRepo.getLastLiveApkInfo().observe(owner, new Observer<ApkBean>() {
            @Override
            public void onChanged(ApkBean beans) {
                if (callback != null) {
                    callback.success(beans);
                }
            }
        });
    }

    public void getLastApkInfo(IRequestCallback<ApkBean> callback) {
        this.callback = callback;
        addSubscrible(articleRepo.getLastApkInfo(callback));
    }
}
