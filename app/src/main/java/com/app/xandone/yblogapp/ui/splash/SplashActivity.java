package com.app.xandone.yblogapp.ui.splash;


import android.view.View;

import com.app.xandone.baselib.base.BaseSimpleActivity;
import com.app.xandone.baselib.cache.SpHelper;
import com.app.xandone.baselib.dialog.MDialogOnclickListener;
import com.app.xandone.baselib.dialog.XPopupUtils;
import com.app.xandone.yblogapp.App;
import com.app.xandone.yblogapp.MainActivity;
import com.app.xandone.yblogapp.config.IMyPermission;
import com.app.xandone.yblogapp.constant.ISpKey;
import com.app.xandone.yblogapp.databinding.ActSplashBinding;
import com.app.xandone.yblogapp.rx.RxHelper;
import com.app.xandone.yblogapp.utils.umeng.PushHelper;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * author: Admin
 * created on: 2020/9/1 14:05
 * description:
 */
public class SplashActivity extends BaseSimpleActivity<ActSplashBinding> {
    private CompositeDisposable mDisposables;

    @Override
    public View getLayout() {
        mBinding = ActSplashBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void init() {
        mDisposables = new CompositeDisposable();
        showPrivaryDialog();
    }

    /**
     * 协议对话框
     */
    private void showPrivaryDialog() {
        if (PushHelper.isAgreePrivacy()) {
            PushHelper.init(getApplicationContext());
            openPermission();
        } else {
            XPopupUtils.showSimpleDialog(this, "是否同意隐私协议", new MDialogOnclickListener() {
                @Override
                public void onConfirm() {
                    PushHelper.agreePrivacy2Local();
                    openPermission();
                }

                @Override
                public void onCancle() {
                    finish();
                }
            });
        }
    }

    /**
     * 打开权限
     */
    private void openPermission() {
        mDisposables.add(new RxPermissions(this).request(IMyPermission.WRITE_AND_READ_PERMS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                if (aBoolean) {
                    go2NextPage();
                } else {
                    finish();
                }
            }
        }));
    }

    private void go2NextPage() {
        mDisposables.add(Flowable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(RxHelper.handleIO())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        startActivity(MainActivity.class);
                        finish();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }
}
