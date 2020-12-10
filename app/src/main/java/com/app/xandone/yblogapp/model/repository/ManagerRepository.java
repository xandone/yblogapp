package com.app.xandone.yblogapp.model.repository;


import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.api.IManager;
import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.AdminBean;
import com.app.xandone.yblogapp.model.bean.ArtInfoBean;
import com.app.xandone.yblogapp.rx.BaseSubscriber;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.rx.RxHelper;


import java.util.List;

import androidx.lifecycle.MediatorLiveData;
import io.reactivex.disposables.Disposable;

/**
 * author: Admin
 * created on: 2020/9/27 11:32
 * description:
 */
public class ManagerRepository implements IManager {
    private MediatorLiveData<AdminBean> adminLiveData = new MediatorLiveData<>();
    private MediatorLiveData<ArtInfoBean> artLiveBeans = new MediatorLiveData<>();

    @Override
    public Disposable login(String name, String psw, IRequestCallback<AdminBean> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .login(name, psw)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleBaseResponse())
                .subscribeWith(new BaseSubscriber<BaseResponse<List<AdminBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<AdminBean>> response) {
                        if (response.getData() != null && response.getData().size() > 0) {
                            adminLiveData.setValue(response.getData().get(0));
                        } else {
                            callback.error(response.getMsg(), response.getCode());
                        }
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                });
    }

    @Override
    public MediatorLiveData<AdminBean> getAdminLiveData() {
        return adminLiveData;
    }

    @Override
    public Disposable getArtInfoData(String id, IRequestCallback<ArtInfoBean> callback) {
        return ApiClient.getInstance()
                .getApiService()
                .getArtInfoDatas(id)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleBaseResponse())
                .subscribeWith(new BaseSubscriber<BaseResponse<ArtInfoBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<ArtInfoBean> response) {
                        if (response.getData() != null) {
                            artLiveBeans.setValue(response.getData());
                        } else {
                            callback.error(response.getMsg(), response.getCode());
                        }
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, apiCode[0]);
                    }
                });
    }

    @Override
    public MediatorLiveData<ArtInfoBean> getArtInfoLiveData() {
        return artLiveBeans;
    }
}
