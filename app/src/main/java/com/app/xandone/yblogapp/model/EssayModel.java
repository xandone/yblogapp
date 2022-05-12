package com.app.xandone.yblogapp.model;

import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.model.base.BaseResponse;
import com.app.xandone.yblogapp.model.bean.BannerBean;
import com.app.xandone.yblogapp.model.bean.EssayArticleBean;
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
 * created on: 2020/9/6 21:29
 * description:
 */
public class EssayModel extends BaseViewModel {
    private MediatorLiveData<BaseResponse<List<EssayArticleBean>>> mEssayLiveData;
    private MediatorLiveData<List<BannerBean>> mBannerLiveData;

    private IRequestCallback<BaseResponse<List<EssayArticleBean>>> callback;
    private IRequestCallback<List<BannerBean>> bannerCallback;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        mEssayLiveData = new MediatorLiveData<>();
        mBannerLiveData = new MediatorLiveData<>();

        mEssayLiveData.observe(owner, new Observer<BaseResponse<List<EssayArticleBean>>>() {
            @Override
            public void onChanged(BaseResponse<List<EssayArticleBean>> beans) {
                if (callback != null) {
                    callback.success(beans);
                }
            }
        });

        mBannerLiveData.observe(owner, new Observer<List<BannerBean>>() {
            @Override
            public void onChanged(List<BannerBean> beans) {
                if (bannerCallback != null) {
                    bannerCallback.success(beans);
                }
            }
        });
    }

    public void getEssayDatas(int page, int row, IRequestCallback<BaseResponse<List<EssayArticleBean>>> callback) {
        this.callback = callback;
        addSubscrible(ApiClient.getInstance()
                .getApiService()
                .getEssayDatas(page, row)
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleBaseResponse())
                .subscribeWith(new BaseSubscriber<BaseResponse<List<EssayArticleBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<EssayArticleBean>> response) {
                        mEssayLiveData.setValue(response);
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                }));
    }

    public void getBannerDatas(IRequestCallback<List<BannerBean>> callback) {
        this.bannerCallback = callback;
        addSubscrible(ApiClient.getInstance()
                .getApiService()
                .getBannerDatas()
                .compose(RxHelper.handleIO())
                .compose(RxHelper.handleRespose())
                .subscribeWith(new BaseSubscriber<List<BannerBean>>() {
                    @Override
                    public void onSuccess(List<BannerBean> beans) {
                        mBannerLiveData.setValue(beans);
                    }

                    @Override
                    public void onFail(String message, int code, int... apiCode) {
                        super.onFail(message, code);
                        callback.error(message, code);
                    }
                }));
    }
}
