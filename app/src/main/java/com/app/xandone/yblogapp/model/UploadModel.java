package com.app.xandone.yblogapp.model;

import android.content.Context;
import android.util.Log;

import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.baselib.utils.ProgressDialogHelper;
import com.app.xandone.baselib.utils.SimpleUtils;
import com.app.xandone.baselib.utils.ToastHelper;
import com.app.xandone.yblogapp.api.ApiClient;
import com.app.xandone.yblogapp.config.ApiHost;
import com.app.xandone.yblogapp.constant.IResponseCode;
import com.app.xandone.yblogapp.exception.ApiException;
import com.app.xandone.yblogapp.model.bean.QiniuTokenBean;
import com.app.xandone.yblogapp.rx.BaseSubscriber;
import com.app.xandone.yblogapp.rx.RxHelper;
import com.app.xandone.yblogapp.viewmodel.BaseViewModel;
import com.blankj.utilcode.util.ObjectUtils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @author: xiao
 * created on: 2022/6/20 11:01
 * description:
 */
public class UploadModel extends BaseViewModel {

    private static UploadManager sUploadManager;

    private String qnToken;

    @Override
    protected void onCreate(LifecycleOwner owner) {
        if (sUploadManager == null) {
            sUploadManager = new UploadManager();
        }
    }

    /**
     * 多图片上传
     *
     * @param cxt
     * @param imags
     * @param isShow
     * @param listenner
     */
    public void upload2QiNiu(Context cxt, List<String> imags, boolean isShow, ImgUploadListenner<List<String>> listenner) {
        List<String> netPics = new ArrayList<>();
        if (ObjectUtils.isEmpty(imags)) {
            LogHelper.e("上传图片集合为为空");
            ToastHelper.showShort("请选择图片");
            return;
        }

        if (isShow) {
            ProgressDialogHelper.getInstance().showLoading(cxt);
        }

        ApiClient.getInstance()
                .getApiService()
                .getQiniuToken()
                .subscribeOn(Schedulers.io())
                .compose(RxHelper.handleRespose())
                .flatMap(new Function<QiniuTokenBean, Flowable<String>>() {
                    @Override
                    public Flowable<String> apply(@NotNull QiniuTokenBean qiniuTokenBean) throws Exception {
                        qnToken = qiniuTokenBean.getQiniutoken();
                        return Flowable.fromIterable(imags);
                    }
                })
                .concatMap(new Function<String, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(@NotNull String name) throws Exception {
                        return Flowable.create(new FlowableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NotNull FlowableEmitter<String> emitter) throws Exception {
                                upPic(qnToken, name, emitter);
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.e("hfghfg", "onSuccess.................");
                        netPics.add(ApiHost.QINIU_URL + s);
                        if (netPics.size() == imags.size()) {
                            Log.e("hfghfg", "图片上传成功：" + JsonUtils.obj2Json(netPics));
                            listenner.onComplete(netPics);
                            if (isShow) {
                                ProgressDialogHelper.getInstance().dimissLoading();
                            }
                        }
                    }

                    @Override
                    public void onFail(String message, int statusCode, int... apiCode) {
                        ToastHelper.showShort(message);
                        listenner.onFail();
                        Log.e("hfghfg", "图片上传失败");
                        if (isShow) {
                            ProgressDialogHelper.getInstance().dimissLoading();
                        }
                    }

                });
    }

    /**
     * 单图片上传
     *
     * @param cxt
     * @param path
     * @param isShow
     * @param listenner
     */
    public void upload2QiNiu(Context cxt, String path, boolean isShow, ImgUploadListenner<String> listenner) {
        if (ObjectUtils.isEmpty(path)) {
            LogHelper.e("上传图片集合为为空");
            ToastHelper.showShort("请选择图片");
            return;
        }
        if (isShow) {
            ProgressDialogHelper.getInstance().showLoading(cxt);
        }
        ApiClient.getInstance()
                .getApiService()
                .getQiniuToken()
                .subscribeOn(Schedulers.io())
                .compose(RxHelper.handleRespose())
                .flatMap(new Function<QiniuTokenBean, Flowable<String>>() {
                    @Override
                    public Flowable<String> apply(@NotNull QiniuTokenBean qiniuTokenBean) throws Exception {
                        return Flowable.create(new FlowableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NotNull FlowableEmitter<String> emitter) throws Exception {
                                upPic(qiniuTokenBean.getQiniutoken(), path, emitter);
                            }
                        }, BackpressureStrategy.BUFFER);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String url) {
                        LogHelper.e(ApiHost.QINIU_URL + url);
                        //TODO
                        listenner.onComplete(ApiHost.QINIU_URL + url);
                    }

                    @Override
                    public void onFail(String message, int statusCode, int... apiCode) {
                        ToastHelper.showShort(message);
                        listenner.onFail();
                    }

                    @Override
                    public void onEnd() {
                        if (isShow) {
                            ProgressDialogHelper.getInstance().dimissLoading();
                        }
                    }
                });
    }


    /**
     * @param token
     * @param path
     * @param emitter
     */
    private void upPic(String token, String path, FlowableEmitter<String> emitter) {
        sUploadManager.put(path,
                SimpleUtils.reNamePic(path),
                token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res 包含 hash、key 等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            emitter.onNext(key);
                            //注意：使用concatMap必须回调onComplete。  flatMap则不需要
                            emitter.onComplete();
                        } else {
                            emitter.onError(new ApiException(info.error, IResponseCode.UPLOAD_PIC_EXCEPTION));
                        }
                    }
                }, null);
    }

    public interface ImgUploadListenner<T> {
        void onComplete(T imgList);

        void onFail();
    }

}
