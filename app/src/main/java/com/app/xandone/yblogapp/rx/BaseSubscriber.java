package com.app.xandone.yblogapp.rx;

import android.text.TextUtils;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.NetworkUtils;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.widgetlib.view.LoadingLayout;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.exception.ApiException;

import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;

/**
 * author: xandone
 * created on: 2020/8/13 16:48
 * description:
 */

public abstract class BaseSubscriber<T> extends ResourceSubscriber<T> {
    private String mErrorMsg;
    private boolean isShowErrorState;

    //默认开启
    public BaseSubscriber() {
        this(true);
    }

    protected BaseSubscriber(String errorMsg) {
        this.mErrorMsg = errorMsg;
    }

    protected BaseSubscriber(boolean isShowErrorState) {
        this.isShowErrorState = isShowErrorState;
    }

    protected BaseSubscriber(String errorMsg, boolean isShowErrorState) {
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onNext(T t) {
        if (!NetworkUtils.isConnected(App.sContext)) {
            ToastUtils.showShort("无法连接，请检查网络");
        }
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        if (isShowErrorState) {
            if (!TextUtils.isEmpty(mErrorMsg)) {
                onFail(mErrorMsg, LoadingLayout.ILoadingStatus.SERVER_ERROR);
            } else if (t instanceof ApiException) {
                ApiException error = (ApiException) t;
                onFail(t.toString(), LoadingLayout.ILoadingStatus.SERVER_ERROR, error.getCode());
            } else if (t instanceof HttpException) {
                onFail("数据加载失败", LoadingLayout.ILoadingStatus.NET_ERROR);
            } else {
                onFail("未知错误", LoadingLayout.ILoadingStatus.SERVER_ERROR);
                LogHelper.d(t.toString());
            }
        }

        ApiException ex = ApiException.handleException(t);
        if (!TextUtils.isEmpty(ex.getMessage())) {
            ToastUtils.showShort(ex.getMessage());
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    public void onFail(String message, int statusCode, int... apiCode) {
    }

}
