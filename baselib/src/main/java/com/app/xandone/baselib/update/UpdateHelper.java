package com.app.xandone.baselib.update;


import android.content.Context;


/**
 * author: Admin
 * created on: 2021/1/25 17:35
 * description:
 */
public class UpdateHelper implements IUpdate {
    private UpdateInfo mUpdateInfo;
    private IUpdateAgent mIUpdateAgent;

    /**
     * 不提供下载引擎，需要使用的时候手动设置
     */
    private IDownloadEngine mIDownloadEngine;

    private UpdateHelper() {
    }

    public static UpdateHelper getInstance() {
        return Builder.helper;
    }

    public IUpdate init() {
        mUpdateInfo = new UpdateInfo();
        mIUpdateAgent = new UpdateAgent();
        return this;
    }

    @Override
    public IUpdate setApkInfo(UpdateInfo updateInfo) {
        mUpdateInfo = updateInfo;
        return this;
    }

    @Override
    public IUpdate setId(int id) {
        mUpdateInfo.setId(id);
        return this;
    }

    @Override
    public IUpdate setVersionCode(int versionCode) {
        mUpdateInfo.setVersionCode(versionCode);
        return this;
    }

    @Override
    public IUpdate setVersionName(String versionName) {
        mUpdateInfo.setVersionName(versionName);
        return this;
    }

    @Override
    public IUpdate setVersionTip(String versionTip) {
        mUpdateInfo.setVersionTip(versionTip);
        return this;
    }

    @Override
    public IUpdate setPostTime(String postTime) {
        mUpdateInfo.setPostTime(postTime);
        return this;
    }

    @Override
    public IUpdate setApkurl(String url) {
        mUpdateInfo.setApkUrl(url);
        return this;
    }

    @Override
    public IUpdate isForce(boolean isForce) {
        mUpdateInfo.setForce(isForce);
        return this;
    }

    @Override
    public IUpdate isCanIgnore(boolean isCanIgnore) {
        mUpdateInfo.setCanIgnore(isCanIgnore);
        return this;
    }

    @Override
    public IUpdate isShowToast(boolean isShowToast) {
        mUpdateInfo.setShowToast(isShowToast);
        return this;
    }

    @Override
    public IUpdate setDownloadEngine(IDownloadEngine engine) {
        this.mIDownloadEngine = engine;
        return this;
    }

    @Override
    public void start(Context context) {
        mIUpdateAgent.checkVersion(context, mUpdateInfo, mIDownloadEngine);
    }

    private static class Builder {
        private static UpdateHelper helper = new UpdateHelper();
    }

    public static final String IGNORE_APK_CODE = "ignore_apk_code";
}
