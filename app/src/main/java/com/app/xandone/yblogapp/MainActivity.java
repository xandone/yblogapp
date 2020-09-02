package com.app.xandone.yblogapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

import com.app.xandone.baselib.base.BaseActivity;
import com.app.xandone.yblogapp.ui.code.CodeListFragment;
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
        fragments.add(new CodeListFragment());
        fragments.add(new CodeListFragment());
        fragments.add(new CodeListFragment());

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