package com.app.xandone.yblogapp.ui.manager.setting;

import android.view.View;
import android.widget.CompoundButton;

import com.app.xandone.baselib.cache.CacheHelper;
import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.dialog.MDialogOnclickListener;
import com.app.xandone.baselib.dialog.MDialogUtils;
import com.app.xandone.baselib.update.UpdateHelper;
import com.app.xandone.widgetlib.view.SettingView;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.R;
import com.app.xandone.yblogapp.base.BaseWallActivity;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.databinding.ActSettingBinding;
import com.app.xandone.yblogapp.model.ApkModel;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.model.event.SwitchEvent;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.utils.download.OkdownloadEngine;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.kyleduo.switchbutton.SwitchButton;

import org.greenrobot.eventbus.EventBus;


import androidx.appcompat.app.AppCompatDelegate;

/**
 * author: Admin
 * created on: 2020/9/29 11:09
 * description:
 */
public class SettingActivity extends BaseWallActivity<ActSettingBinding> {
    private SettingView allCacelSv;
    private SwitchButton moonSb;

    private ApkModel mApkModel;

    private boolean isNightMode;

    @Override
    public View getLayout() {
        mBinding = ActSettingBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void init() {
        allCacelSv = findViewById(R.id.all_cache_sv);
        moonSb = findViewById(R.id.moon_sb);

        allCacelSv.setSettingRightTv(CacheHelper.getTotalCacheSize(App.sContext));
        bindClick(R.id.setting_sv, R.id.all_cache_sv, R.id.version_sv, R.id.exit_btn);

        mApkModel = ModelProvider.getModel(this, ApkModel.class, App.sContext);

        nightMode();

        isNightMode = SpHelper.getDefaultBoolean(App.sContext, ISpKey.IS_NIGHT_MODE_KEY);
        moonSb.setChecked(isNightMode);

        onLoadFinish();
    }

    /**
     * 设置夜间模式
     */
    private void nightMode() {
        moonSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                SpHelper.save2DefaultSp(App.sContext, ISpKey.IS_NIGHT_MODE_KEY, isChecked);
//                moonSb.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Collection<BaseSimpleActivity> list = ActManager.getInstance().getAllActivity().values();
//                        for (BaseSimpleActivity act : list) {
//                            act.recreate();
//                        }
//                    }
//                }, 300);
            }
        });
    }


    @Override
    protected void requestData() {

    }


    @Override
    public void onClick(View v) {
        //选中 switch alt+enter 转化为if else
        switch (v.getId()) {
            case R.id.setting_sv:
                clearSettingInfo();
                break;
            case R.id.all_cache_sv:
                clearAllCache();
                break;
            case R.id.version_sv:
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
                toast("清除完成");
            }
        });

    }

    private void clearAllCache() {
        if (CacheHelper.isHaveCache(App.sContext)) {
            toast("没有缓存~");
            return;
        }
        MDialogUtils.showSimpleDialog(this, "是否清除所有缓存文件?", new MDialogOnclickListener() {
            @Override
            public void onConfirm() {
                CacheHelper.clearExternalCacheDir(App.sContext);
                allCacelSv.setSettingRightTv("0KB");
                toast("清除完成");
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
