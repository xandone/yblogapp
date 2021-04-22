package com.app.xandone.baselib.update;

import android.content.Context;

import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.config.BaseConfig;
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
        int ignoreCode = SpHelper.getDefaultInteger(BaseConfig.sApp, UpdateHelper.IGNORE_APK_CODE);
        //忽略的版本号
        if (updateInfo.isCanIgnore() && ignoreCode == updateInfo.getVersionCode()) {
            return;
        }
        if (code < updateInfo.getVersionCode()) {
            showDialog(context, updateInfo);
        } else {
            if (updateInfo.isShowToast()) {
                ToastUtils.showShort("当前已经是最新版本");
            }
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
                ignoreLastApkVersion(updateInfo);
            }
        });
    }

    private void downloadApkFile(String url) {
        if (mIDownloadEngine == null) {
            return;
        }
        mIDownloadEngine.download(url);
    }


    private void ignoreLastApkVersion(UpdateInfo updateInfo) {
        SpHelper.save2DefaultSp(BaseConfig.sApp, UpdateHelper.IGNORE_APK_CODE, updateInfo.getVersionCode());
    }

}
