package com.app.xandone.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.greenrobot.eventbus.EventBus;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;
import butterknife.ButterKnife;

/**
 * author: Admin
 * created on: 2020/8/12 11:05
 * description:
 */
public abstract class BaseSimpleFragment<VB extends ViewBinding> extends Fragment implements IFragInit, IToastAction {
    protected FragmentActivity mActivity;

    protected boolean mIsLoadedData;

    protected VB mBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetContentView();
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayout();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initButterKnife(view);
        init(view);
    }

    @CallSuper
    protected void initButterKnife(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void doBeforeSetContentView() {

    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    protected void lazyLoadData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsLoadedData) {
            lazyLoadData();
            mIsLoadedData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        mIsLoadedData = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBinding != null) {
            mBinding = null;
        }
    }

}
