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
    private IDownloadEngine mIDownloadEngine;

    private UpdateHelper() {
    }

    public static UpdateHelper getInstance() {
        return Builder.helper;
    }

    public IUpdate init() {
        if (mUpdateInfo == null) {
            mUpdateInfo = new UpdateInfo();
        }
        if (mIUpdateAgent == null) {
            mIUpdateAgent = new UpdateAgent();
        }
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
    public IUpdate isForce(boolean isForce) {
        mUpdateInfo.setForce(isForce);
        return this;
    }

    @Override
    public IUpdate setDownloadEngine(IDownloadEngine engine) {
        this.mIDownloadEngine = engine;
        return this;
    }

    @Override
    public void start(Context context) {
        mIUpdateAgent.checkVersion(context, mUpdateInfo);
    }

    private static class Builder {
        private static UpdateHelper helper = new UpdateHelper();
    }
}
