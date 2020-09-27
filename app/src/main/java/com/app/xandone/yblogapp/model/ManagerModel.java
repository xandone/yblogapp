package com.app.xandone.yblogapp.model;


import com.app.xandone.yblogapp.api.IManager;
import com.app.xandone.yblogapp.model.bean.AdminBean;
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
public class ManagerModel extends BaseViewModel {
    private IManager manaerRepo;

    private IRequestCallback<AdminBean> adminCallback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        manaerRepo = new ManagerRepository();

        manaerRepo.getAdminLiveData().observe(owner, new Observer<AdminBean>() {
            @Override
            public void onChanged(AdminBean adminBean) {
                if (adminCallback != null) {
                    adminCallback.success(adminBean);
                }
            }
        });
    }

    public void login(String name, String psw, IRequestCallback<AdminBean> callback) {
        this.adminCallback = callback;
        addSubscrible(manaerRepo.login(name, psw, callback));
    }
}
