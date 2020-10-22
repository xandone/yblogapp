package com.app.xandone.yblogapp.rx;


import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.yblogapp.constant.IResponseCode;
import com.app.xandone.yblogapp.exception.ApiException;
import com.app.xandone.yblogapp.model.base.BaseResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author: Admin
 * created on: 2019/7/3 14:44
 * description:
 */
public class RxHelper {
    public static <T> FlowableTransformer<T, T> handleIO() {

        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<BaseResponse<T>, T> handleRespose() {
        return new FlowableTransformer<BaseResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<BaseResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<BaseResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(BaseResponse<T> response) {
                        if (response.getCode() == IResponseCode.SUCCESS) {
                            return createData(response.getData());
                        } else {
                            return Flowable.error(new ApiException(response.getMsg(), response.getCode()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 处理不转换类型，code非200的情况
     *
     * @return
     */
    public static <T> FlowableTransformer<BaseResponse<T>, BaseResponse<T>> handleBaseResponse() {
        return upstream -> upstream.flatMap((Function<BaseResponse<T>, Flowable<BaseResponse<T>>>) baseResponse -> {
            LogHelper.d(JsonUtils.obj2Json(baseResponse));
            if (baseResponse.getCode() == IResponseCode.SUCCESS) {
                return createData(baseResponse);
            } else {
                return Flowable.error(new ApiException(baseResponse.getMsg(), baseResponse.getCode()));
            }
        });
    }

    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                    LogHelper.e(e.toString());
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
