package com.app.xandone.widgetlib.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Admin
 * created on: 2020/9/4 9:21
 * description:
 */
public class TextInputHelper implements TextWatcher {
    private View mMainView;
    private List<TextView> mViewSet;

    public TextInputHelper(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The view is empty");
        }
        mMainView = view;
        mMainView.setEnabled(false);
        setSelectCallBack(new ISelectCallBack() {
            @Override
            public void onSelect(boolean isSelect) {
                setEnabled(isSelect);
            }
        });
    }

    public void addViews(TextView... views) {
        if (views == null) {
            return;
        }

        if (mViewSet == null) {
            mViewSet = new ArrayList<>(views.length - 1);
        }

        for (TextView view : views) {
            view.addTextChangedListener(this);
            mViewSet.add(view);
        }
        afterTextChanged(null);
    }

    public void removeViews() {
        if (mViewSet == null) {
            return;
        }

        for (TextView view : mViewSet) {
            view.removeTextChangedListener(this);
        }
        mViewSet.clear();
        mViewSet = null;
    }

    // TextWatcher

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public synchronized void afterTextChanged(Editable s) {
        if (mViewSet == null) {
            return;
        }
        for (TextView view : mViewSet) {
            if ("".equals(view.getText().toString())) {
                if (mCallBack != null) {
                    mCallBack.onSelect(false);
                }
                return;
            }
        }
        if (mCallBack != null) {
            mCallBack.onSelect(true);
        }
    }

    /**
     * 设置View的事件
     *
     * @param enabled 修改button背景颜色
     */
    public void setEnabled(boolean enabled) {
        if (enabled == mMainView.isSelected()) {
            return;
        }
        if (enabled) {
            mMainView.setSelected(true);
            mMainView.setEnabled(true);
        } else {
            mMainView.setSelected(false);
            mMainView.setEnabled(false);
        }
    }

    private ISelectCallBack mCallBack;

    public void setSelectCallBack(ISelectCallBack callBack) {
        this.mCallBack = callBack;
    }

}
