package com.app.xandone.yblogapp.model;

import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.rx.BaseSubscriber;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.rx.RxHelper;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

/**
 * author: Admin
 * created on: 2021/1/25 17:40
 * description:
 */
public class ApkModel extends BaseViewModel {
    private MediatorLiveData<ApkBean> mLiveApkBean;
    private IRequestCallback<ApkBean> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        mLiveApkBean = new MediatorLiveData<>();
        mLiveApkBean.observe(owner, new Observer<ApkBean>() {
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
        addSubscrible(ApiClient.getInstance()
                .getApiService()
                .getLastApkInfo()
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<ApkBean>>() {
                    @Override
                    public void onSuccess(List<ApkBean> beans) {
                        if (!SimpleUtils.isEmpty(beans)) {
                            mLiveApkBean.setValue(beans.get(0));
                        }
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                }));
    }
}
