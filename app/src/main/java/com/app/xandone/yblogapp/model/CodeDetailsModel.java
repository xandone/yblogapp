package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.model.bean.CodeDetailsBean;
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
 * created on: 2020/9/4 11:18
 * description:
 */
public class CodeDetailsModel extends BaseViewModel implements IArtDetailsModel<CodeDetailsBean> {
    private MediatorLiveData<CodeDetailsBean> mCodeDetailsLiveData;
    private IRequestCallback<CodeDetailsBean> callback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        mCodeDetailsLiveData = new MediatorLiveData<>();

        mCodeDetailsLiveData.observe(owner, new Observer<CodeDetailsBean>() {
                    @Override
                    public void onChanged(CodeDetailsBean codeDetailsBean) {
                        if (callback != null) {
                            callback.success(codeDetailsBean);
                        }
                    }
                }
        );
    }

    @Override
    public void getDetails(String id, IRequestCallback<CodeDetailsBean> callback) {
        this.callback = callback;
        addSubscrible(ApiClient.getInstance()
                .getApiService()
                .getCodeDetails(id).compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose()).subscribeWith(new BaseSubscriber<List<CodeDetailsBean>>() {
                    @Override
                    public void onSuccess(List<CodeDetailsBean> detailsBeans) {
                        mCodeDetailsLiveData.setValue(detailsBeans.get(0));
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                }));
    }
}
