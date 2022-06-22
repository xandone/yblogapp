package com.app.xandone.baselib.base;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.xandone.baselib.R;

import androidx.viewbinding.ViewBinding;


/**
 * author: Admin
 * created on: 2020/8/12 11:03
 * description:
 */
public abstract class BaseActivity<VB extends ViewBinding> extends BaseSimpleActivity<VB> {

    @Override
    protected void initContentView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.base_toolbar_layout, null);
        FrameLayout content = rootView.findViewById(R.id.content);
        View view = getLayout();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        content.addView(view);
        setContentView(rootView);
    }

    protected abstract void initToolbar();
}
