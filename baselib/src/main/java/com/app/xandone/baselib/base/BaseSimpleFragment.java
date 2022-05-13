package com.app.xandone.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.xandone.baselib.event.SimplEvent;
import com.app.xandone.baselib.log.LogHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;

/**
 * author: Admin
 * created on: 2020/8/12 11:05
 * description:
 */
public abstract class BaseSimpleFragment extends Fragment implements IFragInit, IToastAction {
    protected FragmentActivity mActivity;

    protected boolean mIsLoadedData;

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
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initButterKnife(view);
        init(view);
    }

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

}
