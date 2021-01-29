package com.app.xandone.baselib.update;

import android.content.Context;

/**
 * author: Admin
 * created on: 2021/1/25 17:00
 * description:
 */
public interface IUpdateAgent {

    void checkVersion(Context context, UpdateInfo updateInfo, IDownloadEngine engine);

    void showDialog(Context context, UpdateInfo updateInfo);

}
