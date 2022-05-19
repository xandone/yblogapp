package com.app.xandone.yblogapp.model;

import android.util.Log;

import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.CodeArticleBean;
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
 * created on: 2020/8/12 16:29
 * description:
 */
public class CodeModel extends BaseViewModel {
    private IRequestCallback<BaseResponse<List<CodeArticleBean>>> callback;

    private MediatorLiveData<BaseResponse<List<CodeArticleBean>>> mArtsLiveData;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        mArtsLiveData = new MediatorLiveData<>();

        mArtsLiveData.observe(owner, new Observer<BaseResponse<List<CodeArticleBean>>>() {
            @Override
            public void onChanged(BaseResponse<List<CodeArticleBean>> beans) {
                if (callback != null) {
                    callback.success(beans);
                }
            }
        });
    }

    public void getCodeDatas(int page, int row, int type, IRequestCallback<BaseResponse<List<CodeArticleBean>>> callback) {
        this.callback = callback;
        addSubscrible(ApiClient.getInstance()
                .getApiService()
                .getCodeDatas(page, row, type)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleBaseResponse())
                .subscribeWith(new BaseSubscriber<BaseResponse<List<CodeArticleBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<CodeArticleBean>> response) {
                        mArtsLiveData.setValue(response);
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                }));
    }
}
