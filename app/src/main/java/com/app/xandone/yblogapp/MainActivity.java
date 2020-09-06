package com.app.xandone.yblogapp;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

import com.app.xandone.baselib.base.BaseActivity;
import com.app.xandone.baselib.log.LogHelper;
import com.app.xandone.baselib.utils.JsonUtils;
import com.app.xandone.yblogapp.ui.code.CodeFragment;
import com.app.xandone.yblogapp.ui.code.CodeListFragment;
import com.app.xandone.yblogapp.ui.essay.Essayfragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private List<Fragment> fragments;
    private Fragment mCurrentFragment;
    private int mCurrentIndex = 0;

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
        fragments.add(new Essayfragment());

        List<String> list = JsonUtils.json2List(" [\"http://www.xandone.pub/FuGbIQJe3r-yAO0EjDBfA047NghH\",\"http://www.xandone.pub/FiPNEtA9s8aF2Nd8qM2DHGG1BpU6\",\"http://wwwxandone.pub/Fpxigj5CgGNVlh3JmIOmKSC0XFvO\"]");
        LogHelper.d(list.get(0));

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
}