package com.app.xandone.yblogapp.model;


import com.app.xandone.yblogapp.api.IManager;
import com.app.xandone.yblogapp.model.bean.ArtInfoBean;
import com.app.xandone.yblogapp.model.repository.ManagerRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2020/9/27 11:30
 * description:
 */
public class ManagerChartModel extends BaseViewModel {
    private IManager manaerRepo;

    private IRequestCallback<ArtInfoBean> adminCallback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        manaerRepo = new ManagerRepository();

        manaerRepo.getArtInfoLiveData().observe(owner, new Observer<ArtInfoBean>() {
            @Override
            public void onChanged(ArtInfoBean adminBean) {
                if (adminCallback != null) {
                    adminCallback.success(adminBean);
                }
            }
        });
    }

    public void getArtInfoData(String id, IRequestCallback<ArtInfoBean> callback) {
        this.adminCallback = callback;
        addSubscrible(manaerRepo.getArtInfoData(id, callback));
    }
}
