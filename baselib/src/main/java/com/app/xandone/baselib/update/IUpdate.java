package com.app.xandone.baselib.update;

import android.content.Context;

/**
 * author: Admin
 * created on: 2021/1/25 16:52
 * description:
 */
public interface IUpdate {

    IUpdate setApkInfo(UpdateInfo updateInfo);

    IUpdate setId(int id);

    IUpdate setVersionCode(int versionCode);

    IUpdate setVersionName(String versionName);

    IUpdate setVersionTip(String versionTip);

    IUpdate setPostTime(String postTime);

    IUpdate setApkurl(String url);

    IUpdate isForce(boolean isForce);

    IUpdate isCanIgnore(boolean isCanIgnore);

    IUpdate isShowToast(boolean isShowToast);

    IUpdate setDownloadEngine(IDownloadEngine engine);

    void start(Context context);


}
