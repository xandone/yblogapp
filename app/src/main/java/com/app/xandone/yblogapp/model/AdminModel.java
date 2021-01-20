package com.app.xandone.yblogapp.model;


import com.app.xandone.yblogapp.api.IManager;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.model.repository.ManagerRepository;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2020/9/27 11:30
 * description:
 */
public class AdminModel extends BaseViewModel {
    private IManager manaerRepo;

    private IRequestCallback<List<AdminBean>> adminCallback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        manaerRepo = new ManagerRepository();

        manaerRepo.getAdminListLiveData().observe(owner, new Observer<List<AdminBean>>() {
            @Override
            public void onChanged(List<AdminBean> adminBeans) {
                if (adminCallback != null) {
                    adminCallback.success(adminBeans);
                }
            }
        });
    }

    public void getAdminList(String id, IRequestCallback<List<AdminBean>> callback) {
        this.adminCallback = callback;
        addSubscrible(manaerRepo.getAdminList(id, callback));
    }
}
