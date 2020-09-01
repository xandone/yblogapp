package com.app.xandone.yblogapp.rx;

import android.text.TextUtils;

import com.app.xandone.baselib.base.IBaseView;
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

public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState;

    //默认开启
    public CommonSubscriber(IBaseView baseView) {
        this(baseView, true);
    }

    protected CommonSubscriber(IBaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(IBaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(IBaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
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
        if (mView == null) {
            return;
        }
        if (isShowErrorState) {
            if (!TextUtils.isEmpty(mErrorMsg)) {
                mView.showMsg(mErrorMsg, LoadingLayout.ILoadingStatus.SERVER_ERROR);
            } else if (t instanceof ApiException) {
                mView.showMsg(t.toString(), LoadingLayout.ILoadingStatus.SERVER_ERROR);
            } else if (t instanceof HttpException) {
                mView.showMsg("数据加载失败", LoadingLayout.ILoadingStatus.NET_ERROR);
            } else {
                mView.showMsg("未知错误", LoadingLayout.ILoadingStatus.SERVER_ERROR);
                LogHelper.d(t.toString());
            }

            mView.stateError();
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
}
