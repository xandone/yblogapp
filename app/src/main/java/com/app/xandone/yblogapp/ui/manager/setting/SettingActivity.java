package com.app.xandone.yblogapp.ui.manager.setting;

import android.view.View;
import android.widget.TextView;

import com.app.xandone.baselib.cache.CacheHelper;
import com.app.xandone.baselib.dialog.MDialogOnclickListener;
import com.app.xandone.baselib.dialog.MDialogUtils;
import com.app.xandone.baselib.update.UpdateHelper;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallActivity;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.ApkModel;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.model.event.SwitchEvent;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.utils.download.OkdownloadEngine;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;

import org.greenrobot.eventbus.EventBus;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * author: Admin
 * created on: 2020/9/29 11:09
 * description:
 */
public class SettingActivity extends BaseWallActivity {
    @BindView(R.id.all_cache_size_tv)
    TextView allCacheSizeTv;

    private ApkModel mApkModel;

    @Override
    public int getLayout() {
        return R.layout.act_setting;
    }

    @Override
    public void wallInit() {
        try {
            allCacheSizeTv.setText(CacheHelper.getTotalCacheSize(App.sContext));
        } catch (Exception e) {
            e.printStackTrace();
        }

        onLoadFinish();
    }

    @Override
    protected void initDataObserver() {
        mApkModel = ModelProvider.getModel(this, ApkModel.class, App.sContext);
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.clear_setting_cl, R.id.clear_all_cache_cl, R.id.check_version_cl, R.id.exit_btn})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.clear_setting_cl:
                clearSettingInfo();
                break;
            case R.id.clear_all_cache_cl:
                clearAllCache();
                break;
            case R.id.check_version_cl:
                checkApkVersion();
                break;
            case R.id.exit_btn:
                exit();
                break;
            default:
                break;
        }
    }

    private void clearSettingInfo() {
        MDialogUtils.showSimpleDialog(this, "是否清除配置信息?", new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                CacheHelper.clearDefaultSp(App.sContext, ISpKey.CODE_TYPE_KEY);
                ToastUtils.showShort("清除完成");
            }
        });

    }

    private void clearAllCache() {
        MDialogUtils.showSimpleDialog(this, "是否清除所有缓存文件?", new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                CacheHelper.clearExternalFilesDir(App.sContext);
                allCacheSizeTv.setText("0KB");
                ToastUtils.showShort("清除完成");
            }
        });
    }

    private void checkApkVersion() {
        showApiLoading();
        mApkModel.getLastApkInfo(new IRequestCallback<ApkBean>() {
            @Override
            public void success(ApkBean apkBean) {
                cancleApiLoading();
                UpdateHelper.getInstance().init()
                        .setId(apkBean.getId())
                        .setVersionCode(Integer.parseInt(apkBean.getVersionCode()))
                        .setVersionName(apkBean.getVersionName())
                        .setPostTime(apkBean.getPostTime())
                        .setVersionTip(apkBean.getVersionTip())
                        .setApkurl("http://xandone.pub/yblog/apk/apkdown")
                        .isForce(apkBean.getIsForce() == 1)
                        .isShowToast(true)
                        .setDownloadEngine(new OkdownloadEngine(SettingActivity.this))
                        .start(SettingActivity.this);
            }

            @Override
            public void error(String message, int statusCode) {
                cancleApiLoading();
            }

        });
    }

    private void exit() {
        MDialogUtils.showSimpleDialog(this, "是否退出登录？", new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                EventBus.getDefault().post(new SwitchEvent(SwitchEvent.MANAGER_LOGIN_RAG));
                CacheHelper.clearDefaultSp(App.sContext, ISpKey.ADMIN_INFO_KEY);
                finish();
            }
        });
    }
}
