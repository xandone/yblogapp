package com.app.xandone.yblogapp.utils.download;

import com.app.xandone.baselib.cache.ApkCache;
import com.app.xandone.baselib.update.IDownloadEngine;
import com.app.xandone.yblogapp.App;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;

import java.io.File;

/**
 * author: Admin
 * created on: 2021/2/1 10:10
 * description:
 */
public class OkdownloadEngine implements IDownloadEngine {

    private DownloadTask task;
    private DownloadListener mDownloadListener;

    public OkdownloadEngine(DownloadListener listener) {
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

        task.enqueue(mDownloadListener);
    }

    @Override
    public void error(int code) {
        task.cancel();
    }
}
