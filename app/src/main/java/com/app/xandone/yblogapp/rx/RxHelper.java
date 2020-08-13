package com.app.xandone.yblogapp.rx;


import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.yblogapp.constant.IResponseCode;
import com.app.xandone.yblogapp.exception.ApiException;
import com.app.xandone.yblogapp.model.base.BaseResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
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
