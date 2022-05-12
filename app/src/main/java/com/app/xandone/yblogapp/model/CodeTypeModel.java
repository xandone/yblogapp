package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.model.bean.CodeTypeBean;
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
 * created on: 2020/9/9 16:29
 * description:
 */
public class CodeTypeModel extends BaseViewModel {
    private MediatorLiveData<List<CodeTypeBean>> mCodeTypeLiveData;

    private IRequestCallback<List<CodeTypeBean>> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        mCodeTypeLiveData = new MediatorLiveData<>();

        mCodeTypeLiveData.observe(owner, new Observer<List<CodeTypeBean>>() {
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
        addSubscrible(ApiClient.getInstance()
                .getApiService()
                .getCodeTypeDatas()
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<CodeTypeBean>>() {
                    @Override
                    public void onSuccess(List<CodeTypeBean> beans) {
                        mCodeTypeLiveData.setValue(beans);
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                }));
    }
}
