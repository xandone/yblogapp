package com.app.xandone.yblogapp;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

import com.app.xandone.baselib.base.BaseSimpleActivity;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.update.IUpdateAgent;
import com.app.xandone.baselib.update.UpdateHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.yblogapp.model.AdminModel;
import com.app.xandone.yblogapp.model.ApkModel;
import com.app.xandone.yblogapp.model.bean.ApkBean;
import com.app.xandone.yblogapp.rx.IRequestCallback;
import com.app.xandone.yblogapp.ui.code.CodeFragment;
import com.app.xandone.yblogapp.ui.essay.Essayfragment;
import com.app.xandone.yblogapp.ui.manager.ManagerFragment;
import com.app.xandone.yblogapp.viewmodel.ModelProvider;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseSimpleActivity {
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private List<Fragment> fragments;
    private Fragment mCurrentFragment;
    private int mCurrentIndex = 0;
    private ApkModel mApkModel;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        ATest a = new ATest();
        getLifecycle().addObserver(a);

        fragments = new ArrayList<>();
        fragments.add(new CodeFragment());
        fragments.add(Essayfragment.getInstance());
        fragments.add(new ManagerFragment());

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.main_footer_code_rb:
                        mCurrentIndex = 0;
                        break;
                    case R.id.main_footer_essay_rb:
                        mCurrentIndex = 1;
                        break;
                    case R.id.main_footer_manager_rb:
                        mCurrentIndex = 2;
                        break;
                    default:
                        break;
                }
                turn2Fragment(mCurrentIndex);
            }
        });
    }

    @Override
    protected void initDataObserver() {
        mApkModel = ModelProvider.getModel(this, ApkModel.class, App.sContext);

//        checkApkVersion();
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
                        .isForce(false)
                        .start(MainActivity.this);
            }

            @Override
            public void error(String message, int statusCode) {

            }
        });
    }
}