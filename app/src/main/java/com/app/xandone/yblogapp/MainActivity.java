package com.app.xandone.yblogapp;


import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.xandone.baselib.base.BaseSimpleActivity;
import com.app.xandone.baselib.update.UpdateHelper;
import com.app.xandone.baselib.utils.DoubleClickHelper;
import com.app.xandone.baselib.utils.ToastUtils;
import com.app.xandone.yblogapp.databinding.ActivityMainBinding;
import com.app.xandone.yblogapp.model.ApkModel;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.ui.code.CodeFragment;
import com.app.xandone.yblogapp.ui.essay.Essayfragment;
import com.app.xandone.yblogapp.ui.manager.ManagerFragment;
import com.app.xandone.yblogapp.utils.download.OkdownloadEngine;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseSimpleActivity {

    /**
     * ButterKnife改为 ViewBinding
     */
    private ActivityMainBinding mMainBinding;

    private List<Fragment> fragments;
    private Fragment mCurrentFragment;
    private int mCurrentIndex = 0;
    private ApkModel mApkModel;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initContentView() {
        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainBinding.getRoot());
    }

    @Override
    public void init() {
        ATest a = new ATest();
        getLifecycle().addObserver(a);

        fragments = new ArrayList<>();
        fragments.add(new CodeFragment());
        fragments.add(Essayfragment.getInstance());
        fragments.add(new ManagerFragment());

        turn2Fragment(mCurrentIndex);

        mMainBinding.bottomBar.setOnNavigationItemSelectedListener(item -> {
            boolean isSelect;
            int itemId = item.getItemId();
            //R 中的id 不再是final，改为if else
            if (itemId == R.id.main_footer_code_rb) {
                mCurrentIndex = 0;
                isSelect = true;
            } else if (itemId == R.id.main_footer_essay_rb) {
                mCurrentIndex = 1;
                isSelect = true;
            } else if (itemId == R.id.main_footer_manager_rb) {
                mCurrentIndex = 2;
                isSelect = true;
            } else {
                isSelect = false;
            }
            turn2Fragment(mCurrentIndex);
            return isSelect;
        });
    }

    @Override
    protected void initDataObserver() {
        mApkModel = ModelProvider.getModel(this, ApkModel.class, App.sContext);

        checkApkVersion();
    }


    public void turn2Fragment(int index) {
        Fragment toFragment = fragments.get(index);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            ft.hide(mCurrentFragment);
        }
        if (toFragment.isAdded()) {
            ft.show(toFragment);
        } else {
            ft.add(R.id.main_frame, toFragment);
        }
        ft.commitAllowingStateLoss();
        mCurrentFragment = toFragment;
    }

    private void checkApkVersion() {
        mApkModel.getLastApkInfo(new IRequestCallback<ApkBean>() {
            @Override
            public void success(ApkBean apkBean) {
                UpdateHelper.getInstance().init()
                        .setId(apkBean.getId())
                        .setVersionCode(Integer.parseInt(apkBean.getVersionCode()))
                        .setVersionName(apkBean.getVersionName())
                        .setPostTime(apkBean.getPostTime())
                        .setVersionTip(apkBean.getVersionTip())
                        .setApkurl("http://xandone.pub/yblog/apk/apkdown")
                        .isForce(false)
                        .isCanIgnore(true)
                        .setDownloadEngine(new OkdownloadEngine(MainActivity.this))
                        .start(MainActivity.this);
            }

            @Override
            public void error(String message, int statusCode) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            ToastUtils.showShort("再按一次退出");
            return;
        }
        moveTaskToBack(false);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 300);
    }
}