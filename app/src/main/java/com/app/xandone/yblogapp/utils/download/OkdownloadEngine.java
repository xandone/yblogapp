package com.app.xandone.yblogapp.utils.download;

import android.content.Context;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.xandone.baselib.cache.ApkCache;
import com.app.xandone.baselib.update.IDownloadEngine;
import com.app.xandone.baselib.utils.AppUtils;
import com.app.xandone.baselib.utils.ToastHelper;
import com.app.xandone.yblogapp.App;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author: Admin
 * created on: 2021/2/1 10:10
 * description:
 */
public class OkdownloadEngine implements IDownloadEngine {

    private DownloadTask task;
    private DownloadListener mDownloadListener;
    private Context mContext;
    private MaterialDialog downDialog;
    //文件大小
    private long contentLen;
    //累计下载
    private long grandTotal;

    public OkdownloadEngine(Context context) {
        this.mContext = context;
        this.mDownloadListener = new OkdownloadCallback() {

            @Override
            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
                contentLen = contentLength;
                showDownloadDialog(context);
                if (!downDialog.isShowing()) {
                    downDialog.show();
                }
            }

            @Override
            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
                grandTotal += increaseBytes;
                int ratio = (int) (grandTotal * downDialog.getMaxProgress() / contentLen);
                if (downDialog.getCurrentProgress() < downDialog.getMaxProgress()) {
                    downDialog.setProgress(ratio);
                }
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
                success();
            }

        };
    }

    public OkdownloadEngine(Context context, DownloadListener listener) {
        this.mContext = context;
        this.mDownloadListener = listener;
    }

    @Override
    public void download(String url) {
        task = new DownloadTask.Builder(url, new File(ApkCache.getApkFilePath(App.sContext)))
                .setFilename(System.currentTimeMillis() + ".apk")
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(30)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();

        if (mDownloadListener != null) {
            task.enqueue(mDownloadListener);
        }
    }


    private void showDownloadDialog(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("版本更新")
                .content("下载中..")
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 100, true)
//                .progressNumberFormat("%1d Mb/%2d Mb")
                .canceledOnTouchOutside(false);
        downDialog = builder.build();
    }


    @Override
    public void success() {
        downDialog.setContent("下载完成");
        downDialog.dismiss();
        if (task.getFile() != null) {
            AppUtils.installApp(App.sContext, task.getFile());
        } else {
            ToastHelper.showShort("App下载失败");
        }

    }

    @Override
    public void error(int code) {
        task.cancel();
    }
}
