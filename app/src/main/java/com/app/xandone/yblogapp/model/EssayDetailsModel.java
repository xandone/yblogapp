package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.model.bean.EssayDetailsBean;
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
public class EssayDetailsModel extends BaseViewModel implements IArtDetailsModel<EssayDetailsBean> {
    private MediatorLiveData<EssayDetailsBean> mEssayDetailsLiveData;

    private IRequestCallback<EssayDetailsBean> callback;


    @Override
    protected void onCreate(LifecycleOwner owner) {
        mEssayDetailsLiveData = new MediatorLiveData<>();

        mEssayDetailsLiveData.observe(owner, new Observer<EssayDetailsBean>() {
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
        addSubscrible(ApiClient.getInstance()
                .getApiService()
                .getEssayDetails(id)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<EssayDetailsBean>>() {
                    @Override
                    public void onSuccess(List<EssayDetailsBean> detailsBeans) {
                        mEssayDetailsLiveData.setValue(detailsBeans.get(0));
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                }));
    }

}
