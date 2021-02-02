package com.app.xandone.yblogapp.ui.manager.setting;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.xandone.baselib.cache.ApkCache;
import com.app.xandone.baselib.cache.CacheHelper;
import com.app.xandone.baselib.cache.FileHelper;
import com.app.xandone.baselib.dialog.MDialogOnclickListener;
import com.app.xandone.baselib.dialog.MDialogUtils;
import com.app.xandone.baselib.update.UpdateHelper;
import com.app.xandone.baselib.utils.AppUtils;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallActivity;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.model.ApkModel;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.model.event.SwitchEvent;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.utils.download.OkdownloadCallback;
import com.app.xandone.yblogapp.utils.download.OkdownloadEngine;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private long contentLen;
    private long grandTotal;

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
//                        .setApkurl("http://localhost/apk/apkdown")
                        .setApkurl("http://xandone.pub/yblog/apk/apkdown")
                        .isForce(apkBean.getIsForce() == 1)
                        .setDownloadEngine(new OkdownloadEngine(new OkdownloadCallback() {
                            @Override
                            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
                                contentLen = contentLength;
                                Log.d("xandone1", "contentLength=" + contentLength + "字节   " + FileHelper.getFormatSize(contentLength));
                                tt();
                                if (!dialog.isShowing()) {
                                    dialog.show();
                                }
                            }

                            @Override
                            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
                                grandTotal += increaseBytes;

                                int ratio = (int) (grandTotal * dialog.getMaxProgress() / contentLen);
                                Log.d("xandone1", "increaseBytes=" + ratio);
                                if (dialog.getCurrentProgress() < dialog.getMaxProgress()) {
                                    dialog.setProgress(ratio);
                                }

                            }

                            @Override
                            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
                                Log.d("xandone1", "下载完成...." + task.getFile().getPath());
                                dialog.setContent("下载完成");
                                dialog.dismiss();
                                AppUtils.installApk(App.sContext, task.getFile().getPath());
                            }
                        }))
                        .start(SettingActivity.this);
            }

            @Override
            public void error(String message, int statusCode) {
                cancleApiLoading();
            }

        });
    }

    private MaterialDialog dialog;

    private void tt() {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("版本更新")
                .content("下载中..")
                .contentGravity(GravityEnum.CENTER)
                .progress(false, 100, true)
//                .progressNumberFormat("%1d Mb/%2d Mb")
                .canceledOnTouchOutside(false);
        dialog = builder.build();
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
