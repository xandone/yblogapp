package com.app.xandone.baselib.update;

import android.content.Context;

import com.app.xandone.baselib.dialog.MDialogOnclickListener;
import com.app.xandone.baselib.dialog.MDialogUtils;
import com.app.xandone.baselib.utils.AppUtils;
import com.app.xandone.baselib.utils.ToastUtils;

/**
 * author: Admin
 * created on: 2021/1/26 10:12
 * description:
 */
public class UpdateAgent implements IUpdateAgent {

    private IDownloadEngine mIDownloadEngine;

    @Override
    public void checkVersion(Context context, UpdateInfo updateInfo, IDownloadEngine engine) {
        this.mIDownloadEngine = engine;
        int code = AppUtils.getAppVersionCode();
        if (code < updateInfo.getVersionCode()) {
            showDialog(context, updateInfo);
        } else {
            ToastUtils.showShort("当前已经是最新版本");
        }
    }

    @Override
    public void showDialog(Context context, final UpdateInfo updateInfo) {

        MDialogUtils.showVersionDialog(context, updateInfo, new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                downloadApkFile(updateInfo.getApkUrl());
            }

            @Override
            public void onNeutral() {

            }
        });
    }

    private void downloadApkFile(String url) {
        if (mIDownloadEngine == null) {
            return;
        }
        mIDownloadEngine.download(url);
    }


}
